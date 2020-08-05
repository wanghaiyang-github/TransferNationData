package com.bazl.lims.transfer.model.bo;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/9.
 */
public class ExternalSysInfo implements Serializable {

    private String externalSysType;
    private String externalSysNo;

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
}
