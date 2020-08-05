package com.bazl.lims.transfer.constants;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/9.
 */
public class TransferConstants implements Serializable {

    /**
     * lims版本
     */
    public static final String LIMS_VERSION_OLD = "0";
    public static final String LIMS_VERSION_SHUNYI = "1";
    public static final String LIMS_VERSION_ALLNEW = "2";


    /**
     * 待处理
     */
    public static final int TRANSFER_STATUS_PENDING = 0;

    /**
     * 生成文件失败
     */
    public static final int TRANSFER_STATUS_GENERATE_ERROR = -1;

    /**
     * 上报失败
     */
    public static final int TRANSFER_STATUS_UPLOAD_ERROR = -2;

    /**
     * 生成文件成功
     */
    public static final int TRANSFER_STATUS_GENERATE_SUCCEED = 1;

    /**
     * 上报成功
     */
    public static final int TRANSFER_STATUS_UPLOAD_SUCCEED = 2;

    /**
     * 案件队列
     */
    public static final String QUEUE_TYPE_15 = "15";

    /**
     * 建库队列
     */
    public static final String QUEUE_TYPE_16 = "16";


    /**
     * 旧系统刑事案件类别
     */
    //二版本案件
    public static final String CASE_TYPE_CRIMINAL_OLD_1 = "1";
    //命案与上级交办案件
    public static final String CASE_TYPE_CRIMINAL_OLD_2 = "2";

    /**
     * 新系统刑事案件类别
     */
    public static final String CASE_TYPE_CRIMINAL_NEW_1 = "01";



    /**
     * 旧系统非刑事案件类别
     */
    //涉黑人员登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_OLD_3 = "3";
    //违法犯罪登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_OLD_5 = "5";
    //质控人员登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_OLD_8 = "8";

    /**
     * 新系统非刑事案件类别
     */
    //涉黑人员登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_NEW_2 = "02";
    //违法犯罪登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_NEW_4 = "04";
    //质控人员登记案件
    public static final String CASE_TYPE_NOT_CRIMINAL_NEW_5 = "05";



    /**
     * 旧系统民事案件类别
     */
    //亲缘鉴定登记案件
    public static final String CASE_TYPE_CIVIL_OLD_4 = "4";

    /**
     * 新系统民事案件类别
     */
    //亲缘鉴定登记案件
    public static final String CASE_TYPE_CIVIL_NEW_3 = "03";



    /*      案件性质        **/
    /**
     * 老系统字典
     */
    public static final String CASE_PROPERTY_OLD_XIONGSHA = "1";
    public static final String CASE_PROPERTY_OLD_SHANGHAI = "2";
    public static final String CASE_PROPERTY_OLD_QITADAOQIE = "3";
    public static final String CASE_PROPERTY_OLD_QIANGDUOQIANGJIE = "4";
    public static final String CASE_PROPERTY_OLD_QIANGJIAN = "5";
    public static final String CASE_PROPERTY_OLD_ZOUSHIRENKOU = "6";
    public static final String CASE_PROPERTY_OLD_SHANGHAIZHISI = "7";
    public static final String CASE_PROPERTY_OLD_ZHIAN = "8";
    public static final String CASE_PROPERTY_OLD_QITA = "9";
    public static final String CASE_PROPERTY_OLD_JIAOTONGSHIGU = "10";
    public static final String CASE_PROPERTY_OLD_DAGUAI = "11";
    public static final String CASE_PROPERTY_OLD_SHIYUANRENDING = "12";
    public static final String CASE_PROPERTY_OLD_RUSHIDAOQIE = "13";
    public static final String CASE_PROPERTY_OLD_DAOQIEJIDONGCHE = "14";
    public static final String CASE_PROPERTY_OLD_DAOQIECHENEICAIWU = "15";
    public static final String CASE_PROPERTY_OLD_PALOUZUANCHUANG = "17";
    public static final String CASE_PROPERTY_OLD_DAOQIEBAOXIANGUI = "18";
    public static final String CASE_PROPERTY_OLD_ZHAPIAN = "19";

    /**
     * 新系统字典
     */
    public static final String CASE_PROPERTY_NEW_XIONGSHA = "01";
    public static final String CASE_PROPERTY_NEW_SHANGHAI = "02";
    public static final String CASE_PROPERTY_NEW_QITADAOQIE = "03";
    public static final String CASE_PROPERTY_NEW_QIANGJIE = "04";
    public static final String CASE_PROPERTY_NEW_QIANGJIAN = "05";
    public static final String CASE_PROPERTY_NEW_FEIZHENGCHANGSIWANG = "06";
    public static final String CASE_PROPERTY_NEW_SHANGHAIZHISI = "07";
    public static final String CASE_PROPERTY_NEW_ZHIAN = "08";
    public static final String CASE_PROPERTY_NEW_ZOUSHIRENKOU = "09";
    public static final String CASE_PROPERTY_NEW_DAGUAI = "10";
    public static final String CASE_PROPERTY_NEW_JIAOTONGSHIGU = "11";
    public static final String CASE_PROPERTY_NEW_SHIYUANRENDING = "12";
    public static final String CASE_PROPERTY_NEW_RUSHIDAOQIE = "13";
    public static final String CASE_PROPERTY_NEW_DAOQIEJIDONGCHE = "14";
    public static final String CASE_PROPERTY_NEW_DAOQIECHENEICAIWU = "15";
    public static final String CASE_PROPERTY_NEW_DAOQIANGGONGDI = "16";
    public static final String CASE_PROPERTY_NEW_PALOUZUANCHUANG = "17";
    public static final String CASE_PROPERTY_NEW_DAOQIEBAOXIANGUI = "18";
    public static final String CASE_PROPERTY_NEW_QINYUAN = "19";
    public static final String CASE_PROPERTY_NEW_QITA = "20";
    public static final String CASE_PROPERTY_NEW_ZHAPIAN = "21";
    public static final String CASE_PROPERTY_NEW_QIANGDUO = "22";


    /**
     * 案件状态
     */
    public static final String CASE_STATUS_OLD_PENDING = "0";
    public static final String CASE_STATUS_OLD_ACCEPTED = "1";
    public static final String CASE_STATUS_OLD_FINISHED = "2";
    public static final String CASE_STATUS_OLD_NOT_RECEIVE = "3";
    public static final String CASE_STATUS_OLD_RECEIVED = "4";
    public static final String CASE_STATUS_OLD_REFUSED = "9";

    public static final String CASE_STATUS_NEW_PENDING = "01";
    public static final String CASE_STATUS_NEW_ACCEPTED = "02";
    public static final String CASE_STATUS_NEW_FINISHED = "03";
    public static final String CASE_STATUS_NEW_REFUSED = "04";


    /**
     * 案件级别
     */
    public static final String CASE_LEVEL_01 = "01";
    public static final String CASE_LEVEL_02 = "02";
    public static final String CASE_LEVEL_03 = "03";
    public static final String CASE_LEVEL_04 = "04";
    public static final String CASE_LEVEL_05 = "05";
    public static final String CASE_LEVEL_06 = "06";

    /**
     * old入库样本类型
     */
    public static final String INSTORE_SAMPLE_TYPE_EVIDENCE_OLD = "1";
    public static final String INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_OLD = "2";
    public static final String INSTORE_SAMPLE_TYPE_OFFENDER_OLD = "3";
    public static final String INSTORE_SAMPLE_TYPE_SUSPECT_OLD = "4";
    public static final String INSTORE_SAMPLE_TYPE_VICTIM_OLD = "5";
    public static final String INSTORE_SAMPLE_TYPE_MISSING_OLD = "6";
    public static final String INSTORE_SAMPLE_TYPE_UNKNOWN_OLD = "7";
    public static final String INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_OLD = "8";
    public static final String INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_OLD = "9";
    public static final String INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_OLD = "10";
    public static final String INSTORE_SAMPLE_TYPE_BASE_OLD = "12";
    public static final String INSTORE_SAMPLE_TYPE_YSTR_OLD = "13";

    /**
     * new入库样本类型
     */
    public static final String INSTORE_SAMPLE_TYPE_EVIDENCE_NEW = "01";
    public static final String INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_NEW = "02";
    public static final String INSTORE_SAMPLE_TYPE_OFFENDER_NEW = "03";
    public static final String INSTORE_SAMPLE_TYPE_SUSPECT_NEW = "04";
    public static final String INSTORE_SAMPLE_TYPE_VICTIM_NEW = "05";
    public static final String INSTORE_SAMPLE_TYPE_MISSING_NEW = "06";
    public static final String INSTORE_SAMPLE_TYPE_UNKNOWN_NEW = "07";
    public static final String INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_NEW = "08";
    public static final String INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_NEW = "09";
    public static final String INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_NEW = "10";
    public static final String INSTORE_SAMPLE_TYPE_BASE_NEW = "11";
    public static final String INSTORE_SAMPLE_TYPE_YSTR_NEW = "12";


    /**
     * 鉴定要求
     */
    public static final String IDENTIFY_REQUIRED_SAME_OLD = "1";
    public static final String IDENTIFY_REQUIRED_RELATIVE_OLD = "2";

    public static final String IDENTIFY_REQUIRED_SAME_NEW = "1";
    public static final String IDENTIFY_REQUIRED_RELATIVE_NEW = "2";

    public static final String IDENTIFY_REQUEST_SAME_NAME = "同一认定";
    public static final String IDENTIFY_REQUEST_RELATIVE_NAME = "亲缘认定";


    /**
     * 样本类型
     */
    //血斑
    public static final String SAMPLE_TYPE_BLOOD_OLD = "1";
    public static final String SAMPLE_TYPE_BLOOD_NEW = "01";
    //精斑
    public static final String SAMPLE_TYPE_SPERM_OLD = "2";
    public static final String SAMPLE_TYPE_SPERM_NEW = "02";
    //毛发
    public static final String SAMPLE_TYPE_HARI_OLD = "3";
    public static final String SAMPLE_TYPE_HARI_NEW = "09";
    //骨骼
    public static final String SAMPLE_TYPE_SKELETON_OLD = "4";
    public static final String SAMPLE_TYPE_SKELETON_NEW = "07";
    //牙齿
    public static final String SAMPLE_TYPE_TOOTH_NEW = "06";
    //唾液斑
    public static final String SAMPLE_TYPE_SALIVA_OLD = "5";
    public static final String SAMPLE_TYPE_SALIVA_NEW = "04";
    //脱落细胞
    public static final String SAMPLE_TYPE_CELLS_OLD = "6";
    public static final String SAMPLE_TYPE_CELLS_NEW = "03";
    //组织
    public static final String SAMPLE_TYPE_TISSUE_NEW = "08";
    //其它
    public static final String SAMPLE_TYPE_OTHER_OLD = "7";
    public static final String SAMPLE_TYPE_OTHER_NEW = "99";


    /**
     * 上报国家库样本默认状态7 ： 已审核
     */
    public static final String DEFAULT_NATION_SAMPLE_STATUS = "7";


    /**
     * 国家库样本类别：物证
     */
    public static final String NATION_SAMPLE_CATEGORY_EVIDENCE = "020101";

    /**
     * 国家库样本类别：违法犯罪人员
     */
    public static final String NATION_SAMPLE_CATEGORY_OFFENDER = "010102";

    /**
     * 国家库样本类别：嫌疑人
     */
    public static final String NATION_SAMPLE_CATEGORY_SUSPECT = "020203";

    /**
     * 国家库样本类别：受害人
     */
    public static final String NATION_SAMPLE_CATEGORY_VICTIM = "020201";

    /**
     * 国家库样本类别：失踪人口
     */
    public static final String NATION_SAMPLE_CATEGORY_MISSING = "010306";
    /**
     * 国家库样本类别：失踪受害人        （LIMS中的失踪人员按照失踪受害人上报）
     */
    public static final String NATION_SAMPLE_CATEGORY_MISSING_VICTIM = "020202";

    /**
     * 国家库样本类别：无名尸
     */
    public static final String NATION_SAMPLE_CATEGORY_KNOWN = "020102";

    /**
     * 国家库样本类别：嫌疑人亲属、受害人亲属、失踪人口亲属
     */
    public static final String NATION_SAMPLE_CATEGORY_CASE_PERSONS = "020205";


    /**
     * 国家库性别 1：男，2：女,0：未知
     */
    public static final String NATION_GENDER_UNKNOWN = "0";
    public static final String NATION_GENDER_MALE = "1";
    public static final String NATION_GENDER_FEMALE = "2";


    /**
     * 新版lims 1：男，2：女
     */
    public static final String OLD_LIMS_GENDER_1 = "1";
    public static final String OLD_LIMS_GENDER_2 = "2";


    /**
     * 老版lims 01：男，02：女,03：未知
     */
    public static final String GENDER_01 = "01";
    public static final String GENDER_02 = "02";
    public static final String GENDER_03 = "03";

    public static final String RELATION_TYPE_SINGLE = "6";

    /**
     * 寻找配偶：2或子女：3
     */
    public static final int FIND_SPOUSE = 2;
    public static final int FIND_CHILD = 3;

    /**
     * 国家库亲缘关系
     */
    public static final String RELATION_WITH_TARGET_FATHER = "1";
    public static final String RELATION_WITH_TARGET_MOTHER = "2";
    public static final String RELATION_WITH_TARGET_CHILDREN = "3";
    public static final String RELATION_WITH_TARGET_MALE_HALF = "7";
    public static final String RELATION_WITH_TARGET_FEMALE_HALF = "8";
    public static final String RELATION_WITH_TARGET_OTHER = "10";

    //人员亲缘类型
    /** 父亲 */
    public final static String RELATION_TYPE_01  = "01";
    /** 母亲 */
    public final static String RELATION_TYPE_02  = "02";
    /** 丈夫 */
    public final static String RELATION_TYPE_03  = "03";
    /** 妻子 */
    public final static String RELATION_TYPE_04  = "04";
    /** 儿子 */
    public final static String RELATION_TYPE_05  = "05";
    /** 女儿 */
    public final static String RELATION_TYPE_06  = "06";

    /**
     * 国家库基因类别：1：STR，2：YSTR
     */
    public static final String GENE_TYPE_STR = "1";
    public static final String GENE_TYPE_YSTR = "2";

    //常规类型：国家库类型为：1
    public final static String GENE_NORMAL = "0";
    public final static String GENE_LADDER = "1";
    public final static String GENE_YANG = "2";
    public final static String GENE_YIN = "3";
    //混合类型：国家库类型为：1
    public final static String GENE_MIXED = "4";
    //Y基因型类型：国家库类型为：2
    public final static String GENE_YSTR = "5";

    public static final String LAB_SERVER_NO_BEIJING = "110000";
    public static final String LAB_SERVER_NO_FYZHX = "110230";
    public static final String LAB_SERVER_NO_DCH = "110101";
    public static final String LAB_SERVER_NO_XCH = "110102";
    public static final String LAB_SERVER_NO_CHY = "110105";
    public static final String LAB_SERVER_NO_FT = "110106";
    public static final String LAB_SERVER_NO_SHJSH = "110107";
    public static final String LAB_SERVER_NO_HD = "110108";
    public static final String LAB_SERVER_NO_TZ = "110112";
    public static final String LAB_SERVER_NO_SHY = "110113";
    public static final String LAB_SERVER_NO_CHP = "110114";
    public static final String LAB_SERVER_NO_DX = "110115";




    public static final String DELEGATE_ORG_DCH = "110101";
    public static final String DELEGATE_ORG_XCH = "110102";
    public static final String DELEGATE_ORG_CHY = "110105";
    public static final String DELEGATE_ORG_FT = "110106";
    public static final String DELEGATE_ORG_SHJSH = "110107";
    public static final String DELEGATE_ORG_HD = "110108";
    public static final String DELEGATE_ORG_MTG = "110109";
    public static final String DELEGATE_ORG_FSH = "110111";
    public static final String DELEGATE_ORG_TZ = "110112";
    public static final String DELEGATE_ORG_SHY = "110113";
    public static final String DELEGATE_ORG_CHP = "110114";
    public static final String DELEGATE_ORG_DX = "110115";
    public static final String DELEGATE_ORG_HR = "110116";
    public static final String DELEGATE_ORG_PG = "110117";
    public static final String DELEGATE_ORG_MY = "110228";
    public static final String DELEGATE_ORG_YQ = "110229";


    public static final int NATION_SAMPLE_STATE_FALSE = 0;
    public static final int NATION_SAMPLE_STATE_TRUE = 1;

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
}
