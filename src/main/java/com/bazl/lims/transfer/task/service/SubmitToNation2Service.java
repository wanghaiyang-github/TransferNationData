package com.bazl.lims.transfer.task.service;

import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;

import java.util.Map;

/**
 * Created by Administrator on 2019/6/10.
 */
public interface SubmitToNation2Service {

    Map<String, String> submitToNation(SubmitNationDataModel var1) throws Exception;


}
