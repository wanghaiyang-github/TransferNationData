package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface TransferCaseQueueService {

    /**
     * 获取待处理的案件上报队列
     * @param labServerNo 当前实验室编号
     * @return
     */
    public List<TransferCaseQueue> selectPendingList(String labServerNo);


    /**
     * 获取失败的案件上报队列
     * @param labServerNo 当前实验室编号
     * @param transferFailedStatus  -1：生成文件失败；-2：上报国家库失败
     * @return
     */
    public List<TransferCaseQueue> selectFailedList(String labServerNo, int transferFailedStatus);


    /**
     * 更新案件上报队列状态
     * @param transferCaseQueue
     */
    public void updateStatus(TransferCaseQueue transferCaseQueue);

}
