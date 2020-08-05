package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/10.
 */
public class RelativeDefModel implements Serializable{
    private String id;
    private String labId;
    private String initServerNo;
    private String relative1Id;
    private String relative2Id;
    private String relation;
    private String targetBirthdateFromStr;
    private Date targetBirthdateFrom;
    private String targetBirthdateToStr;
    private Date targetBirthdateTo;
    private String targetGender;
    private String targetSpeciality;
    private String targetExtrinsicSign;
    private String deleteFlag;
    private String remarlk;
    private String relation1;
    private String relation2;
    private String transferFlag;
    private String createDatetimeStr;
    private Date createDatetime;
    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getRelative1Id() {
        return relative1Id;
    }

    public void setRelative1Id(String relative1Id) {
        this.relative1Id = relative1Id;
    }

    public String getRelative2Id() {
        return relative2Id;
    }

    public void setRelative2Id(String relative2Id) {
        this.relative2Id = relative2Id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getTargetBirthdateFromStr() {
        return targetBirthdateFromStr;
    }

    public void setTargetBirthdateFromStr(String targetBirthdateFromStr) {
        this.targetBirthdateFromStr = targetBirthdateFromStr;
    }

    public Date getTargetBirthdateFrom() {
        return targetBirthdateFrom;
    }

    public void setTargetBirthdateFrom(Date targetBirthdateFrom) {
        this.targetBirthdateFrom = targetBirthdateFrom;
    }

    public String getTargetBirthdateToStr() {
        return targetBirthdateToStr;
    }

    public void setTargetBirthdateToStr(String targetBirthdateToStr) {
        this.targetBirthdateToStr = targetBirthdateToStr;
    }

    public Date getTargetBirthdateTo() {
        return targetBirthdateTo;
    }

    public void setTargetBirthdateTo(Date targetBirthdateTo) {
        this.targetBirthdateTo = targetBirthdateTo;
    }

    public String getTargetGender() {
        return targetGender;
    }

    public void setTargetGender(String targetGender) {
        this.targetGender = targetGender;
    }

    public String getTargetSpeciality() {
        return targetSpeciality;
    }

    public void setTargetSpeciality(String targetSpeciality) {
        this.targetSpeciality = targetSpeciality;
    }

    public String getTargetExtrinsicSign() {
        return targetExtrinsicSign;
    }

    public void setTargetExtrinsicSign(String targetExtrinsicSign) {
        this.targetExtrinsicSign = targetExtrinsicSign;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemarlk() {
        return remarlk;
    }

    public void setRemarlk(String remarlk) {
        this.remarlk = remarlk;
    }

    public String getRelation1() {
        return relation1;
    }

    public void setRelation1(String relation1) {
        this.relation1 = relation1;
    }

    public String getRelation2() {
        return relation2;
    }

    public void setRelation2(String relation2) {
        this.relation2 = relation2;
    }

    public String getTransferFlag() {
        return transferFlag;
    }

    public void setTransferFlag(String transferFlag) {
        this.transferFlag = transferFlag;
    }

    public String getCreateDatetimeStr() {
        return createDatetimeStr;
    }

    public void setCreateDatetimeStr(String createDatetimeStr) {
        this.createDatetimeStr = createDatetimeStr;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}
