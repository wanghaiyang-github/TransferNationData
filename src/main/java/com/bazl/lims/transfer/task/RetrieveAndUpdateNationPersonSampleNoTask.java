package com.bazl.lims.transfer.task;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.po.SampleInfoModel;
import com.bazl.lims.transfer.model.po.TransferPersonQueue;
import com.bazl.lims.transfer.service.*;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import org.apache.cxf.common.i18n.Exception;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
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

import javax.xml.namespace.QName;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * 获取并更新国家库建库样本编号
 * Created by lizhihua on 2019/6/9.
 */
public class RetrieveAndUpdateNationPersonSampleNoTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(RetrieveAndUpdateNationPersonSampleNoTask.class);


    @Autowired
    OldLimsPersonQueueService oldLimsPersonQueueService;

    @Autowired
    NewLimsPersonQueueService newLimsPersonQueueService;

    @Autowired
    SampleInfoService sampleInfoService;

    @Autowired
    CaseInfoService caseInfoService;

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;

    @Autowired
    CaseInfoConfigs caseInfoConfigs;

    /**
     *
     */


    /**
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!transferNationParamsConfig.isRetrievePersonSampleNoActived()){
            logger.info("******************更新建库样本入国家库状态任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(!TaskStaticFlag.RETRIEVE_PERSON_SAMPLE_RUNNING) {
            logger.info("******************开始执行更新建库样本入国家库状态任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.RETRIEVE_PERSON_SAMPLE_RUNNING = true;

            List<TransferPersonQueue> transferPersonQueueList = null;

            try {
                if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                    transferPersonQueueList = oldLimsPersonQueueService.selectGenerateSucceedList(transferNationParamsConfig.getLabServerNo());
                    if (ListUtils.isNotEmptyList(transferPersonQueueList)) {
                        executeTransferCaseQueue(transferPersonQueueList);
                    }
                }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                    transferPersonQueueList = newLimsPersonQueueService.selectGenerateSucceedList(transferNationParamsConfig.getLabServerNo());
                    if (ListUtils.isNotEmptyList(transferPersonQueueList)) {
                        executeTransferCaseQueue(transferPersonQueueList);
                    }
                }

                logger.info("******************完成更新案件样本入国家库状态任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("更新案件样本入国家库状态过程中出现错误！", ex);
            }
        }

        TaskStaticFlag.RETRIEVE_PERSON_SAMPLE_RUNNING = false;
    }

    /**
     * 执行案件数据上报
     * @throws Exception
     */
    private void executeTransferCaseQueue(List<TransferPersonQueue> transferPersonQueueList) throws Exception {
        //String uploadFileProcessedPath = transferNationParamsConfig.getProcessedPath();

        //File transferFile = null;
        for(TransferPersonQueue transferPersonQueue : transferPersonQueueList){
            //transferFile = new File(uploadFileProcessedPath + "\\" + transferPersonQueue.getTransferFileName());
            //if(transferFile.getAbsoluteFile().exists()){
                updateNationSampleNoFromDNADataBase(transferPersonQueue);
            //}
        }

    }

    private void updateNationSampleNoFromDNADataBase(TransferPersonQueue transferPersonQueue) throws Exception {
        String sampleBarcode = transferPersonQueue.getSampleBarcode();

        SampleInfoModel sampleInfoModel = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            sampleInfoModel = sampleInfoService.selectSampleInfoByBarcode(sampleBarcode);
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            sampleInfoModel = sampleInfoService.selectNewSampleInfoByBarcode(sampleBarcode);
        }
        if (sampleInfoModel != null) {
            if(retriveNationSampleNo(sampleInfoModel)){
                transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_UPLOAD_SUCCEED);
                transferPersonQueue.setTransferDatetime(new Date());
                if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                    oldLimsPersonQueueService.updateStatus(transferPersonQueue);
                }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                    newLimsPersonQueueService.updateStatus(transferPersonQueue);
                }
            }
        }
    }

    private boolean retriveNationSampleNo(SampleInfoModel sampleInfoModel){
        try {
            String xmlSampleParams = this.sampleXmlParam(sampleInfoModel);
            String nationSampleNo = this.queryNationSampleNo(xmlSampleParams, sampleInfoModel);

            if(StringUtils.isNotBlank(nationSampleNo)) {
                if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                    //todo
                    sampleInfoService.updateSampleNoAndStateByBarcode(nationSampleNo, TransferConstants.NATION_SAMPLE_STATE_TRUE, sampleInfoModel.getSampleLabNo());
                    sampleInfoService.updateSampleNoAndStateByBarcode2(nationSampleNo, TransferConstants.NATION_SAMPLE_STATE_TRUE, sampleInfoModel.getSampleLabNo());
                }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                    sampleInfoService.updateNewSampleNoAndStateByBarcode(nationSampleNo, TransferConstants.NATION_SAMPLE_STATE_TRUE, sampleInfoModel.getSampleLabNo());
                }
            }

            return true;
        }catch(Exception ex){
            logger.info("获取并更新案件及样本国家库编号失败。", ex);
            return false;
        }
    }

    private String queryNationSampleNo(String xmlStr, SampleInfoModel sampleInfo) throws Exception {
        String queryNationDbNoUrl = transferNationParamsConfig.getQueryNationDbNoUrl();
        String result = "";

        try {
            logger.info("-------------queryNationSampleNo接口参数：" + xmlStr);

            JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
            Client client = dcf.createClient(queryNationDbNoUrl);

            Object[] objects = null;
            //命名空间http://service对应现勘wsdl接口中wsdl:definitions中的targetNamespace的值
            //objects = client.invoke(new QName("http://queryData.webservice.gdna2.highershine.com", "queryPersonNoBySampleLabNO"), xmlStr);

            objects = client.invoke("queryPersonNoBySampleLabNO", new Object[]{xmlStr, "lims"});

            result = stringParseSampleResult(objects[0].toString());
            logger.info("获取到国家库的样本编号为" + result);
        } catch (Exception ex3) {
            this.logger.error("调用国家库查询样本编号接口失败。", ex3);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (java.lang.Exception e) {
            e.printStackTrace();
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

    private String sampleXmlParam(SampleInfoModel sampleInfo) {
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        str.append("<ROOT>");
        str.append("<conditions>");
        str.append("<sampleLabNo>").append(sampleInfo.getSampleLabNo()).append("</sampleLabNo>");
        str.append("<sampleName>").append(sampleInfo.getEvidenceName()).append("</sampleName>");
        str.append("</conditions>");
        str.append("</ROOT>");
        return str.toString();
    }

    private String xmlCaseParam(String caseId) {
        StringBuffer str = new StringBuffer();
        str.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        str.append("<ROOT>");
        str.append("<type>").append(1).append("</type>");
        str.append("<conditions>");
        str.append("<extid>").append(caseId).append("</extid>");
        str.append("</conditions>");
        str.append("</ROOT>");
        return str.toString();
    }

}
