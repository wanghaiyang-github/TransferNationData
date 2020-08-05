package com.bazl.lims.transfer.model.po;

/**
 * @author wanghaiyang
 * @date 2019/6/23.
 */
public class TimeTask {

    /* 主键id **/
    private String id;

    /* 定时任务类型 **/
    private String timeTaskType;

    /* 定时任务次数 **/
    private Integer timeCount;

    /* 开始数量 **/
    private Integer startCount;

    /* 结束数量 **/
    private Integer endCount;

    /* 读取数量 **/
    private Integer readCount;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeTaskType() {
        return timeTaskType;
    }

    public void setTimeTaskType(String timeTaskType) {
        this.timeTaskType = timeTaskType;
    }

    public Integer getTimeCount() {
        return timeCount;
    }

    public void setTimeCount(Integer timeCount) {
        this.timeCount = timeCount;
    }

    public Integer getStartCount() {
        return startCount;
    }

    public void setStartCount(Integer startCount) {
        this.startCount = startCount;
    }

    public Integer getEndCount() {
        return endCount;
    }

    public void setEndCount(Integer endCount) {
        this.endCount = endCount;
    }

    public Integer getReadCount() {
        return readCount;
    }

    public void setReadCount(Integer readCount) {
        this.readCount = readCount;
    }
}
