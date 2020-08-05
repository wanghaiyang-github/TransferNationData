package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class PhysicalEvidenceModel implements Serializable {
    private String id;
    private String physicalEvidenceType;
    private String physicalEvidenceName;
    private String description;
    private String remark;
    private String createUser;
    private Date createDatatime;
    private String updateUser;
    private Date updateDatetime;
    private String externalSysType;
    private String externalSysNo;
    private String labId;
    private String initServer_no;
    private String consignmentId;
    private String consignOrgCode;
    private String physicalEvidenceNo;
    private String trustyFlag;
    private String utilization;
    private String secrecy;
    private String transferFlag;
    private String transferUser;
    private String transferDatetime;
    private String deleteFlag;
    private String dataSource;
    private String dataLevel;
    private String updateDatetimeStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhysicalEvidenceType() {
        return physicalEvidenceType;
    }

    public void setPhysicalEvidenceType(String physicalEvidenceType) {
        this.physicalEvidenceType = physicalEvidenceType;
    }

    public String getPhysicalEvidenceName() {
        return physicalEvidenceName;
    }

    public void setPhysicalEvidenceName(String physicalEvidenceName) {
        this.physicalEvidenceName = physicalEvidenceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatatime() {
        return createDatatime;
    }

    public void setCreateDatatime(Date createDatatime) {
        this.createDatatime = createDatatime;
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

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getInitServer_no() {
        return initServer_no;
    }

    public void setInitServer_no(String initServer_no) {
        this.initServer_no = initServer_no;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getConsignOrgCode() {
        return consignOrgCode;
    }

    public void setConsignOrgCode(String consignOrgCode) {
        this.consignOrgCode = consignOrgCode;
    }

    public String getPhysicalEvidenceNo() {
        return physicalEvidenceNo;
    }

    public void setPhysicalEvidenceNo(String physicalEvidenceNo) {
        this.physicalEvidenceNo = physicalEvidenceNo;
    }

    public String getTrustyFlag() {
        return trustyFlag;
    }

    public void setTrustyFlag(String trustyFlag) {
        this.trustyFlag = trustyFlag;
    }

    public String getUtilization() {
        return utilization;
    }

    public void setUtilization(String utilization) {
        this.utilization = utilization;
    }

    public String getSecrecy() {
        return secrecy;
    }

    public void setSecrecy(String secrecy) {
        this.secrecy = secrecy;
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getTransferUser() {
        return transferUser;
    }

    public void setTransferUser(String transferUser) {
        this.transferUser = transferUser;
    }

    public String getTransferDatetime() {
        return transferDatetime;
    }

    public void setTransferDatetime(String transferDatetime) {
        this.transferDatetime = transferDatetime;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataLevel() {
        return dataLevel;
    }

    public void setDataLevel(String dataLevel) {
        this.dataLevel = dataLevel;
    }

    public String getUpdateDatetimeStr() {
        return updateDatetimeStr;
    }

    public void setUpdateDatetimeStr(String updateDatetimeStr) {
        this.updateDatetimeStr = updateDatetimeStr;
    }
}
