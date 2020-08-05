package com.bazl.lims.transfer.model.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/10.
 */
public class RelativeSampleInfo implements Serializable {

    private String id;
    private String sampleAid;
    private String sampleAname;
    private String sampleBid;
    private String sampleBname;
    private String sampleType;
    private String caseId;
    private String caseName;
    private String caseSn;
    private String isStore;
    private int relationType;
    private String submitOperator;
    private String submitTime;
    private String reserve1;
    private String reserve2;
    private String reserve3;
    private String reserve4;
    private String reserve5;
    private String reserve6;

    private String sampleaRole;
    private String samplebRole;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleAid() {
        return sampleAid;
    }

    public void setSampleAid(String sampleAid) {
        this.sampleAid = sampleAid;
    }

    public String getSampleAname() {
        return sampleAname;
    }

    public void setSampleAname(String sampleAname) {
        this.sampleAname = sampleAname;
    }

    public String getSampleBid() {
        return sampleBid;
    }

    public void setSampleBid(String sampleBid) {
        this.sampleBid = sampleBid;
    }

    public String getSampleBname() {
        return sampleBname;
    }

    public void setSampleBname(String sampleBname) {
        this.sampleBname = sampleBname;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseSn() {
        return caseSn;
    }

    public void setCaseSn(String caseSn) {
        this.caseSn = caseSn;
    }

    public String getIsStore() {
        return isStore;
    }

    public void setIsStore(String isStore) {
        this.isStore = isStore;
    }

    public int getRelationType() {
        return relationType;
    }

    public void setRelationType(int relationType) {
        this.relationType = relationType;
    }

    public String getSubmitOperator() {
        return submitOperator;
    }

    public void setSubmitOperator(String submitOperator) {
        this.submitOperator = submitOperator;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getReserve1() {
        return reserve1;
    }

    public void setReserve1(String reserve1) {
        this.reserve1 = reserve1;
    }

    public String getReserve2() {
        return reserve2;
    }

    public void setReserve2(String reserve2) {
        this.reserve2 = reserve2;
    }

    public String getReserve3() {
        return reserve3;
    }

    public void setReserve3(String reserve3) {
        this.reserve3 = reserve3;
    }

    public String getReserve4() {
        return reserve4;
    }

    public void setReserve4(String reserve4) {
        this.reserve4 = reserve4;
    }

    public String getReserve5() {
        return reserve5;
    }

    public void setReserve5(String reserve5) {
        this.reserve5 = reserve5;
    }

    public String getReserve6() {
        return reserve6;
    }

    public void setReserve6(String reserve6) {
        this.reserve6 = reserve6;
    }

    public String getSampleaRole() {
        return sampleaRole;
    }

    public void setSampleaRole(String sampleaRole) {
        this.sampleaRole = sampleaRole;
    }

    public String getSamplebRole() {
        return samplebRole;
    }

    public void setSamplebRole(String samplebRole) {
        this.samplebRole = samplebRole;
    }
}
