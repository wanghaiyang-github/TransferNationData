package com.bazl.lims.transfer.task;

import com.alibaba.fastjson.JSONObject;
import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.*;
import com.bazl.lims.transfer.task.service.SubmitToNation2PersonService;
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
 * 上报案件数据定时任务
 *
 * Created by lizhihua on 2019-05-08.
 */
public class TransferPersonDataTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(TransferPersonDataTask.class);

    /*@Autowired
    TransferPersonQueueService transferPersonQueueService;*/
    @Autowired
    OldLimsPersonQueueService oldLimsPersonQueueService;
    @Autowired
    NewLimsPersonQueueService newLimsPersonQueueService;
    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;
    @Autowired
    CaseInfoConfigs caseInfoConfigs;

    @Autowired
    TransferPersonDataExecutor transferPersonDataExecutor;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if(!transferNationParamsConfig.isTransferPersonActived()){
            logger.info("******************建库上报任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            oldVersionTransfer();
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            //TODO
            //按照顺义版本，生成上报数据文件
            shunyiVersionTransfer();
        }

    }

    private void shunyiVersionTransfer(){
        if(!TaskStaticFlag.TRANSFER_PERSON_RUUNING) {
            logger.info("******************开始执行newLims建库上报队列任务！********************" + System.currentTimeMillis());
            TaskStaticFlag.TRANSFER_PERSON_RUUNING = true;

            List<TransferPersonQueue> transferPersonQueueList = null;
            /**
             *  先获取待处理队列，优先进行处理
             */
            try {
                logger.info("******************执行newLims待处理建库上报队列任务！********************" + System.currentTimeMillis());

                transferPersonQueueList = newLimsPersonQueueService.selectPendingList(transferNationParamsConfig.getLabServerNo());
                if(ListUtils.isNotEmptyList(transferPersonQueueList)) {
                    executeTransfePersonQueue(transferPersonQueueList);
                }

                logger.info("******************完成newLims待处理建库上报队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("待处理建库队列执行过程中出现错误！", ex);
            }

            TaskStaticFlag.TRANSFER_PERSON_RUUNING = false;
            logger.info("******************完成执行newLims委托反馈任务！********************" + System.currentTimeMillis());
        }
    }

    private void oldVersionTransfer(){
        if(!TaskStaticFlag.TRANSFER_PERSON_RUUNING) {
            logger.info("******************开始执行建库上报队列任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.TRANSFER_PERSON_RUUNING = true;

            List<TransferPersonQueue> transferPersonQueueList = null;
            /**
             *  先获取待处理队列，优先进行处理
             */
            try {
                logger.info("******************执行待处理建库上报队列任务！********************" + System.currentTimeMillis());

                transferPersonQueueList = oldLimsPersonQueueService.selectPendingList(transferNationParamsConfig.getLabServerNo());
                if(ListUtils.isNotEmptyList(transferPersonQueueList)) {
                    executeTransfePersonQueue(transferPersonQueueList);
                }

                logger.info("******************完成待处理建库上报队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("待处理建库队列执行过程中出现错误！", ex);
            }

            /**
             *  处理生成上报文件失败的队列
             */
            /*try {
                logger.info("******************执行生成上报文件失败的队列任务！********************" + System.currentTimeMillis());

                transferPersonQueueList = oldLimsPersonQueueService.selectFailedList(transferNationParamsConfig.getLabServerNo(), TransferConstants.TRANSFER_STATUS_GENERATE_ERROR);
                if(ListUtils.isNotEmptyList(transferPersonQueueList)) {
                    executeTransfePersonQueue(transferPersonQueueList);
                }

                logger.info("******************完成生成上报文件失败的队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("生成上报文件失败的队列执行过程中出现错误！", ex);
            }*/

            /**
             *  处理上报失败的队列
             */
            /*try {
                logger.info("******************执行上报失败的队列任务！********************" + System.currentTimeMillis());

                transferPersonQueueList = oldLimsPersonQueueService.selectFailedList(transferNationParamsConfig.getLabServerNo(), TransferConstants.TRANSFER_STATUS_UPLOAD_ERROR);
                if(ListUtils.isNotEmptyList(transferPersonQueueList)) {
                    executeTransfePersonQueue(transferPersonQueueList);
                }

                logger.info("******************完成上报失败的队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("上报失败的队列执行过程中出现错误！", ex);
            }*/

            TaskStaticFlag.TRANSFER_PERSON_RUUNING = false;
            logger.info("******************完成执行委托反馈任务！********************" + System.currentTimeMillis());
        }
    }

    /**
     * 执行建库数据上报
     * @throws Exception
     */
    private void executeTransfePersonQueue(List<TransferPersonQueue> transferPersonQueueList) throws Exception {
        logger.info("****************加载建库上报队列数量：" + transferPersonQueueList.size() + "条！*****************");

        if(ListUtils.isNotEmptyList(transferPersonQueueList)){
            for(TransferPersonQueue transferPersonQueue : transferPersonQueueList){
                transferPersonDataExecutor.transferPersonData(transferPersonQueue);
            }
        }
    }

}
