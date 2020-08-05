package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import com.bazl.lims.transfer.model.po.TransferPersonQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface TransferPersonQueueMapper {

    List<TransferPersonQueue> select(TransferPersonQueue transferPersonQueue);

    int updateStatus(TransferPersonQueue queueSample);

}
