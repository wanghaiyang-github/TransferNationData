package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class InstoreSampleInfo implements Serializable {

    public String labno;
    public String sampleNo;
    public String sampleid;
    public String sampleLabNumber;
    public String strType;
    public String caseid;
    public String caseSn;
    public String caseName;
    public String personid;
    public int sampletype;
    public String samplename;
    public String submitorg;
    public String submitoperator;
    public Date submittime;
    public String analysissoftware;
    public String panelid;
    public String panelname;
    public String additionalinfo;
    public String comments;
    public String reserve1;
    public String reserve2;
    public String reserve3;
    public String reserve4;
    public String reserve5;
    public String reserve6;
    public int state = -1;

    public String getLabno() {
        return labno;
    }

    public void setLabno(String labno) {
        this.labno = labno;
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getSampleid() {
        return sampleid;
    }

    public void setSampleid(String sampleid) {
        this.sampleid = sampleid;
    }

    public String getSampleLabNumber() {
        return sampleLabNumber;
    }

    public void setSampleLabNumber(String sampleLabNumber) {
        this.sampleLabNumber = sampleLabNumber;
    }

    public String getStrType() {
        return strType;
    }

    public void setStrType(String strType) {
        this.strType = strType;
    }

    public String getCaseid() {
        return caseid;
    }

    public void setCaseid(String caseid) {
        this.caseid = caseid;
    }

    public String getCaseSn() {
        return caseSn;
    }

    public void setCaseSn(String caseSn) {
        this.caseSn = caseSn;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getPersonid() {
        return personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public int getSampletype() {
        return sampletype;
    }

    public void setSampletype(int sampletype) {
        this.sampletype = sampletype;
    }

    public String getSamplename() {
        return samplename;
    }

    public void setSamplename(String samplename) {
        this.samplename = samplename;
    }

    public String getSubmitorg() {
        return submitorg;
    }

    public void setSubmitorg(String submitorg) {
        this.submitorg = submitorg;
    }

    public String getSubmitoperator() {
        return submitoperator;
    }

    public void setSubmitoperator(String submitoperator) {
        this.submitoperator = submitoperator;
    }

    public Date getSubmittime() {
        return submittime;
    }

    public void setSubmittime(Date submittime) {
        this.submittime = submittime;
    }

    public String getAnalysissoftware() {
        return analysissoftware;
    }

    public void setAnalysissoftware(String analysissoftware) {
        this.analysissoftware = analysissoftware;
    }

    public String getPanelid() {
        return panelid;
    }

    public void setPanelid(String panelid) {
        this.panelid = panelid;
    }

    public String getPanelname() {
        return panelname;
    }

    public void setPanelname(String panelname) {
        this.panelname = panelname;
    }

    public String getAdditionalinfo() {
        return additionalinfo;
    }

    public void setAdditionalinfo(String additionalinfo) {
        this.additionalinfo = additionalinfo;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
