package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.dao.TransferCaseQueueMapper;
import com.bazl.lims.transfer.dao.TransferPersonQueueMapper;
import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import com.bazl.lims.transfer.model.po.TransferPersonQueue;
import com.bazl.lims.transfer.service.TransferCaseQueueService;
import com.bazl.lims.transfer.service.TransferPersonQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class TransferPersonQueueServiceImpl extends BaseService implements TransferPersonQueueService {

    @Autowired
    TransferPersonQueueMapper transferPersonQueueMapper;

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
            return transferPersonQueueMapper.select(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.selectPendingList error.", ex);
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
            return transferPersonQueueMapper.select(transferPersonQueue);
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
            transferPersonQueueMapper.updateStatus(transferPersonQueue);
        }catch(Exception ex){
            logger.error("invoke TransferPersonQueueService.updateStatus error.", ex);
        }
    }
}
