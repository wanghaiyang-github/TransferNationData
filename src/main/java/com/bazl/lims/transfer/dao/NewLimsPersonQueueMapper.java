package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.TransferPersonQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * @date 2019/6/18.
 */
@Repository
public interface NewLimsPersonQueueMapper {

    List<TransferPersonQueue> select(TransferPersonQueue transferPersonQueue);

    int updateStatus(TransferPersonQueue queueSample);

    /**
     * 插入队列
     * @param transferPersonQueue
     * @return
     */
    public int insertQueueType(TransferPersonQueue transferPersonQueue);

    /**
     * 查询队列信息
     * @param transferPersonQueue
     * @return
     */
//    public List<TransferPersonQueue> selectList(TransferPersonQueue transferPersonQueue);
}
