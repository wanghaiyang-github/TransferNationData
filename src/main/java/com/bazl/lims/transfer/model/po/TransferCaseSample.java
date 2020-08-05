package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class TransferCaseSample implements Serializable {

    private String id;
    private String transferCaseQueueId;
    private String sampleBarcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTransferCaseQueueId() {
        return transferCaseQueueId;
    }

    public void setTransferCaseQueueId(String transferCaseQueueId) {
        this.transferCaseQueueId = transferCaseQueueId;
    }

    public String getSampleBarcode() {
        return sampleBarcode;
    }

    public void setSampleBarcode(String sampleBarcode) {
        this.sampleBarcode = sampleBarcode;
    }
}
