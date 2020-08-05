package com.bazl.lims.transfer.task;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.utils.StringUtils;

/**
 * Created by Administrator on 2019/6/9.
 */
public class TransferHelper {


    /**
     * 判断案件类型是否为刑事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeCriminal(String limsVersion, String caseType){
        if(TransferConstants.LIMS_VERSION_OLD.equals(limsVersion)){
            return TransferConstants.CASE_TYPE_CRIMINAL_OLD_1.equals(caseType)
                    || TransferConstants.CASE_TYPE_CRIMINAL_OLD_2.equals(caseType);
        }else {
            return TransferConstants.CASE_TYPE_CRIMINAL_NEW_1.equals(caseType);
        }
    }

    /**
     * 判断案件类型是否为非刑事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeNotCriminal(String limsVersion, String caseType){
        if(TransferConstants.LIMS_VERSION_OLD.equals(limsVersion)) {
            return TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_3.equals(caseType)
                    || TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_5.equals(caseType)
                    || TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_8.equals(caseType);
        }else{
            return TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_2.equals(caseType)
                    || TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_4.equals(caseType)
                    || TransferConstants.CASE_TYPE_NOT_CRIMINAL_NEW_5.equals(caseType);
        }
    }


    /**
     * 判断案件类型是否为民事案件
     * @param caseType
     * @return
     */
    public static boolean isCaseTypeCivil(String limsVersion, String caseType){
        if(TransferConstants.LIMS_VERSION_OLD.equals(limsVersion)) {
            return TransferConstants.CASE_TYPE_CIVIL_OLD_4.equals(caseType);
        }else{
            return TransferConstants.CASE_TYPE_CIVIL_NEW_3.equals(caseType);
        }
    }

    /**
     * 判断样本入库类型是否为物证（或混合物证）
     * @return
     */
    public static boolean isInstoreSampleTypeEvidence(String limsVersion, String instoreSampleType, String evidenceNo){
        if(TransferConstants.LIMS_VERSION_OLD.equals(limsVersion)){
            if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE_OLD.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_OLD.equals(instoreSampleType)
                    || (TransferConstants.INSTORE_SAMPLE_TYPE_YSTR_OLD.equals(instoreSampleType)
                    && StringUtils.isNotBlank(evidenceNo))){
                return true;
            }
        }else if (TransferConstants.LIMS_VERSION_SHUNYI.equals(limsVersion)){
            if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE_NEW.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_NEW.equals(instoreSampleType)
                    || (TransferConstants.INSTORE_SAMPLE_TYPE_YSTR_NEW.equals(instoreSampleType)
                    && StringUtils.isNotBlank(evidenceNo))){
                return true;
            }
        }

        return false;
    }


    /**
     * 将案件性质转换为国家库对应案件性质
     * @param limsVersion
     * @param caseProperty
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseProperty(String limsVersion, String caseProperty, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseProperty)){
            return caseInfoConfigs.getDefaultCaseProperty();
        }

        if(TransferConstants.LIMS_VERSION_OLD.equals(limsVersion)) {
            /**
             * 凶杀、伤害致死  转为 杀人
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_XIONGSHA)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_SHANGHAIZHISI)) {
                return caseInfoConfigs.getCasePropertyShaRen();
            }

            /**
             * 伤害  转为 伤害
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_SHANGHAI)) {
                return caseInfoConfigs.getCasePropertyShangHai();
            }

            /**
             * 其它盗窃、入室盗窃、盗窃机动车、盗窃车内财物、爬楼钻窗、盗窃保险柜、盗抢工地   转为 盗窃
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_QITADAOQIE)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_RUSHIDAOQIE)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_DAOQIEJIDONGCHE)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_DAOQIECHENEICAIWU)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_PALOUZUANCHUANG)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_DAOQIEBAOXIANGUI)) {
                return caseInfoConfigs.getCasePropertyDaoQie();
            }

            /**
             * 抢夺、抢劫 转为 抢劫
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_QIANGDUOQIANGJIE)) {
                return caseInfoConfigs.getCasePropertyQiangjie();
            }

            /**
             * 强奸 转为 强奸
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_QIANGJIAN)) {
                return caseInfoConfigs.getCasePropertyQiangJian();
            }

            /**
             * 交通事故 转为 交通
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_JIAOTONGSHIGU)) {
                return caseInfoConfigs.getCasePropertyJiaoTong();
            }

            /**
             * 诈骗 转为诈骗
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_ZHAPIAN)) {
                return caseInfoConfigs.getCasePropertyZhaPian();
            }


            /**
             * 走失人口、治安、打拐、尸源认定、非正常死亡、其它、亲缘  转为 其它
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_ZOUSHIRENKOU)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_ZHIAN)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_QITA)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_DAGUAI)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_OLD_SHIYUANRENDING)) {
                return caseInfoConfigs.getCasePropertyQiTa();
            }

            return caseInfoConfigs.getDefaultCaseProperty();
        }else{
            /**
             * 凶杀、伤害致死  转为 杀人
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_XIONGSHA)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHANGHAIZHISI)) {
                return caseInfoConfigs.getCasePropertyShaRen();
            }

            /**
             * 伤害  转为 伤害
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHANGHAI)) {
                return caseInfoConfigs.getCasePropertyShangHai();
            }

            /**
             * 其它盗窃、入室盗窃、盗窃机动车、盗窃车内财物、爬楼钻窗、盗窃保险柜、盗抢工地   转为 盗窃
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITADAOQIE)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_RUSHIDAOQIE)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIECHENEICAIWU)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIANGGONGDI)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_PALOUZUANCHUANG)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAOQIEBAOXIANGUI)) {
                return caseInfoConfigs.getCasePropertyDaoQie();
            }

            /**
             * 抢夺、抢劫 转为 抢劫
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGDUO)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGJIE)) {
                return caseInfoConfigs.getCasePropertyQiangjie();
            }

            /**
             * 强奸 转为 强奸
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QIANGJIAN)) {
                return caseInfoConfigs.getCasePropertyQiangJian();
            }

            /**
             * 交通事故 转为 交通
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_JIAOTONGSHIGU)) {
                return caseInfoConfigs.getCasePropertyJiaoTong();
            }


            /**
             * 诈骗 转为诈骗
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZHAPIAN)) {
                return caseInfoConfigs.getCasePropertyZhaPian();
            }


            /**
             * 走失人口、治安、打拐、尸源认定、非正常死亡、其它、亲缘  转为 其它
             */
            if (caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZOUSHIRENKOU)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_FEIZHENGCHANGSIWANG)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_ZHIAN)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITA)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_DAGUAI)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_SHIYUANRENDING)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QINYUAN)
                    || caseProperty.equals(TransferConstants.CASE_PROPERTY_NEW_QITA)) {
                return caseInfoConfigs.getCasePropertyQiTa();
            }

            return caseInfoConfigs.getDefaultCaseProperty();
        }

    }

    /**
     * 将案件状态转换为国家库对应案件状态
     * @param limsVersion
     * @param caseStatus
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseStatus(String limsVersion, String caseStatus, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseStatus)){
            return caseInfoConfigs.getCaseStatusDefault();
        }

        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(caseStatus.equals(TransferConstants.CASE_STATUS_OLD_PENDING)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_OLD_ACCEPTED)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_OLD_NOT_RECEIVE)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_OLD_RECEIVED)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_OLD_REFUSED)){
                return caseInfoConfigs.getCaseStatusNotSolved();
            }else if(caseStatus.equals(TransferConstants.CASE_STATUS_OLD_FINISHED)){
                return caseInfoConfigs.getCaseStatusMatched();
            }
        }else{
            if(caseStatus.equals(TransferConstants.CASE_STATUS_NEW_PENDING)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_NEW_ACCEPTED)
                    || caseStatus.equals(TransferConstants.CASE_STATUS_NEW_REFUSED)){
                return caseInfoConfigs.getCaseStatusNotSolved();
            }else if(caseStatus.equals(TransferConstants.CASE_STATUS_NEW_FINISHED)){
                return caseInfoConfigs.getCaseStatusMatched();
            }
        }

        return caseInfoConfigs.getCaseStatusDefault();
    }


    /**
     * 将案件级别转换为国家库对应案件级别
     * @param limsVersion
     * @param caseLevel
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCaseLevel(String limsVersion, String caseLevel, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseLevel)){
            return caseInfoConfigs.getCaseLevelDefault();
        }

        if(limsVersion.equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            if(caseLevel.equals(TransferConstants.CASE_LEVEL_01)){
                return caseInfoConfigs.getCaseLevelSerious();
            }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_02)){
                return caseInfoConfigs.getCaseLevelLarge();
            }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_03)){
                return caseInfoConfigs.getCaseLevelMajor();
            }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_04)){
                return caseInfoConfigs.getCaseLevelGeneral();
            }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_05)){
                return caseInfoConfigs.getCaseLevelSlight();
            }else if(caseLevel.equals(TransferConstants.CASE_LEVEL_06)){
                return caseInfoConfigs.getCaseLevelOther();
            }
        }

        return caseInfoConfigs.getCaseLevelDefault();
    }

    /**
     * 判断当前样本入库类型是否为失踪人口亲属
     * @param limsVersion
     * @param sampleType
     * @return
     */
    public static boolean isSampleTypeMissingPeople(String limsVersion, String sampleType){
        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_OLD.equals(sampleType)){
                return true;
            }
        }else if (limsVersion.equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
            if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_NEW.equals(sampleType)){
                return true;
            }
        }

        return false;
    }


    /**
     * 将委托类型转换为国家库对应委托类型
     * @param limsVersion
     * @param caseType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationConsignmentType(String limsVersion, String caseType, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(caseType)){
            return caseInfoConfigs.getConsignmentTypeDefault();
        }

        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(caseType.equals(TransferConstants.CASE_TYPE_CRIMINAL_OLD_1)
                    || caseType.equals(TransferConstants.CASE_TYPE_CRIMINAL_OLD_2)
                    || caseType.equals(TransferConstants.CASE_TYPE_CIVIL_OLD_4)){
                return caseInfoConfigs.getConsignmentTypeCase();
            }else if(caseType.equals(TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_3)
                    || caseType.equals(TransferConstants.CASE_TYPE_NOT_CRIMINAL_OLD_5)){
                return caseInfoConfigs.getConsignmentTypePerson();
            }
        }else if (limsVersion.equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            if(caseType.equals(TransferConstants.CASE_TYPE_CRIMINAL_NEW_1)){
                return caseInfoConfigs.getConsignmentTypeCase();
            }
        }

        return caseInfoConfigs.getConsignmentTypeDefault();
    }

    /**
     * 转换国家库证件类型
     * @param limsVersion
     * @param credentialType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertToNationCredentialType(String limsVersion, String credentialType, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(credentialType)){
            return caseInfoConfigs.getCredentialTypeDefault();
        }

        if(credentialType.equals("警官证")
                || credentialType.contains("警官")){
            return caseInfoConfigs.getCredentialTypePolicer();
        }


        return caseInfoConfigs.getCredentialTypeDefault();
    }

    /**
     * 转换鉴定要求
     * @param limsVersion
     * @param identifyRequest
     * @return
     */
    public static String convertIdentifyRequest(String limsVersion, String identifyRequest){
        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_SAME_OLD)){
                return TransferConstants.IDENTIFY_REQUEST_SAME_NAME;
            }else if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_RELATIVE_OLD)){
                return TransferConstants.IDENTIFY_REQUEST_RELATIVE_NAME;
            }
        }else{
            if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_SAME_NEW)){
                return TransferConstants.IDENTIFY_REQUEST_SAME_NAME;
            }else if(identifyRequest.equals(TransferConstants.IDENTIFY_REQUIRED_RELATIVE_NEW)){
                return TransferConstants.IDENTIFY_REQUEST_RELATIVE_NAME;
            }
        }

        return TransferConstants.IDENTIFY_REQUEST_SAME_NAME;
    }


    /**
     * 转换物证承载类型
     * @param limsVersion
     * @param evidenceCarrierType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertEvidenceCarrierType(String limsVersion, String evidenceCarrierType, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(evidenceCarrierType)){
            return caseInfoConfigs.getEvidenceCarrierTypeDefault();
        }

        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_BLOOD_OLD)){
                return caseInfoConfigs.getEvidenceCarrierTypeBlood();
            }else if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_SALIVA_OLD)){
                return caseInfoConfigs.getEvidenceCarrierTypeSaliva();
            }else{
                return caseInfoConfigs.getEvidenceCarrierTypeOther();
            }
        }else{
            if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_BLOOD_NEW)){
                return caseInfoConfigs.getEvidenceCarrierTypeBlood();
            }else if(evidenceCarrierType.equals(TransferConstants.SAMPLE_TYPE_SALIVA_NEW)){
                return caseInfoConfigs.getEvidenceCarrierTypeSaliva();
            }else{
                return caseInfoConfigs.getEvidenceCarrierTypeOther();
            }
        }
    }

    /**
     * 转换性别
     * @param limsVersion
     * @param gender
     * @return
     */
    public static String convertNationGener(String limsVersion, String gender){
        if(limsVersion.equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            if(TransferConstants.GENDER_01.equals(gender)){
                return TransferConstants.NATION_GENDER_MALE;
            }else if(TransferConstants.GENDER_02.equals(gender)){
                return TransferConstants.NATION_GENDER_FEMALE;
            }else if(TransferConstants.GENDER_03.equals(gender)){
                return TransferConstants.NATION_GENDER_UNKNOWN;
            }
        }else{
            if(TransferConstants.OLD_LIMS_GENDER_1.equals(gender)){
                return TransferConstants.NATION_GENDER_MALE;
            }else if(TransferConstants.OLD_LIMS_GENDER_2.equals(gender)){
                return TransferConstants.NATION_GENDER_FEMALE;
            }
        }

        return TransferConstants.NATION_GENDER_UNKNOWN;
    }

    /**
     * 转换样本类型
     * @param limsVersion
     * @param sampleType
     * @param caseInfoConfigs
     * @return
     */
    public static String convertNationSampleType(String limsVersion, String sampleType, CaseInfoConfigs caseInfoConfigs){
        if(StringUtils.isBlank(sampleType)){
            return caseInfoConfigs.getSampleTypeDefault();
        }

        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(sampleType.equals(TransferConstants.SAMPLE_TYPE_BLOOD_OLD)){
                return caseInfoConfigs.getSampleTypeBlood();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SALIVA_OLD)){
                return caseInfoConfigs.getSampleTypeSaliva();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_CELLS_OLD)){
                return caseInfoConfigs.getSampleTypeCells();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SPERM_OLD)){
                return caseInfoConfigs.getSampleTypeSperm();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_HARI_OLD)){
                return caseInfoConfigs.getSampleTypeHairs();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SKELETON_OLD)){
                return caseInfoConfigs.getSampleTypeSkeleton();
            }else{
                return caseInfoConfigs.getEvidenceCarrierTypeOther();
            }
        }else{
            if(sampleType.equals(TransferConstants.SAMPLE_TYPE_BLOOD_NEW)){
                return caseInfoConfigs.getSampleTypeBlood();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SALIVA_NEW)){
                return caseInfoConfigs.getSampleTypeSaliva();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_CELLS_NEW)){
                return caseInfoConfigs.getSampleTypeCells();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SPERM_NEW)){
                return caseInfoConfigs.getSampleTypeSperm();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_HARI_NEW)){
                return caseInfoConfigs.getSampleTypeHairs();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_SKELETON_NEW)
                    || sampleType.equals(TransferConstants.SAMPLE_TYPE_TOOTH_NEW)){
                return caseInfoConfigs.getSampleTypeSkeleton();
            }else if(sampleType.equals(TransferConstants.SAMPLE_TYPE_TISSUE_NEW)){
                return caseInfoConfigs.getSampleTypeTissue();
            }else{
                return caseInfoConfigs.getEvidenceCarrierTypeOther();
            }
        }
    }

    public static final String convertNationSampleCategory(String limsVersion, String instoreSampleType){
        if(limsVersion.equals(TransferConstants.LIMS_VERSION_OLD)){
            if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE_OLD.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_OFFENDER_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_OFFENDER;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_SUSPECT;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_VICTIM;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_MISSING_VICTIM;
            }else if (TransferConstants.INSTORE_SAMPLE_TYPE_UNKNOWN_OLD.equals(instoreSampleType)) {
                return TransferConstants.NATION_SAMPLE_CATEGORY_KNOWN;
            } else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_OLD.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_OLD.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_CASE_PERSONS;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_YSTR_OLD.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
            }
        }else if (limsVersion.equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
            if(TransferConstants.INSTORE_SAMPLE_TYPE_EVIDENCE_NEW.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MIX_EVIDENCE_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_OFFENDER_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_OFFENDER;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_SUSPECT;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_VICTIM;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_MISSING_VICTIM;
            }else if (TransferConstants.INSTORE_SAMPLE_TYPE_UNKNOWN_NEW.equals(instoreSampleType)) {
                return TransferConstants.NATION_SAMPLE_CATEGORY_KNOWN;
            } else if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_NEW.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_NEW.equals(instoreSampleType)
                    || TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_CASE_PERSONS;
            }else if(TransferConstants.INSTORE_SAMPLE_TYPE_YSTR_NEW.equals(instoreSampleType)){
                return TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
            }
        }

        return TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE;
    }

    public static String getXckyCallbackUrlByServerNoAndDelegateOrgCode(String delegateOrgCode, TransferNationParamsConfig transferNationParamsConfig){
        if(TransferConstants.LAB_SERVER_NO_BEIJING.equals(transferNationParamsConfig.getLabServerNo())
                || TransferConstants.LAB_SERVER_NO_FYZHX.equals(transferNationParamsConfig.getLabServerNo())){
            if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_DCH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_DCH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_DCH))){
                return transferNationParamsConfig.getXckyCallbackURL_DCH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_XCH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_XCH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_XCH))){
                return transferNationParamsConfig.getXckyCallbackURL_XCH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_CHY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_CHY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_CHY))){
                return transferNationParamsConfig.getXckyCallbackURL_CHY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_FT)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_FT.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_FT))){
                return transferNationParamsConfig.getXckyCallbackURL_FT();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_SHJSH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_SHJSH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_SHJSH))){
                return transferNationParamsConfig.getXckyCallbackURL_SHJSH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_HD)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_HD.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_HD))){
                return transferNationParamsConfig.getXckyCallbackURL_HD();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_MTG)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_MTG.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_MTG))){
                return transferNationParamsConfig.getXckyCallbackURL_MTG();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_FSH)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_FSH.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_FSH))){
                return transferNationParamsConfig.getXckyCallbackURL_FSH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_TZ)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_TZ.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_TZ))){
                return transferNationParamsConfig.getXckyCallbackURL_TZH();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_SHY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_SHY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_SHY))){
                return transferNationParamsConfig.getXckyCallbackURL_SHY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_CHP)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_CHP.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_CHP))){
                return transferNationParamsConfig.getXckyCallbackURL_CHP();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_DX)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_DX.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_DX))){
                return transferNationParamsConfig.getXckyCallbackURL_DX();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_HR)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_HR.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_HR))){
                return transferNationParamsConfig.getXckyCallbackURL_HR();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_PG)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_PG.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_PG))){
                return transferNationParamsConfig.getXckyCallbackURL_PG();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_MY)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_MY.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_MY))){
                return transferNationParamsConfig.getXckyCallbackURL_MY();
            }else if(delegateOrgCode.equals(TransferConstants.DELEGATE_ORG_YQ)
                    || (delegateOrgCode.length() > TransferConstants.DELEGATE_ORG_YQ.length() && delegateOrgCode.contains(TransferConstants.DELEGATE_ORG_YQ))){
                return transferNationParamsConfig.getXckyCallbackURL_YQ();
            }

            return transferNationParamsConfig.getXckyCallbackURL_BEIJING();
        }else if(TransferConstants.LAB_SERVER_NO_DCH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_DCH();
        }else if(TransferConstants.LAB_SERVER_NO_XCH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_XCH();
        }else if(TransferConstants.LAB_SERVER_NO_CHY.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_CHY();
        }else if(TransferConstants.LAB_SERVER_NO_FT.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_FT();
        }else if(TransferConstants.LAB_SERVER_NO_SHJSH.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_SHJSH();
        }else if(TransferConstants.LAB_SERVER_NO_HD.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_HD();
        }else if(TransferConstants.LAB_SERVER_NO_CHP.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_CHP();
        }else if(TransferConstants.LAB_SERVER_NO_DX.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_DX();
        }else if(TransferConstants.LAB_SERVER_NO_TZ.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_TZH();
        }else if(TransferConstants.LAB_SERVER_NO_SHY.equals(transferNationParamsConfig.getLabServerNo())){
            return transferNationParamsConfig.getXckyCallbackURL_SHY();
        }

        return transferNationParamsConfig.getXckyCallbackURL_BEIJING();
    }
}
