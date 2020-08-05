package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2019/6/18.
 */
@Repository
public interface OldLimsCaseQueueMapper {

    List<TransferCaseQueue> select(TransferCaseQueue transferCaseQueue);

    int updateStatus(TransferCaseQueue queueSample);

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
