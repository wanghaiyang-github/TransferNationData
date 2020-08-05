package com.bazl.lims.transfer.task;
/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
public class TaskStaticFlag {

    /**
     * 案件上报运行中
     */
    public static boolean TRANSFER_CASE_RUUNING = false;

    /**
     * 人员上报运行中
     */
    public static boolean TRANSFER_PERSON_RUUNING = false;


    /**
     * 更新案件样本入国家库编号运行中
     */
    public static boolean RETRIEVE_CASE_SAMPLE_RUNNING = false;


    /**
     * 更新建库样本入国家库编号运行中
     */
    public static boolean RETRIEVE_PERSON_SAMPLE_RUNNING = false;


    /**
     * 读取Ystr基因型运行中
     */
    public static boolean TRANSFER_YSTR_RUNNING = false;


    /**
     * 读取已经是入库的检材列表运行中
     */
    public static boolean TRANSFER_NOT_INSTORE_RUNNING = false;

}
