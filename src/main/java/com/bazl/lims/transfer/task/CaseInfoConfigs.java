package com.bazl.lims.transfer.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019-05-08.
 */
@Configuration
@ConfigurationProperties(prefix="caseinfo", ignoreUnknownFields = true)
public class CaseInfoConfigs {

    /**
     * 现勘系统类型代码
     */
    private String externalSysTypeXk;

    /**
     * 警综系统类型代码
     */
    private String externalSysTypeJz;

    /**
     * 警情系统类型代码
     */
    private String externalSysTypeJq;

    /**
     * 刑专系统类型代码
     */
    private String externalSysTypeXz;

    /**
     * 无名氏系统类型代码
     */
    private String externalSysTypeWms;

    /**
     * 其它系统类型代码
     */
    private String externalSysTypeQt;


    private String defaultCaseType;
    /**
     * 刑事案件
     */
    private String caseTypeCriminal;
    /**
     * 非刑事案件
     */
    private String caseTypeNotCriminal;
    /**
     * 民事案件
     */
    private String caseTypeCivil;

    /**
     * 默认案件性质
     */
    private String defaultCaseProperty;
    private String casePropertyBuXiang;
    private String casePropertyShaRen;
    private String casePropertyQiangJian;
    private String casePropertyLunJian;
    private String casePropertyDaoQie;
    private String casePropertyShangHai;
    private String casePropertyQiangjie;
    private String casePropertySuiShi;
    private String casePropertyQiangJianShaRen;
    private String casePropertyBaoZha;
    private String casePropertyMinShi;
    private String casePropertyJiaoTong;
    private String casePropertyZongHuo;
    private String casePropertyTouDu;
    private String casePropertyDuPin;
    private String casePropertyYiWai;
    private String casePropertyBangJia;
    private String casePropertyJieChi;
    private String casePropertyZhaPian;
    private String casePropertyFenShi;
    private String casePropertyQiaoZhaLeSuo;
    private String casePropertyQiTa;

    /**
     * 案件状态
     * @return
     */
    private String caseStatusDefault;
    private String caseStatusNotSolved;
    private String caseStatusMatched;
    private String caseStatusSolved;


    /**
     * 委托类型
     * @return
     */
    private String consignmentTypeDefault;
    private String consignmentTypeCase;
    private String consignmentTypeUnknown;
    private String consignmentTypeMissing;
    private String consignmentTypePerson;
    private String consignmentTypeDisaster;


    /**
     * 证件类型
     * @return
     */
    private String credentialTypeDefault;
    private String credentialTypeIdcard;
    private String credentialTypeSoldier;
    private String credentialTypePassport;
    private String credentialTypeStudent;
    private String credentialTypeDriver;
    private String credentialTypeOfficer;
    private String credentialTypePolicer;
    private String credentialTypeOther;


    /**
     * 物证承载物类型
     * @return
     */
    private String evidenceCarrierTypeDefault;
    private String evidenceCarrierTypeOther;
    private String evidenceCarrierTypeBlood;
    private String evidenceCarrierTypeSaliva;


    /**
     * 样本类型
     * @return
     */
    private String sampleTypeDefault;
    private String sampleTypeOther;
    private String sampleTypeBlood;
    private String sampleTypeSperm;
    private String sampleTypeHairs;
    private String sampleTypeSaliva;
    private String sampleTypeSkeleton;
    private String sampleTypeCells;
    private String sampleTypeTissue;


    /**
     * 案件级别
     * @return
     */
    private String caseLevelDefault;
    private String caseLevelSerious;
    private String caseLevelLarge;
    private String caseLevelMajor;
    private String caseLevelGeneral;
    private String caseLevelSlight;
    private String caseLevelOther;




    public String getExternalSysTypeXk() {
        return externalSysTypeXk;
    }

    public void setExternalSysTypeXk(String externalSysTypeXk) {
        this.externalSysTypeXk = externalSysTypeXk;
    }

    public String getExternalSysTypeJz() {
        return externalSysTypeJz;
    }

    public void setExternalSysTypeJz(String externalSysTypeJz) {
        this.externalSysTypeJz = externalSysTypeJz;
    }

    public String getExternalSysTypeJq() {
        return externalSysTypeJq;
    }

    public void setExternalSysTypeJq(String externalSysTypeJq) {
        this.externalSysTypeJq = externalSysTypeJq;
    }

    public String getExternalSysTypeXz() {
        return externalSysTypeXz;
    }

    public void setExternalSysTypeXz(String externalSysTypeXz) {
        this.externalSysTypeXz = externalSysTypeXz;
    }

    public String getExternalSysTypeWms() {
        return externalSysTypeWms;
    }

    public void setExternalSysTypeWms(String externalSysTypeWms) {
        this.externalSysTypeWms = externalSysTypeWms;
    }

    public String getExternalSysTypeQt() {
        return externalSysTypeQt;
    }

    public void setExternalSysTypeQt(String externalSysTypeQt) {
        this.externalSysTypeQt = externalSysTypeQt;
    }

    public String getDefaultCaseType() {
        return defaultCaseType;
    }

    public void setDefaultCaseType(String defaultCaseType) {
        this.defaultCaseType = defaultCaseType;
    }

    public String getCaseTypeCriminal() {
        return caseTypeCriminal;
    }

    public void setCaseTypeCriminal(String caseTypeCriminal) {
        this.caseTypeCriminal = caseTypeCriminal;
    }

    public String getCaseTypeNotCriminal() {
        return caseTypeNotCriminal;
    }

    public void setCaseTypeNotCriminal(String caseTypeNotCriminal) {
        this.caseTypeNotCriminal = caseTypeNotCriminal;
    }

    public String getCaseTypeCivil() {
        return caseTypeCivil;
    }

    public void setCaseTypeCivil(String caseTypeCivil) {
        this.caseTypeCivil = caseTypeCivil;
    }

    public String getDefaultCaseProperty() {
        return defaultCaseProperty;
    }

    public void setDefaultCaseProperty(String defaultCaseProperty) {
        this.defaultCaseProperty = defaultCaseProperty;
    }

    public String getCasePropertyBuXiang() {
        return casePropertyBuXiang;
    }

    public void setCasePropertyBuXiang(String casePropertyBuXiang) {
        this.casePropertyBuXiang = casePropertyBuXiang;
    }

    public String getCasePropertyShaRen() {
        return casePropertyShaRen;
    }

    public void setCasePropertyShaRen(String casePropertyShaRen) {
        this.casePropertyShaRen = casePropertyShaRen;
    }

    public String getCasePropertyQiangJian() {
        return casePropertyQiangJian;
    }

    public void setCasePropertyQiangJian(String casePropertyQiangJian) {
        this.casePropertyQiangJian = casePropertyQiangJian;
    }

    public String getCasePropertyLunJian() {
        return casePropertyLunJian;
    }

    public void setCasePropertyLunJian(String casePropertyLunJian) {
        this.casePropertyLunJian = casePropertyLunJian;
    }

    public String getCasePropertyDaoQie() {
        return casePropertyDaoQie;
    }

    public void setCasePropertyDaoQie(String casePropertyDaoQie) {
        this.casePropertyDaoQie = casePropertyDaoQie;
    }

    public String getCasePropertyShangHai() {
        return casePropertyShangHai;
    }

    public void setCasePropertyShangHai(String casePropertyShangHai) {
        this.casePropertyShangHai = casePropertyShangHai;
    }

    public String getCasePropertyQiangjie() {
        return casePropertyQiangjie;
    }

    public void setCasePropertyQiangjie(String casePropertyQiangjie) {
        this.casePropertyQiangjie = casePropertyQiangjie;
    }

    public String getCasePropertySuiShi() {
        return casePropertySuiShi;
    }

    public void setCasePropertySuiShi(String casePropertySuiShi) {
        this.casePropertySuiShi = casePropertySuiShi;
    }

    public String getCasePropertyQiangJianShaRen() {
        return casePropertyQiangJianShaRen;
    }

    public void setCasePropertyQiangJianShaRen(String casePropertyQiangJianShaRen) {
        this.casePropertyQiangJianShaRen = casePropertyQiangJianShaRen;
    }

    public String getCasePropertyBaoZha() {
        return casePropertyBaoZha;
    }

    public void setCasePropertyBaoZha(String casePropertyBaoZha) {
        this.casePropertyBaoZha = casePropertyBaoZha;
    }

    public String getCasePropertyMinShi() {
        return casePropertyMinShi;
    }

    public void setCasePropertyMinShi(String casePropertyMinShi) {
        this.casePropertyMinShi = casePropertyMinShi;
    }

    public String getCasePropertyJiaoTong() {
        return casePropertyJiaoTong;
    }

    public void setCasePropertyJiaoTong(String casePropertyJiaoTong) {
        this.casePropertyJiaoTong = casePropertyJiaoTong;
    }

    public String getCasePropertyZongHuo() {
        return casePropertyZongHuo;
    }

    public void setCasePropertyZongHuo(String casePropertyZongHuo) {
        this.casePropertyZongHuo = casePropertyZongHuo;
    }

    public String getCasePropertyTouDu() {
        return casePropertyTouDu;
    }

    public void setCasePropertyTouDu(String casePropertyTouDu) {
        this.casePropertyTouDu = casePropertyTouDu;
    }

    public String getCasePropertyDuPin() {
        return casePropertyDuPin;
    }

    public void setCasePropertyDuPin(String casePropertyDuPin) {
        this.casePropertyDuPin = casePropertyDuPin;
    }

    public String getCasePropertyYiWai() {
        return casePropertyYiWai;
    }

    public void setCasePropertyYiWai(String casePropertyYiWai) {
        this.casePropertyYiWai = casePropertyYiWai;
    }

    public String getCasePropertyBangJia() {
        return casePropertyBangJia;
    }

    public void setCasePropertyBangJia(String casePropertyBangJia) {
        this.casePropertyBangJia = casePropertyBangJia;
    }

    public String getCasePropertyJieChi() {
        return casePropertyJieChi;
    }

    public void setCasePropertyJieChi(String casePropertyJieChi) {
        this.casePropertyJieChi = casePropertyJieChi;
    }

    public String getCasePropertyZhaPian() {
        return casePropertyZhaPian;
    }

    public void setCasePropertyZhaPian(String casePropertyZhaPian) {
        this.casePropertyZhaPian = casePropertyZhaPian;
    }

    public String getCasePropertyFenShi() {
        return casePropertyFenShi;
    }

    public void setCasePropertyFenShi(String casePropertyFenShi) {
        this.casePropertyFenShi = casePropertyFenShi;
    }

    public String getCasePropertyQiaoZhaLeSuo() {
        return casePropertyQiaoZhaLeSuo;
    }

    public void setCasePropertyQiaoZhaLeSuo(String casePropertyQiaoZhaLeSuo) {
        this.casePropertyQiaoZhaLeSuo = casePropertyQiaoZhaLeSuo;
    }

    public String getCasePropertyQiTa() {
        return casePropertyQiTa;
    }

    public void setCasePropertyQiTa(String casePropertyQiTa) {
        this.casePropertyQiTa = casePropertyQiTa;
    }

    public String getCaseStatusDefault() {
        return caseStatusDefault;
    }

    public void setCaseStatusDefault(String caseStatusDefault) {
        this.caseStatusDefault = caseStatusDefault;
    }

    public String getCaseStatusNotSolved() {
        return caseStatusNotSolved;
    }

    public void setCaseStatusNotSolved(String caseStatusNotSolved) {
        this.caseStatusNotSolved = caseStatusNotSolved;
    }

    public String getCaseStatusMatched() {
        return caseStatusMatched;
    }

    public void setCaseStatusMatched(String caseStatusMatched) {
        this.caseStatusMatched = caseStatusMatched;
    }

    public String getCaseStatusSolved() {
        return caseStatusSolved;
    }

    public void setCaseStatusSolved(String caseStatusSolved) {
        this.caseStatusSolved = caseStatusSolved;
    }

    public String getConsignmentTypeDefault() {
        return consignmentTypeDefault;
    }

    public void setConsignmentTypeDefault(String consignmentTypeDefault) {
        this.consignmentTypeDefault = consignmentTypeDefault;
    }

    public String getConsignmentTypeCase() {
        return consignmentTypeCase;
    }

    public void setConsignmentTypeCase(String consignmentTypeCase) {
        this.consignmentTypeCase = consignmentTypeCase;
    }

    public String getConsignmentTypeUnknown() {
        return consignmentTypeUnknown;
    }

    public void setConsignmentTypeUnknown(String consignmentTypeUnknown) {
        this.consignmentTypeUnknown = consignmentTypeUnknown;
    }

    public String getConsignmentTypeMissing() {
        return consignmentTypeMissing;
    }

    public void setConsignmentTypeMissing(String consignmentTypeMissing) {
        this.consignmentTypeMissing = consignmentTypeMissing;
    }

    public String getConsignmentTypePerson() {
        return consignmentTypePerson;
    }

    public void setConsignmentTypePerson(String consignmentTypePerson) {
        this.consignmentTypePerson = consignmentTypePerson;
    }

    public String getConsignmentTypeDisaster() {
        return consignmentTypeDisaster;
    }

    public void setConsignmentTypeDisaster(String consignmentTypeDisaster) {
        this.consignmentTypeDisaster = consignmentTypeDisaster;
    }

    public String getCredentialTypeDefault() {
        return credentialTypeDefault;
    }

    public void setCredentialTypeDefault(String credentialTypeDefault) {
        this.credentialTypeDefault = credentialTypeDefault;
    }

    public String getCredentialTypeIdcard() {
        return credentialTypeIdcard;
    }

    public void setCredentialTypeIdcard(String credentialTypeIdcard) {
        this.credentialTypeIdcard = credentialTypeIdcard;
    }

    public String getCredentialTypeSoldier() {
        return credentialTypeSoldier;
    }

    public void setCredentialTypeSoldier(String credentialTypeSoldier) {
        this.credentialTypeSoldier = credentialTypeSoldier;
    }

    public String getCredentialTypePassport() {
        return credentialTypePassport;
    }

    public void setCredentialTypePassport(String credentialTypePassport) {
        this.credentialTypePassport = credentialTypePassport;
    }

    public String getCredentialTypeStudent() {
        return credentialTypeStudent;
    }

    public void setCredentialTypeStudent(String credentialTypeStudent) {
        this.credentialTypeStudent = credentialTypeStudent;
    }

    public String getCredentialTypeDriver() {
        return credentialTypeDriver;
    }

    public void setCredentialTypeDriver(String credentialTypeDriver) {
        this.credentialTypeDriver = credentialTypeDriver;
    }

    public String getCredentialTypeOfficer() {
        return credentialTypeOfficer;
    }

    public void setCredentialTypeOfficer(String credentialTypeOfficer) {
        this.credentialTypeOfficer = credentialTypeOfficer;
    }

    public String getCredentialTypePolicer() {
        return credentialTypePolicer;
    }

    public void setCredentialTypePolicer(String credentialTypePolicer) {
        this.credentialTypePolicer = credentialTypePolicer;
    }

    public String getCredentialTypeOther() {
        return credentialTypeOther;
    }

    public void setCredentialTypeOther(String credentialTypeOther) {
        this.credentialTypeOther = credentialTypeOther;
    }

    public String getEvidenceCarrierTypeDefault() {
        return evidenceCarrierTypeDefault;
    }

    public void setEvidenceCarrierTypeDefault(String evidenceCarrierTypeDefault) {
        this.evidenceCarrierTypeDefault = evidenceCarrierTypeDefault;
    }

    public String getEvidenceCarrierTypeOther() {
        return evidenceCarrierTypeOther;
    }

    public void setEvidenceCarrierTypeOther(String evidenceCarrierTypeOther) {
        this.evidenceCarrierTypeOther = evidenceCarrierTypeOther;
    }

    public String getEvidenceCarrierTypeBlood() {
        return evidenceCarrierTypeBlood;
    }

    public void setEvidenceCarrierTypeBlood(String evidenceCarrierTypeBlood) {
        this.evidenceCarrierTypeBlood = evidenceCarrierTypeBlood;
    }

    public String getEvidenceCarrierTypeSaliva() {
        return evidenceCarrierTypeSaliva;
    }

    public void setEvidenceCarrierTypeSaliva(String evidenceCarrierTypeSaliva) {
        this.evidenceCarrierTypeSaliva = evidenceCarrierTypeSaliva;
    }

    public String getSampleTypeDefault() {
        return sampleTypeDefault;
    }

    public void setSampleTypeDefault(String sampleTypeDefault) {
        this.sampleTypeDefault = sampleTypeDefault;
    }

    public String getSampleTypeOther() {
        return sampleTypeOther;
    }

    public void setSampleTypeOther(String sampleTypeOther) {
        this.sampleTypeOther = sampleTypeOther;
    }

    public String getSampleTypeBlood() {
        return sampleTypeBlood;
    }

    public void setSampleTypeBlood(String sampleTypeBlood) {
        this.sampleTypeBlood = sampleTypeBlood;
    }

    public String getSampleTypeSperm() {
        return sampleTypeSperm;
    }

    public void setSampleTypeSperm(String sampleTypeSperm) {
        this.sampleTypeSperm = sampleTypeSperm;
    }

    public String getSampleTypeHairs() {
        return sampleTypeHairs;
    }

    public void setSampleTypeHairs(String sampleTypeHairs) {
        this.sampleTypeHairs = sampleTypeHairs;
    }

    public String getSampleTypeSaliva() {
        return sampleTypeSaliva;
    }

    public void setSampleTypeSaliva(String sampleTypeSaliva) {
        this.sampleTypeSaliva = sampleTypeSaliva;
    }

    public String getSampleTypeSkeleton() {
        return sampleTypeSkeleton;
    }

    public void setSampleTypeSkeleton(String sampleTypeSkeleton) {
        this.sampleTypeSkeleton = sampleTypeSkeleton;
    }

    public String getSampleTypeCells() {
        return sampleTypeCells;
    }

    public void setSampleTypeCells(String sampleTypeCells) {
        this.sampleTypeCells = sampleTypeCells;
    }

    public String getSampleTypeTissue() {
        return sampleTypeTissue;
    }

    public void setSampleTypeTissue(String sampleTypeTissue) {
        this.sampleTypeTissue = sampleTypeTissue;
    }

    public String getCaseLevelDefault() {
        return caseLevelDefault;
    }

    public void setCaseLevelDefault(String caseLevelDefault) {
        this.caseLevelDefault = caseLevelDefault;
    }

    public String getCaseLevelSerious() {
        return caseLevelSerious;
    }

    public void setCaseLevelSerious(String caseLevelSerious) {
        this.caseLevelSerious = caseLevelSerious;
    }

    public String getCaseLevelLarge() {
        return caseLevelLarge;
    }

    public void setCaseLevelLarge(String caseLevelLarge) {
        this.caseLevelLarge = caseLevelLarge;
    }

    public String getCaseLevelMajor() {
        return caseLevelMajor;
    }

    public void setCaseLevelMajor(String caseLevelMajor) {
        this.caseLevelMajor = caseLevelMajor;
    }

    public String getCaseLevelGeneral() {
        return caseLevelGeneral;
    }

    public void setCaseLevelGeneral(String caseLevelGeneral) {
        this.caseLevelGeneral = caseLevelGeneral;
    }

    public String getCaseLevelSlight() {
        return caseLevelSlight;
    }

    public void setCaseLevelSlight(String caseLevelSlight) {
        this.caseLevelSlight = caseLevelSlight;
    }

    public String getCaseLevelOther() {
        return caseLevelOther;
    }

    public void setCaseLevelOther(String caseLevelOther) {
        this.caseLevelOther = caseLevelOther;
    }
}
