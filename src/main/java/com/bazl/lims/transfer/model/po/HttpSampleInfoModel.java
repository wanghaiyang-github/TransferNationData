package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class HttpSampleInfoModel implements Serializable {

    private String sampleNo;
    private String acceptorid;

    @Override
    public String toString() {
        return "HttpSampleInfoModel{" +
                "sampleNo='" + sampleNo + '\'' +
                ", acceptorid='" + acceptorid + '\'' +
                '}';
    }

    public String getSampleNo() {
        return sampleNo;
    }

    public void setSampleNo(String sampleNo) {
        this.sampleNo = sampleNo;
    }

    public String getAcceptorid() {
        return acceptorid;
    }

    public void setAcceptorid(String acceptorid) {
        this.acceptorid = acceptorid;
    }
}
