package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.dao.TransferCaseQueueMapper;
import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import com.bazl.lims.transfer.service.TransferCaseQueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lizhihua on 2019/6/9.
 */
@Service
public class TransferCaseQueueServiceImpl extends BaseService implements TransferCaseQueueService {

    @Autowired
    TransferCaseQueueMapper transferCaseQueueMapper;

    /**
     * 获取待处理的案件上报队列
     * @return
     */
    @Override
    public List<TransferCaseQueue> selectPendingList(String labServerNo) {
        try {
            TransferCaseQueue transferCaseQueue = new TransferCaseQueue();
            transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_PENDING);
            transferCaseQueue.setLabServerNo(labServerNo);
            return transferCaseQueueMapper.select(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.selectPendingList error.", ex);
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
            return transferCaseQueueMapper.select(transferCaseQueue);
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
            transferCaseQueueMapper.updateStatus(transferCaseQueue);
        }catch(Exception ex){
            logger.error("invoke TransferCaseQueueService.updateStatus error.", ex);
        }
    }
}
