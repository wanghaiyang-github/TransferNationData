package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class SampleGeneInfoModel implements Serializable {

    private String id;
    private String sampleId;
    private String geneType;
    private String reagentKit;
    private int alleleCount;
    private String geneInfo;
    private String labId;
    private String serverNo;
    private String sampleCategory;
    private Date storeDate;
    private String storeDateStr;
    private String storeBy;
    private String createUser;
    private Date createDatetime;
    private String createDatetimeStr;
    private String updateUser;
    private Date updateDatetime;
    private String updateDatetimeStr;
    private String storeFlag;
    private String reviewBy;
    private Date reviewDate;
    private String reviewDateStr;
    private String reviewStatus;
    private String transferBy;
    private Date transferDate;
    private String transferDateStr;
    private String transferStatus;
    private String matchedStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleId() {
        return sampleId;
    }

    public void setSampleId(String sampleId) {
        this.sampleId = sampleId;
    }

    public String getGeneType() {
        return geneType;
    }

    public void setGeneType(String geneType) {
        this.geneType = geneType;
    }

    public String getReagentKit() {
        return reagentKit;
    }

    public void setReagentKit(String reagentKit) {
        this.reagentKit = reagentKit;
    }

    public int getAlleleCount() {
        return alleleCount;
    }

    public void setAlleleCount(int alleleCount) {
        this.alleleCount = alleleCount;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getServerNo() {
        return serverNo;
    }

    public void setServerNo(String serverNo) {
        this.serverNo = serverNo;
    }

    public String getSampleCategory() {
        return sampleCategory;
    }

    public void setSampleCategory(String sampleCategory) {
        this.sampleCategory = sampleCategory;
    }

    public Date getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(Date storeDate) {
        this.storeDate = storeDate;
    }

    public String getStoreDateStr() {
        return storeDateStr;
    }

    public void setStoreDateStr(String storeDateStr) {
        this.storeDateStr = storeDateStr;
    }

    public String getStoreBy() {
        return storeBy;
    }

    public void setStoreBy(String storeBy) {
        this.storeBy = storeBy;
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

    public String getStoreFlag() {
        return storeFlag;
    }

    public void setStoreFlag(String storeFlag) {
        this.storeFlag = storeFlag;
    }

    public String getReviewBy() {
        return reviewBy;
    }

    public void setReviewBy(String reviewBy) {
        this.reviewBy = reviewBy;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewDateStr() {
        return reviewDateStr;
    }

    public void setReviewDateStr(String reviewDateStr) {
        this.reviewDateStr = reviewDateStr;
    }

    public String getReviewStatus() {
        return reviewStatus;
    }

    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }

    public String getTransferBy() {
        return transferBy;
    }

    public void setTransferBy(String transferBy) {
        this.transferBy = transferBy;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getTransferDateStr() {
        return transferDateStr;
    }

    public void setTransferDateStr(String transferDateStr) {
        this.transferDateStr = transferDateStr;
    }

    public String getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(String transferStatus) {
        this.transferStatus = transferStatus;
    }

    public String getMatchedStatus() {
        return matchedStatus;
    }

    public void setMatchedStatus(String matchedStatus) {
        this.matchedStatus = matchedStatus;
    }
}
