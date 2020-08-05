package com.bazl.lims.transfer.task;

import com.alibaba.fastjson.JSONObject;
import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.ExternalSysInfo;
import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.*;
import com.bazl.lims.transfer.task.service.SubmitToNation2CriminalService;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * @author
 *
 * 查询ystr基因型并插入队列定时任务
 *
 * Created by wanghaiyang on 2019-06-23.
 */
public class TransferYstrDataTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(TransferYstrDataTask.class);

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;
    @Autowired
    TimeTaskService timeTaskService;
    @Autowired
    SampleInfoService sampleInfoService;
    @Autowired
    OldLimsPersonQueueService oldLimsPersonQueueService;
    @Autowired
    OldLimsCaseQueueService oldLimsCaseQueueService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!transferNationParamsConfig.isTransferYstrActived()){
            logger.info("******************读取Ystr任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(!TaskStaticFlag.TRANSFER_YSTR_RUNNING) {
            logger.info("******************开始读取Ystr队列任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.TRANSFER_YSTR_RUNNING = true;

            TimeTask timeTask = new TimeTask();
            timeTask.setTimeTaskType(transferNationParamsConfig.getTimeTaskType());
            List<TimeTask> timeTaskList = null;

            try {
                timeTaskList = timeTaskService.selectList(timeTask);
            }catch (Exception e) {
                logger.error("查询定时任务储存表过程中出现错误！", e);
            }

            if (ListUtils.isEmptyList(timeTaskList)) {
                timeTask.setId(UUID.randomUUID().toString());
                timeTask.setTimeCount(1);
                timeTask.setStartCount(0);
                timeTask.setEndCount(transferNationParamsConfig.getReadCount());
                timeTask.setReadCount(transferNationParamsConfig.getReadCount());

                try {
                    timeTaskService.insert(timeTask);
                }catch (Exception e) {
                    logger.error("插入定时任务储存表过程中出现错误！", e);
                }
            }else {
                timeTask = timeTaskList.get(0);
            }

            if (timeTask != null) {
                List<YstrDnaLibView> ystrDnaLibViewList = sampleInfoService.selectYstrDnaLibViewList(timeTask.getStartCount().intValue(), timeTask.getEndCount().intValue());
                try {
                    logger.info("******************执行待处理的Ystr队列任务！********************" + System.currentTimeMillis());

                    boolean flag = false;
                    if (ListUtils.isNotEmptyList(ystrDnaLibViewList)) {
                        flag = executeInsertYstrQueue(ystrDnaLibViewList);
                    }

                    //更新表信息
                    if (flag) {
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
                            timeTaskService.update(timeTask);
                        }catch (Exception e) {
                            logger.error("更新定时任务储存表过程中出现错误！" + timeTask.getId(), e);
                        }
                    }

                    logger.info("******************完成待处理的Ystr队列任务！********************" + System.currentTimeMillis());
                } catch (Exception ex) {
                    logger.error("待处理的Ystr队列执行过程中出现错误！", ex);
                }
            }

            TaskStaticFlag.TRANSFER_YSTR_RUNNING = false;
            logger.info("******************完成执行Ystr队列任务！********************" + System.currentTimeMillis());
        }
    }

    /**
     * 执行插入Ystr队列
     * @throws Exception
     */
    private boolean executeInsertYstrQueue(List<YstrDnaLibView> ystrDnaLibViewList) throws Exception {
        logger.info("****************加载Ystr上报队列数量：" + ystrDnaLibViewList.size() + "条！*****************");

        boolean flag = false;
        if(ystrDnaLibViewList.size()>0){
            logger.info("****************开始执行Ystr上报队列*****************");
            for (YstrDnaLibView ydlv : ystrDnaLibViewList) {
                SampleInfoModel sampleInfoModel = sampleInfoService.selectSampleInfoByBarcode(ydlv.getSampleId());
                if (sampleInfoModel != null) {
                    //判断案件类型是否为空
                    if (StringUtils.isNotBlank(sampleInfoModel.getCaseType())) {
                        //判断案件类型是否是违法犯罪案件或涉黑案件
                        if (sampleInfoModel.getCaseType().equals(TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_3)
                                || sampleInfoModel.getCaseType().equals(TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_5)) {
                            //插入队列queue_type为16
                            try {
                                TransferPersonQueue transferPersonQueue = new TransferPersonQueue();
                                transferPersonQueue.setId(UUID.randomUUID().toString());
                                transferPersonQueue.setSampleBarcode(sampleInfoModel.getSampleLabNo());
                                transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
                                transferPersonQueue.setCreateDatetime(new Date());
                                transferPersonQueue.setQueueType(TransferConstants.QUEUE_TYPE_16);

                                oldLimsPersonQueueService.insertQueueType(transferPersonQueue);
                            }catch (Exception e) {
                                logger.error("插入建库Ystr队列执行过程中出现错误！", e);
                                return false;
                            }
                        }else {
                            //插入案件队列queue_type为15
                            TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
                            try {
                                transferCaseQueue.setId(UUID.randomUUID().toString());
                                transferCaseQueue.setCaseId(sampleInfoModel.getCaseId());
                                transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
                                transferCaseQueue.setCreateDatetime(new Date());
                                transferCaseQueue.setQueueType(TransferConstants.QUEUE_TYPE_15);

                                oldLimsCaseQueueService.insertQueueType(transferCaseQueue);
                            }catch (Exception e) {
                                logger.error("插入案件Ystr队列执行过程中出现错误！", e);
                                return false;
                            }

                            try {
                                TransferCaseQueue transferCaseQueueDetail = new TransferCaseQueue();
                                transferCaseQueueDetail.setId(UUID.randomUUID().toString());
                                transferCaseQueueDetail.setQueueId(transferCaseQueue.getId());
                                transferCaseQueueDetail.setCaseId(transferCaseQueue.getCaseId());
                                transferCaseQueueDetail.setSampleBarcode(sampleInfoModel.getSampleLabNo());
                                transferCaseQueueDetail.setCreateDatetime(new Date());
                                transferCaseQueueDetail.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);

                                oldLimsCaseQueueService.insertQueueDetail(transferCaseQueueDetail);
                            }catch (Exception e) {
                                logger.error("插入案件Ystr队列执行过程中出现错误！", e);
                                return false;
                            }
                        }
                    }
                }
            }

            flag = true;
            logger.info("****************结束执行Ystr上报队列*****************");

        }

        return flag;
    }

}
