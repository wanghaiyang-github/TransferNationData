package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.dao.OldLimsPersonQueueMapper;
import com.bazl.lims.transfer.model.po.TransferPersonQueue;
import com.bazl.lims.transfer.service.OldLimsPersonQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author
 * @date 2019/6/18.
 */
@Service
public class OldLimsPersonQueueServiceImpl extends BaseService implements OldLimsPersonQueueService {


    @Autowired
    OldLimsPersonQueueMapper oldLimsPersonQueueMapper;

    /**
     * 获取待处理的人员上报队列
     * @return
     */
    @Override
    public List<TransferPersonQueue> selectPendingList(String labServerNo) {
        try {
            TransferPersonQueue transferPersonQueue = new TransferPersonQueue();
            transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
            transferPersonQueue.setLabServerNo(labServerNo);
            return oldLimsPersonQueueMapper.select(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.selectPendingList error.", ex);
            return null;
        }
    }

    /**
     * 获取所有已生成上报文件的上报队列
     * @param labServerNo
     * @return
     */
    public List<TransferPersonQueue> selectGenerateSucceedList(String labServerNo) {
        try {
            TransferPersonQueue transferPersonQueue = new TransferPersonQueue();
            transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_SUCCEED);
            transferPersonQueue.setLabServerNo(labServerNo);
            return oldLimsPersonQueueMapper.select(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.selectGenerateSucceedList error.", ex);
            return null;
        }
    }

    /**
     * 获取失败的人员上报队列
     * @param transferFailedStatus  -1：生成文件失败；-2：上报国家库失败
     * @return
     */
    @Override
    public List<TransferPersonQueue> selectFailedList(String labServerNo, int transferFailedStatus) {
        try {
            TransferPersonQueue transferPersonQueue = new TransferPersonQueue();
            transferPersonQueue.setTransferStatus(transferFailedStatus);
            transferPersonQueue.setLabServerNo(labServerNo);
            return oldLimsPersonQueueMapper.select(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.selectFailedList error, param is " + transferFailedStatus + ".", ex);
            return null;
        }
    }

    /**
     * 更新人员上报队列状态
     * @param transferPersonQueue
     */
    @Override
    public void updateStatus(TransferPersonQueue transferPersonQueue) {
        try {
            oldLimsPersonQueueMapper.updateStatus(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.updateStatus error.", ex);
        }
    }

    /**
     * 插入上报队列
     * @param transferPersonQueue
     */
    @Override
    public int insertQueueType(TransferPersonQueue transferPersonQueue) {
        try {
            return oldLimsPersonQueueMapper.insertQueueType(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.insertQueueType error.", ex);
            return 0;
        }
    }

}
