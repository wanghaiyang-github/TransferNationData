package com.bazl.lims.transfer.model.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/10.
 */
public class LimsMarkernames implements Serializable {

    private String locusName;
    private String fieldName;

    public String getLocusName() {
        return locusName;
    }

    public void setLocusName(String locusName) {
        this.locusName = locusName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
}
