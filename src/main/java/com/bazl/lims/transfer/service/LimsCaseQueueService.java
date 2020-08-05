package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;

import java.util.List;

/**
 * Created by Administrator on 2019-09-01.
 */
public interface LimsCaseQueueService {

    /**
     * 获取待处理的案件上报队列
     * @param labServerNo 当前实验室编号
     * @return
     */
    public List<TransferCaseQueue> selectPendingList(String labServerNo);

    /**
     * 获取所有已生成上报文件的上报队列
     * @param labServerNo
     * @return
     */
    public List<TransferCaseQueue> selectGenerateSucceedList(String labServerNo);


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

    /**
     * 插入队列
     * @param transferCaseQueue
     * @return
     */
    public int insertQueueType(TransferCaseQueue transferCaseQueue);

    /**
     * 插入检材详情队列
     * @param transferCaseQueue
     * @return
     */
    public int insertQueueDetail(TransferCaseQueue transferCaseQueue);

}
