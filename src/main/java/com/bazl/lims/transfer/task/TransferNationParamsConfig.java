package com.bazl.lims.transfer.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019-05-08.
 */
@Configuration
@ConfigurationProperties(prefix="transfer", ignoreUnknownFields = true)
public class TransferNationParamsConfig {

    public boolean transferCaseActived;

    /**
     * 案件上报任务执行间隔时间（秒数）
     */
    private int transferCaseIntervalSeconds;


    public boolean transferPersonActived;

    /**
     * 人员上报任务执行间隔时间（秒数）
     */
    private int transferPersonIntervalSeconds;



    private boolean retrieveCaseSampleNoActived;
    private int retrieveCaseSampleNoSeconds;

    private boolean retrievePersonSampleNoActived;
    private int retrievePersonSampleNoSeconds;

    private boolean transferYstrActived;
    private int transferYstrIntervalSeconds;
    private String timeTaskType;
    private int readCount;

    private boolean transferNotInstoreActived;
    private int transferNotInstoreIntervalSeconds;
    private String timeTaskNotInstoreType;
    private int readNotInstoreCount;

    /**
     *  当前实验室编号
     */
    private String labServerNo;
    private String labServerName;

    private boolean needPushInstoreMsg;
    private String pushInstoreMsgUrl;

    /**
     * lims版本
     * 0 - 旧版；
     * 1 - 顺义新版
     * 2 - 新版
     */
    private String limsVersion;

    /**
     * 现勘回调连接
     */
    private String xckyCallbackURL_BEIJING;
    private String xckyCallbackURL_FYZHX;
    private String xckyCallbackURL_DCH;
    private String xckyCallbackURL_XCH;
    private String xckyCallbackURL_CHY;
    private String xckyCallbackURL_FT;
    private String xckyCallbackURL_SHJSH;
    private String xckyCallbackURL_HD;
    private String xckyCallbackURL_MTG;
    private String xckyCallbackURL_FSH;
    private String xckyCallbackURL_TZH;
    private String xckyCallbackURL_SHY;
    private String xckyCallbackURL_CHP;
    private String xckyCallbackURL_DX;
    private String xckyCallbackURL_HR;
    private String xckyCallbackURL_PG;
    private String xckyCallbackURL_MY;
    private String xckyCallbackURL_YQ;

    private String createNationalLibrary2FileURL;
    private String failedPath;
    private String processedPath;

    private String queryNationDbNoUrl;

    public boolean isTransferCaseActived() {
        return transferCaseActived;
    }

    public void setTransferCaseActived(boolean transferCaseActived) {
        this.transferCaseActived = transferCaseActived;
    }

    public boolean isTransferPersonActived() {
        return transferPersonActived;
    }

    public void setTransferPersonActived(boolean transferPersonActived) {
        this.transferPersonActived = transferPersonActived;
    }

    public int getTransferCaseIntervalSeconds() {
        return transferCaseIntervalSeconds;
    }

    public void setTransferCaseIntervalSeconds(int transferCaseIntervalSeconds) {
        this.transferCaseIntervalSeconds = transferCaseIntervalSeconds;
    }

    public int getTransferPersonIntervalSeconds() {
        return transferPersonIntervalSeconds;
    }

    public void setTransferPersonIntervalSeconds(int transferPersonIntervalSeconds) {
        this.transferPersonIntervalSeconds = transferPersonIntervalSeconds;
    }

    public boolean isRetrieveCaseSampleNoActived() {
        return retrieveCaseSampleNoActived;
    }

    public void setRetrieveCaseSampleNoActived(boolean retrieveCaseSampleNoActived) {
        this.retrieveCaseSampleNoActived = retrieveCaseSampleNoActived;
    }

    public int getRetrieveCaseSampleNoSeconds() {
        return retrieveCaseSampleNoSeconds;
    }

    public void setRetrieveCaseSampleNoSeconds(int retrieveCaseSampleNoSeconds) {
        this.retrieveCaseSampleNoSeconds = retrieveCaseSampleNoSeconds;
    }

    public boolean isRetrievePersonSampleNoActived() {
        return retrievePersonSampleNoActived;
    }

    public void setRetrievePersonSampleNoActived(boolean retrievePersonSampleNoActived) {
        this.retrievePersonSampleNoActived = retrievePersonSampleNoActived;
    }

    public int getRetrievePersonSampleNoSeconds() {
        return retrievePersonSampleNoSeconds;
    }

    public void setRetrievePersonSampleNoSeconds(int retrievePersonSampleNoSeconds) {
        this.retrievePersonSampleNoSeconds = retrievePersonSampleNoSeconds;
    }

    public boolean isTransferYstrActived() {
        return transferYstrActived;
    }

    public void setTransferYstrActived(boolean transferYstrActived) {
        this.transferYstrActived = transferYstrActived;
    }

    public int getTransferYstrIntervalSeconds() {
        return transferYstrIntervalSeconds;
    }

    public void setTransferYstrIntervalSeconds(int transferYstrIntervalSeconds) {
        this.transferYstrIntervalSeconds = transferYstrIntervalSeconds;
    }

    public boolean isTransferNotInstoreActived() {
        return transferNotInstoreActived;
    }

    public void setTransferNotInstoreActived(boolean transferNotInstoreActived) {
        this.transferNotInstoreActived = transferNotInstoreActived;
    }

    public int getTransferNotInstoreIntervalSeconds() {
        return transferNotInstoreIntervalSeconds;
    }

    public void setTransferNotInstoreIntervalSeconds(int transferNotInstoreIntervalSeconds) {
        this.transferNotInstoreIntervalSeconds = transferNotInstoreIntervalSeconds;
    }

    public String getTimeTaskType() {
        return timeTaskType;
    }

    public void setTimeTaskType(String timeTaskType) {
        this.timeTaskType = timeTaskType;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public String getLabServerNo() {
        return labServerNo;
    }

    public void setLabServerNo(String labServerNo) {
        this.labServerNo = labServerNo;
    }

    public String getTimeTaskNotInstoreType() {
        return timeTaskNotInstoreType;
    }

    public void setTimeTaskNotInstoreType(String timeTaskNotInstoreType) {
        this.timeTaskNotInstoreType = timeTaskNotInstoreType;
    }

    public int getReadNotInstoreCount() {
        return readNotInstoreCount;
    }

    public void setReadNotInstoreCount(int readNotInstoreCount) {
        this.readNotInstoreCount = readNotInstoreCount;
    }

    public String getLabServerName() {
        return labServerName;
    }

    public void setLabServerName(String labServerName) {
        this.labServerName = labServerName;
    }

    public String getXckyCallbackURL_BEIJING() {
        return xckyCallbackURL_BEIJING;
    }

    public void setXckyCallbackURL_BEIJING(String xckyCallbackURL_BEIJING) {
        this.xckyCallbackURL_BEIJING = xckyCallbackURL_BEIJING;
    }

    public String getXckyCallbackURL_FYZHX() {
        return xckyCallbackURL_FYZHX;
    }

    public void setXckyCallbackURL_FYZHX(String xckyCallbackURL_FYZHX) {
        this.xckyCallbackURL_FYZHX = xckyCallbackURL_FYZHX;
    }

    public String getXckyCallbackURL_DCH() {
        return xckyCallbackURL_DCH;
    }

    public void setXckyCallbackURL_DCH(String xckyCallbackURL_DCH) {
        this.xckyCallbackURL_DCH = xckyCallbackURL_DCH;
    }

    public String getXckyCallbackURL_XCH() {
        return xckyCallbackURL_XCH;
    }

    public void setXckyCallbackURL_XCH(String xckyCallbackURL_XCH) {
        this.xckyCallbackURL_XCH = xckyCallbackURL_XCH;
    }

    public String getXckyCallbackURL_CHY() {
        return xckyCallbackURL_CHY;
    }

    public void setXckyCallbackURL_CHY(String xckyCallbackURL_CHY) {
        this.xckyCallbackURL_CHY = xckyCallbackURL_CHY;
    }

    public String getXckyCallbackURL_FT() {
        return xckyCallbackURL_FT;
    }

    public void setXckyCallbackURL_FT(String xckyCallbackURL_FT) {
        this.xckyCallbackURL_FT = xckyCallbackURL_FT;
    }

    public String getXckyCallbackURL_SHJSH() {
        return xckyCallbackURL_SHJSH;
    }

    public void setXckyCallbackURL_SHJSH(String xckyCallbackURL_SHJSH) {
        this.xckyCallbackURL_SHJSH = xckyCallbackURL_SHJSH;
    }

    public String getXckyCallbackURL_HD() {
        return xckyCallbackURL_HD;
    }

    public void setXckyCallbackURL_HD(String xckyCallbackURL_HD) {
        this.xckyCallbackURL_HD = xckyCallbackURL_HD;
    }

    public String getXckyCallbackURL_MTG() {
        return xckyCallbackURL_MTG;
    }

    public void setXckyCallbackURL_MTG(String xckyCallbackURL_MTG) {
        this.xckyCallbackURL_MTG = xckyCallbackURL_MTG;
    }

    public String getXckyCallbackURL_FSH() {
        return xckyCallbackURL_FSH;
    }

    public void setXckyCallbackURL_FSH(String xckyCallbackURL_FSH) {
        this.xckyCallbackURL_FSH = xckyCallbackURL_FSH;
    }

    public String getXckyCallbackURL_TZH() {
        return xckyCallbackURL_TZH;
    }

    public void setXckyCallbackURL_TZH(String xckyCallbackURL_TZH) {
        this.xckyCallbackURL_TZH = xckyCallbackURL_TZH;
    }

    public String getXckyCallbackURL_SHY() {
        return xckyCallbackURL_SHY;
    }

    public void setXckyCallbackURL_SHY(String xckyCallbackURL_SHY) {
        this.xckyCallbackURL_SHY = xckyCallbackURL_SHY;
    }

    public String getXckyCallbackURL_CHP() {
        return xckyCallbackURL_CHP;
    }

    public void setXckyCallbackURL_CHP(String xckyCallbackURL_CHP) {
        this.xckyCallbackURL_CHP = xckyCallbackURL_CHP;
    }

    public String getXckyCallbackURL_DX() {
        return xckyCallbackURL_DX;
    }

    public void setXckyCallbackURL_DX(String xckyCallbackURL_DX) {
        this.xckyCallbackURL_DX = xckyCallbackURL_DX;
    }

    public String getXckyCallbackURL_HR() {
        return xckyCallbackURL_HR;
    }

    public void setXckyCallbackURL_HR(String xckyCallbackURL_HR) {
        this.xckyCallbackURL_HR = xckyCallbackURL_HR;
    }

    public String getXckyCallbackURL_PG() {
        return xckyCallbackURL_PG;
    }

    public void setXckyCallbackURL_PG(String xckyCallbackURL_PG) {
        this.xckyCallbackURL_PG = xckyCallbackURL_PG;
    }

    public String getXckyCallbackURL_MY() {
        return xckyCallbackURL_MY;
    }

    public void setXckyCallbackURL_MY(String xckyCallbackURL_MY) {
        this.xckyCallbackURL_MY = xckyCallbackURL_MY;
    }

    public String getXckyCallbackURL_YQ() {
        return xckyCallbackURL_YQ;
    }

    public void setXckyCallbackURL_YQ(String xckyCallbackURL_YQ) {
        this.xckyCallbackURL_YQ = xckyCallbackURL_YQ;
    }

    public String getLimsVersion() {
        return limsVersion;
    }

    public void setLimsVersion(String limsVersion) {
        this.limsVersion = limsVersion;
    }

    public String getCreateNationalLibrary2FileURL() {
        return createNationalLibrary2FileURL;
    }

    public void setCreateNationalLibrary2FileURL(String createNationalLibrary2FileURL) {
        this.createNationalLibrary2FileURL = createNationalLibrary2FileURL;
    }

    public String getFailedPath() {
        return failedPath;
    }

    public void setFailedPath(String failedPath) {
        this.failedPath = failedPath;
    }

    public String getProcessedPath() {
        return processedPath;
    }

    public void setProcessedPath(String processedPath) {
        this.processedPath = processedPath;
    }


    public String getQueryNationDbNoUrl() {
        return queryNationDbNoUrl;
    }

    public void setQueryNationDbNoUrl(String queryNationDbNoUrl) {
        this.queryNationDbNoUrl = queryNationDbNoUrl;
    }

    public boolean isNeedPushInstoreMsg() {
        return needPushInstoreMsg;
    }

    public void setNeedPushInstoreMsg(boolean needPushInstoreMsg) {
        this.needPushInstoreMsg = needPushInstoreMsg;
    }

    public String getPushInstoreMsgUrl() {
        return pushInstoreMsgUrl;
    }

    public void setPushInstoreMsgUrl(String pushInstoreMsgUrl) {
        this.pushInstoreMsgUrl = pushInstoreMsgUrl;
    }
}
