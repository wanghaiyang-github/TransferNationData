package com.bazl.lims.transfer.model.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/6/10.
 */
public class YstrDnaLibView implements Serializable {

    private String id;
    private String sampleId;
    private String sampleTypeId;
    private String panelName;
    private String geneInfo;
    private String realPanelName;

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

    public String getSampleTypeId() {
        return sampleTypeId;
    }

    public void setSampleTypeId(String sampleTypeId) {
        this.sampleTypeId = sampleTypeId;
    }

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public String getGeneInfo() {
        return geneInfo;
    }

    public void setGeneInfo(String geneInfo) {
        this.geneInfo = geneInfo;
    }

    public String getRealPanelName() {
        return realPanelName;
    }

    public void setRealPanelName(String realPanelName) {
        this.realPanelName = realPanelName;
    }
}
