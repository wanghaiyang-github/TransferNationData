package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.TimeTask;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/6/23.
 */
public interface TimeTaskService {

    /**
     * 插入信息
     * @param timeTask
     * @return
     */
    public int insert(TimeTask timeTask);

    /**
     * 更新信息
     * @param timeTask
     * @return
     */
    public int update(TimeTask timeTask);

    /**
     * 根据条件查询信息
     * @param timeTask
     * @return
     */
    public List<TimeTask> selectList(TimeTask timeTask);

}
