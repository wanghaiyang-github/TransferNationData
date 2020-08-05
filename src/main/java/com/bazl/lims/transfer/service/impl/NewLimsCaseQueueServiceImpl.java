package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.dao.NewLimsCaseQueueMapper;
import com.bazl.lims.transfer.dao.OldLimsCaseQueueMapper;
import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import com.bazl.lims.transfer.service.NewLimsCaseQueueService;
import com.bazl.lims.transfer.service.OldLimsCaseQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date 2019/6/18.
 */
@Service
public class NewLimsCaseQueueServiceImpl extends BaseService  implements NewLimsCaseQueueService {

    @Autowired
    NewLimsCaseQueueMapper newLimsCaseQueueMapper;

    /**
     * 获取待处理的案件上报队列
     * @return
     */
    @Override
    public List<TransferCaseQueue> selectPendingList(String labServerNo) {
        try {
            TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
            transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
            transferCaseQueue.setQueueType(TransferConstants.QUEUE_TYPE_15);
            transferCaseQueue.setLabServerNo(labServerNo);
            return newLimsCaseQueueMapper.select(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.selectPendingList error.", ex);
            return null;
        }
    }

    /**
     * 获取所有已生成上报文件的上报队列
     * @param labServerNo
     * @return
     */
    @Override
    public List<TransferCaseQueue> selectGenerateSucceedList(String labServerNo) {
        try {
            TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
            transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_SUCCEED);
            transferCaseQueue.setQueueType(TransferConstants.QUEUE_TYPE_15);
            transferCaseQueue.setLabServerNo(labServerNo);
            return newLimsCaseQueueMapper.select(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.selectGenerateSucceedList error.", ex);
            return null;
        }
    }

    /**
     * 获取失败的案件上报队列
     * @param transferFailedStatus  -1：生成文件失败；-2：上报国家库失败
     * @return
     */
    @Override
    public List<TransferCaseQueue> selectFailedList(String labServerNo, int transferFailedStatus) {
        try {
            TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
            transferCaseQueue.setTransferStatus(transferFailedStatus);
            transferCaseQueue.setLabServerNo(labServerNo);
            return newLimsCaseQueueMapper.select(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.selectFailedList error, param is " + transferFailedStatus + ".", ex);
            return null;
        }
    }

    /**
     * 更新案件上报队列状态
     * @param transferCaseQueue
     */
    @Override
    public void updateStatus(TransferCaseQueue transferCaseQueue) {
        try {
            newLimsCaseQueueMapper.updateStatus(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.updateStatus error.", ex);
        }
    }

    @Override
    public int insertQueueType(TransferCaseQueue transferCaseQueue) {
        try {
            return newLimsCaseQueueMapper.insertQueueType(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.updateStatus error.", ex);
            return 0;
        }
    }

    @Override
    public int insertQueueDetail(TransferCaseQueue transferCaseQueue) {
        try {
            return newLimsCaseQueueMapper.insertQueueDetail(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.insertQueueDetail error.", ex);
            return 0;
        }
    }
}
