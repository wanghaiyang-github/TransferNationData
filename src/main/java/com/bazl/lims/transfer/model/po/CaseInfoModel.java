package com.bazl.lims.transfer.model.po;

import com.bazl.lims.transfer.model.bo.ExternalSysInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by lizhihua on 2019/6/9.
 */
public class CaseInfoModel implements Serializable {

    private String id;

    private String externalSysType;
    private String externalSysNo;
    private String caseName;
    private String caseType;
    private String caseProperty;
    private String occurrenceDateStr;
    private String sceneRegionalism;
    private String scenePlace;
    private String caseLevel;
    private String caseSummary;
    private String caseStatus;
    private String category;
    private String sceneAreaType;
    private String estimatedDeath;
    private String estimatedWounded;
    private String createUser;
    private Date createDatetime;
    private String createDatetimeStr;
    private String updateUser;
    private Date updateDatetime;
    private String updateDatetimeStr;
    private String caseNo;
    private String labId;
    private String initServerNo;
    private String externalSysName;
    private Date occurrenceDate;
    private String harmLevel;
    private String caseInfoOrigin;
    private String caseLabNo;
    private String storeFlag;
    private Date transferDate;
    private String remark;
    private String deleteFlag;
    private String transferFlag;
    private String appendSn;
    private String acceptor;
    private String acceptorName;
    private String identifyRequired;
    private String acceptPhone;
    private String delegateSn;
    private Date delegateAt;
    private String delegateOrgCode;
    private String acceptOrgPhone;
    private String delegatorCardId;
    private String delegatorAddress;
    private String delegateOrgPostNo;
    private String delegator;
    private String delegateOrgPhone;
    private String delegatePhone;
    private String caseXkSn;
    private String caseBaseId;
    private String caseOldSn;
    private String jzNo;
    private String xckyCallbackURL;
    private String xckyConsignNo;

    private List<ExternalSysInfo> externalSysInfoList;

    private String caseId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExternalSysType() {
        return externalSysType;
    }

    public void setExternalSysType(String externalSysType) {
        this.externalSysType = externalSysType;
    }

    public String getExternalSysNo() {
        return externalSysNo;
    }

    public void setExternalSysNo(String externalSysNo) {
        this.externalSysNo = externalSysNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getOccurrenceDateStr() {
        return occurrenceDateStr;
    }

    public void setOccurrenceDateStr(String occurrenceDateStr) {
        this.occurrenceDateStr = occurrenceDateStr;
    }

    public String getSceneRegionalism() {
        return sceneRegionalism;
    }

    public void setSceneRegionalism(String sceneRegionalism) {
        this.sceneRegionalism = sceneRegionalism;
    }

    public String getScenePlace() {
        return scenePlace;
    }

    public void setScenePlace(String scenePlace) {
        this.scenePlace = scenePlace;
    }

    public String getCaseLevel() {
        return caseLevel;
    }

    public void setCaseLevel(String caseLevel) {
        this.caseLevel = caseLevel;
    }

    public String getCaseSummary() {
        return caseSummary;
    }

    public void setCaseSummary(String caseSummary) {
        this.caseSummary = caseSummary;
    }

    public String getCaseStatus() {
        return caseStatus;
    }

    public void setCaseStatus(String caseStatus) {
        this.caseStatus = caseStatus;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSceneAreaType() {
        return sceneAreaType;
    }

    public void setSceneAreaType(String sceneAreaType) {
        this.sceneAreaType = sceneAreaType;
    }

    public String getEstimatedDeath() {
        return estimatedDeath;
    }

    public void setEstimatedDeath(String estimatedDeath) {
        this.estimatedDeath = estimatedDeath;
    }

    public String getEstimatedWounded() {
        return estimatedWounded;
    }

    public void setEstimatedWounded(String estimatedWounded) {
        this.estimatedWounded = estimatedWounded;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetimeStr() {
        return createDatetimeStr;
    }

    public void setCreateDatetimeStr(String createDatetimeStr) {
        this.createDatetimeStr = createDatetimeStr;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getUpdateDatetimeStr() {
        return updateDatetimeStr;
    }

    public void setUpdateDatetimeStr(String updateDatetimeStr) {
        this.updateDatetimeStr = updateDatetimeStr;
    }

    public String getCaseNo() {
        return caseNo;
    }

    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getInitServerNo() {
        return initServerNo;
    }

    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo;
    }

    public String getExternalSysName() {
        return externalSysName;
    }

    public void setExternalSysName(String externalSysName) {
        this.externalSysName = externalSysName;
    }

    public Date getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(Date occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }

    public String getHarmLevel() {
        return harmLevel;
    }

    public void setHarmLevel(String harmLevel) {
        this.harmLevel = harmLevel;
    }

    public String getCaseInfoOrigin() {
        return caseInfoOrigin;
    }

    public void setCaseInfoOrigin(String caseInfoOrigin) {
        this.caseInfoOrigin = caseInfoOrigin;
    }

    public String getCaseLabNo() {
        return caseLabNo;
    }

    public void setCaseLabNo(String caseLabNo) {
        this.caseLabNo = caseLabNo;
    }

    public String getStoreFlag() {
        return storeFlag;
    }

    public void setStoreFlag(String storeFlag) {
        this.storeFlag = storeFlag;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getAppendSn() {
        return appendSn;
    }

    public void setAppendSn(String appendSn) {
        this.appendSn = appendSn;
    }

    public String getAcceptor() {
        return acceptor;
    }

    public void setAcceptor(String acceptor) {
        this.acceptor = acceptor;
    }

    public String getAcceptorName() {
        return acceptorName;
    }

    public void setAcceptorName(String acceptorName) {
        this.acceptorName = acceptorName;
    }

    public String getIdentifyRequired() {
        return identifyRequired;
    }

    public void setIdentifyRequired(String identifyRequired) {
        this.identifyRequired = identifyRequired;
    }

    public String getAcceptPhone() {
        return acceptPhone;
    }

    public void setAcceptPhone(String acceptPhone) {
        this.acceptPhone = acceptPhone;
    }

    public String getDelegateSn() {
        return delegateSn;
    }

    public void setDelegateSn(String delegateSn) {
        this.delegateSn = delegateSn;
    }

    public Date getDelegateAt() {
        return delegateAt;
    }

    public void setDelegateAt(Date delegateAt) {
        this.delegateAt = delegateAt;
    }

    public String getDelegateOrgCode() {
        return delegateOrgCode;
    }

    public void setDelegateOrgCode(String delegateOrgCode) {
        this.delegateOrgCode = delegateOrgCode;
    }

    public String getAcceptOrgPhone() {
        return acceptOrgPhone;
    }

    public void setAcceptOrgPhone(String acceptOrgPhone) {
        this.acceptOrgPhone = acceptOrgPhone;
    }

    public String getDelegatorCardId() {
        return delegatorCardId;
    }

    public void setDelegatorCardId(String delegatorCardId) {
        this.delegatorCardId = delegatorCardId;
    }

    public String getDelegatorAddress() {
        return delegatorAddress;
    }

    public void setDelegatorAddress(String delegatorAddress) {
        this.delegatorAddress = delegatorAddress;
    }

    public String getDelegateOrgPostNo() {
        return delegateOrgPostNo;
    }

    public void setDelegateOrgPostNo(String delegateOrgPostNo) {
        this.delegateOrgPostNo = delegateOrgPostNo;
    }

    public String getDelegator() {
        return delegator;
    }

    public void setDelegator(String delegator) {
        this.delegator = delegator;
    }

    public String getDelegateOrgPhone() {
        return delegateOrgPhone;
    }

    public void setDelegateOrgPhone(String delegateOrgPhone) {
        this.delegateOrgPhone = delegateOrgPhone;
    }

    public String getDelegatePhone() {
        return delegatePhone;
    }

    public void setDelegatePhone(String delegatePhone) {
        this.delegatePhone = delegatePhone;
    }

    public String getCaseXkSn() {
        return caseXkSn;
    }

    public void setCaseXkSn(String caseXkSn) {
        this.caseXkSn = caseXkSn;
    }

    public String getCaseBaseId() {
        return caseBaseId;
    }

    public void setCaseBaseId(String caseBaseId) {
        this.caseBaseId = caseBaseId;
    }

    public String getCaseOldSn() {
        return caseOldSn;
    }

    public void setCaseOldSn(String caseOldSn) {
        this.caseOldSn = caseOldSn;
    }

    public String getJzNo() {
        return jzNo;
    }

    public void setJzNo(String jzNo) {
        this.jzNo = jzNo;
    }

    public String getXckyCallbackURL() {
        return xckyCallbackURL;
    }

    public void setXckyCallbackURL(String xckyCallbackURL) {
        this.xckyCallbackURL = xckyCallbackURL;
    }

    public String getXckyConsignNo() {
        return xckyConsignNo;
    }

    public void setXckyConsignNo(String xckyConsignNo) {
        this.xckyConsignNo = xckyConsignNo;
    }

    public List<ExternalSysInfo> getExternalSysInfoList() {
        return externalSysInfoList;
    }

    public void setExternalSysInfoList(List<ExternalSysInfo> externalSysInfoList) {
        this.externalSysInfoList = externalSysInfoList;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }
}
