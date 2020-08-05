package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import com.bazl.lims.transfer.model.po.TransferPersonQueue;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface TransferPersonQueueService {

    /**
     * 获取待处理的人员上报队列
     * @return
     */
    public List<TransferPersonQueue> selectPendingList(String labServerNo);


    /**
     * 获取失败的人员上报队列
     * @param transferFailedStatus  -1：生成文件失败；-2：上报国家库失败
     * @return
     */
    public List<TransferPersonQueue> selectFailedList(String labServerNo, int transferFailedStatus);


    /**
     * 更新人员上报队列状态
     * @param transferPersonQueue
     */
    public void updateStatus(TransferPersonQueue transferPersonQueue);

}
