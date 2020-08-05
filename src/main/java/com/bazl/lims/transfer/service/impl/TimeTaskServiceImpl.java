package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.dao.TimeTaskMapper;
import com.bazl.lims.transfer.model.po.TimeTask;
import com.bazl.lims.transfer.service.TimeTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wanghaiyang
 * @date 2019/6/23.
 */
@Service
public class TimeTaskServiceImpl extends BaseService implements TimeTaskService {

    @Autowired
    TimeTaskMapper timeTaskMapper;

    public int insert(TimeTask timeTask) {
        try {
            return timeTaskMapper.insert(timeTask);
        }catch (Exception ex) {
            logger.error("invoke TimeTaskService.insert error, param is " + timeTask.getId() + ".", ex);
            return 0;
        }
    }

    public int update(TimeTask timeTask) {
        try {
            return timeTaskMapper.update(timeTask);
        }catch (Exception ex) {
            logger.error("invoke TimeTaskService.update error, param is " + timeTask.getId() + ".", ex);
            return 0;
        }
    }

    public List<TimeTask> selectList(TimeTask timeTask) {
        try {
            return timeTaskMapper.selectList(timeTask);
        }catch (Exception ex) {
            logger.error("invoke TimeTaskService.selectList error, param is " + timeTask.getId() + ".", ex);
            return null;
        }
    }
}
