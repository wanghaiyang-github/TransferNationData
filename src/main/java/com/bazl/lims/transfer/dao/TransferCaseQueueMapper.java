package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface TransferCaseQueueMapper {

    List<TransferCaseQueue> select(TransferCaseQueue transferCaseQueue);

    int updateStatus(TransferCaseQueue queueSample);

}
