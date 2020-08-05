package com.bazl.lims.transfer.task;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.*;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author
 *
 * 查询未成功上报国家库的检材并插入队列定时任务
 *
 * Created by wanghaiyang on 2019-07-10.
 */
public class TransferNotInstoreSampleDataTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(TransferNotInstoreSampleDataTask.class);

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;
    @Autowired
    TimeTaskService timeTaskService;
    @Autowired
    SampleInfoService sampleInfoService;
    @Autowired
    OldLimsPersonQueueService oldLimsPersonQueueService;
    @Autowired
    NewLimsPersonQueueService newLimsPersonQueueService;
    @Autowired
    OldLimsCaseQueueService oldLimsCaseQueueService;
    @Autowired
    NewLimsCaseQueueService newLimsCaseQueueService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!transferNationParamsConfig.isTransferNotInstoreActived()){
            logger.info("******************读取已经是入库的检材列表任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(!TaskStaticFlag.TRANSFER_NOT_INSTORE_RUNNING) {
            logger.info("******************开始读取已经是入库的检材列表队列任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.TRANSFER_NOT_INSTORE_RUNNING = true;

            TimeTask timeTask = new TimeTask();
            timeTask.setTimeTaskType(transferNationParamsConfig.getTimeTaskNotInstoreType());
            List<TimeTask> timeTaskList = null;

            try {
                timeTaskList = timeTaskService.selectList(timeTask);
            }catch (Exception e) {
                logger.error("查询定时任务储存表过程中出现错误！", e);
            }

            if (ListUtils.isEmptyList(timeTaskList)) {
                logger.info("******************插入定时任务储存表信息！********************" + System.currentTimeMillis());
                timeTask.setId(UUID.randomUUID().toString());
                timeTask.setTimeCount(1);
                timeTask.setStartCount(0);
                timeTask.setEndCount(transferNationParamsConfig.getReadCount());
                timeTask.setReadCount(transferNationParamsConfig.getReadCount());

                try {
                    //插入定时任务信息
                    timeTaskService.insert(timeTask);
                }catch (Exception e) {
                    logger.error("插入定时任务储存表过程中出现错误！", e);
                }
            }else {
                timeTask = timeTaskList.get(0);
            }

            if (timeTask != null) {
                List<SampleInfoModel> sampleInfoModelList = sampleInfoService.selectInstoreSampleList(timeTask.getStartCount().intValue(), timeTask.getEndCount().intValue());
                try {
                    logger.info("******************执行待处理的已经是入库的检材列表队列任务！********************" + System.currentTimeMillis());

                    boolean flag = false;
                    if (ListUtils.isNotEmptyList(sampleInfoModelList)) {
                        flag = executeInsertInstoreSampleQueue(sampleInfoModelList);
                    }

                    //更新表信息
                    if (flag) {
                        logger.info("******************更新定时任务储存表信息！********************" + System.currentTimeMillis());
                        int timeCount = 0;
                        if (timeTask.getTimeCount() != null) {
                            timeCount = timeTask.getTimeCount().intValue();
                        }
                        timeTask.setTimeCount(timeCount + 1);
                        int endNo = 0;
                        if (timeTask.getEndCount() != null) {
                            endNo = timeTask.getEndCount().intValue();
                        }
                        int readNo = transferNationParamsConfig.getReadCount();

                        timeTask.setStartCount(endNo);
                        timeTask.setEndCount(endNo + readNo);
                        timeTask.setReadCount(readNo);

                        try {
                            //更新定时任务信息
                            timeTaskService.update(timeTask);
                        }catch (Exception e) {
                            logger.error("更新定时任务储存表过程中出现错误！" + timeTask.getId(), e);
                        }
                    }

                    logger.info("******************完成待处理的已经是入库的检材列表队列任务！********************" + System.currentTimeMillis());
                } catch (Exception ex) {
                    logger.error("待处理的已经是入库的检材列表队列执行过程中出现错误！", ex);
                }
            }

            TaskStaticFlag.TRANSFER_NOT_INSTORE_RUNNING = false;
            logger.info("******************完成执行已经是入库的检材列表队列任务！********************" + System.currentTimeMillis());
        }
    }

    /**
     * 执行已经是入库的检材列表队列
     * @throws Exception
     */
    private boolean executeInsertInstoreSampleQueue(List<SampleInfoModel> sampleInfoModelList) throws Exception {
        logger.info("****************加载已经是入库的检材列表队列数量：" + sampleInfoModelList.size() + "条！*****************"+ System.currentTimeMillis());

        boolean flag = false;
        if(sampleInfoModelList.size()>0){
            logger.info("****************开始执行已经是入库的检材列表队列*****************" + System.currentTimeMillis());
            for (SampleInfoModel sim : sampleInfoModelList) {
                try {
                    String xmlSampleParams = this.sampleXmlParam(sim);
                    String nationSampleNo = this.queryNationSampleNo(xmlSampleParams, sim);

                    if(StringUtils.isNotBlank(nationSampleNo)) {
                        logger.info("*********此检材已经上报国家库**********" + sim.getEvidenceCode());
                    }else {
                        logger.info(sim.getCaseId() + "****************开始执行插入oueue_sample队列*****************" + System.currentTimeMillis());
                        //插入案件队列queue_type为15
                        TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
                        try {
                            transferCaseQueue.setId(UUID.randomUUID().toString());
                            transferCaseQueue.setCaseId(sim.getCaseId());
                            transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
                            transferCaseQueue.setQueueType(TransferConstants.QUEUE_TYPE_15);
                            transferCaseQueue.setCreateDatetime(new Date());

                            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                                oldLimsCaseQueueService.insertQueueType(transferCaseQueue);
                            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                                newLimsCaseQueueService.insertQueueType(transferCaseQueue);
                            }
                        }catch (Exception e) {
                            logger.error("插入未上报检材的案件队列执行过程中出现错误！", e);
                            return false;
                        }

                        try {
                            logger.info(sim.getSampleLabNo() + "****************开始执行插入queue_detail队列*****************" + System.currentTimeMillis());
                            TransferCaseQueue transferCaseQueueDetail = new TransferCaseQueue();
                            transferCaseQueueDetail.setId(UUID.randomUUID().toString());
                            transferCaseQueueDetail.setQueueId(transferCaseQueue.getId());
                            transferCaseQueueDetail.setSampleBarcode(sim.getSampleLabNo());
                            transferCaseQueueDetail.setCaseId(transferCaseQueue.getCaseId());
                            transferCaseQueueDetail.setCreateDatetime(new Date());
                            transferCaseQueueDetail.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);

                            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                                oldLimsCaseQueueService.insertQueueDetail(transferCaseQueueDetail);
                            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                                newLimsCaseQueueService.insertQueueDetail(transferCaseQueueDetail);
                            }
                        }catch (Exception e) {
                            logger.error("插入未上报检材队列执行过程中出现错误！", e);
                            return false;
                        }
                    }
                }catch (Exception e) {
                    logger.info("获取并更新样本国家库编号失败。"+ sim.getEvidenceCode(), e);
                    return false;
                }
            }

            flag = true;
            logger.info("****************结束执行已经是入库的检材列表队列*****************" + System.currentTimeMillis());

        }

        return flag;
    }

    private String sampleXmlParam(SampleInfoModel sampleInfo) {
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        str.append("<ROOT>");
        str.append("<conditions>");
        str.append("<sampleLabNo>").append(sampleInfo.getEvidenceCode()).append("</sampleLabNo>");
        str.append("<sampleName>").append(sampleInfo.getEvidenceName()).append("</sampleName>");
        str.append("</conditions>");
        str.append("</ROOT>");
        return str.toString();
    }

    private String queryNationSampleNo(String xmlStr, SampleInfoModel sampleInfo) throws Exception {
        String queryNationDbNoUrl = transferNationParamsConfig.getQueryNationDbNoUrl();
        String result = "";

        try {
            logger.info("----------queryNationSampleNo接口参数：" + xmlStr);
            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(queryNationDbNoUrl);
            //设置超时单位为毫秒
            HTTPConduit conduit =(HTTPConduit) client.getConduit();
            HTTPClientPolicy policy = new HTTPClientPolicy();
            policy.setConnectionTimeout(30000); //连接超时时间
            policy.setReceiveTimeout(30000);//请求超时时间.
            conduit.setClient(policy);

            Object[] objects = client.invoke("queryPersonNoBySampleLabNO", new Object[]{xmlStr, "lims"});
            if(objects != null
                    && objects.length > 0) {
                logger.info("获取案件样本入库编号返回：" + objects[0].toString());
                result = stringParseSampleResult(objects[0].toString());
            }
        } catch (Exception ex3) {
            this.logger.error("调用国家库查询样本编号接口失败。", ex3);
        }

        return result;
    }

    public String stringParseSampleResult(String result) throws DocumentException {
        Document document = DocumentHelper.parseText(result);
        Element root = document.getRootElement();
        String status = root.element("status").getTextTrim();

        String sampleNo = "";
        if("1".equals(status)) {
            Element personLists = root.element("personList");
            List personList = personLists.elements("personInfo");
            if(personList != null && personList.size() > 0) {
                Element e = (Element)personList.get(0);
                Element personNo = e.element("personNo");
                sampleNo = personNo.getTextTrim();

                return sampleNo;
            }
        }

        return null;
    }
}
