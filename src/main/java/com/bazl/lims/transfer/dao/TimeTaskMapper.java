package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.TimeTask;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/6/23.
 */
@Repository
public interface TimeTaskMapper {


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
