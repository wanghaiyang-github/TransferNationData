package com.bazl.lims.transfer.task;

import com.alibaba.fastjson.JSONObject;
import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.CaseInfoService;
import com.bazl.lims.transfer.service.NewLimsPersonQueueService;
import com.bazl.lims.transfer.service.OldLimsPersonQueueService;
import com.bazl.lims.transfer.service.SampleInfoService;
import com.bazl.lims.transfer.task.service.SubmitToNation2PersonService;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import com.bazl.lims.transfer.utils.TransferSpecialMarkerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Administrator on 2019/6/18.
 */
@Component
public class TransferPersonDataExecutor {

    private final Logger logger = LoggerFactory.getLogger(TransferPersonDataExecutor.class);

    @Autowired
    OldLimsPersonQueueService oldLimsPersonQueueService;
    @Autowired
    NewLimsPersonQueueService newLimsPersonQueueService;
    @Autowired
    SubmitToNation2PersonService submitToNation2PersonService;

    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    SampleInfoService sampleInfoService;

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;
    @Autowired
    CaseInfoConfigs caseInfoConfigs;

    public void transferPersonData(TransferPersonQueue transferPersonQueue) {

        String caseId = null;
        SubmitNationDataModel submitNationDataModel = getSubmitNationDataModel(transferPersonQueue);

        if (submitNationDataModel != null) {
            caseId = transferPersonQueue.getCaseId();
            try {
                Map<String, String> transferMap = submitToNation2PersonService.submitToNation(submitNationDataModel);
                String transferResult = transferMap.get("result");
                String[] filepath = (transferMap.get("message")).split("\\\\");
                String filename = filepath[filepath.length - 1];
                if (transferResult.equals("-1")) {
                    transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_ERROR);
                    transferPersonQueue.setFailedMsg(filename);
                    transferPersonQueue.setTransferDatetime(new Date());
                    if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                        oldLimsPersonQueueService.updateStatus(transferPersonQueue);
                    }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                        newLimsPersonQueueService.updateStatus(transferPersonQueue);
                    }
                    this.logger.info("---STNL2---建库数据：" + transferPersonQueue.getSampleBarcode() + "，上报失败！");
                } else if (transferResult.equals("1")) {
                    transferPersonQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_SUCCEED);
                    transferPersonQueue.setTransferFileName(filename);
                    transferPersonQueue.setTransferDatetime(new Date());
                    if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                        oldLimsPersonQueueService.updateStatus(transferPersonQueue);
                    }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                        newLimsPersonQueueService.updateStatus(transferPersonQueue);
                    }
                    this.logger.info("---STNL2---建库数据：" + transferPersonQueue.getSampleBarcode() + "，上报国家库2期生成文件成功！");
                    this.logger.info((String) transferMap.get("message") == null ? "" : (String) transferMap.get("message"));
                }
            }catch(Exception ex){
                logger.error("invoke TransferPersonDataExecutor.transferPersonData error.", ex);
            }
        }
    }

    private SubmitNationDataModel getSubmitNationDataModel(TransferPersonQueue transferPersonQueue){
        String queueId = transferPersonQueue.getId();
        String sampleBarcode = transferPersonQueue.getSampleBarcode();
        String caseId = transferPersonQueue.getCaseId();
        SubmitNationDataModel submitNationDataModel = new SubmitNationDataModel();

        CaseInfoModel caseInfoModel = null;
        if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
            caseInfoModel = caseInfoService.selectCaseInfoModelByCaseId(caseId);
        }else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
            caseInfoModel = caseInfoService.selectNewCaseInfoModelByCaseId(caseId);
        }
        if(caseInfoModel == null){
            return null;
        }

        /**
         * 获取委托信息
         */
        ConsignmentModel consignmentModel = getConsignmentModel(caseId, sampleBarcode);
        if(consignmentModel == null){
            markQueueError(transferPersonQueue, TransferConstants.TRANSFER_STATUS_GENERATE_ERROR, "caseId[" + caseId + "]对应委托信息不存在");
            return null;
        }

        submitNationDataModel.setConsignmentModel(consignmentModel);


        /**
         * 获取样本信息
         */
        SampleInfoModel sampleInfoModel = null;
        SampleInfoModel queueSampleInfoModel = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            queueSampleInfoModel = sampleInfoService.selectSampleInfoByBarcode(sampleBarcode);
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            queueSampleInfoModel = this.sampleInfoService.selectNewSampleInfoByBarcode(sampleBarcode);
        }
        if(queueSampleInfoModel != null){
            sampleInfoModel = getSampleInfoModel(caseInfoModel, queueSampleInfoModel);
        }else{
            sampleInfoModel = new SampleInfoModel();
        }
        submitNationDataModel.setSampleInfoModel(sampleInfoModel);

        /**
         * 获取基因信息
         */
        List<SampleGeneInfoModel> sampleGeneInfoModelList = new ArrayList<>();
        if(sampleInfoModel != null){
            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                sampleGeneInfoModelList = getSampleGeneInfoModelList(caseInfoModel, sampleInfoModel);
            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                sampleGeneInfoModelList = getNewSampleGeneInfoModelList(caseInfoModel, queueId);
            }
        }
        submitNationDataModel.setSampleGeneInfoModelList(sampleGeneInfoModelList);

        return submitNationDataModel;
    }

    /**
     * 获取newlims检材基因型信息
     * @param caseInfoModel
     * @param queueId
     * @return
     */
    private List<SampleGeneInfoModel> getNewSampleGeneInfoModelList(CaseInfoModel caseInfoModel, String queueId) {
        List<SampleGeneInfoModel> sampleGeneInfoModelList = sampleInfoService.getNewSampleGeneInfoListByQueueId(queueId);

        if (ListUtils.isEmptyList(sampleGeneInfoModelList)) {
            return null;
        }

        for (SampleGeneInfoModel sgim : sampleGeneInfoModelList) {
            int alleleCount = 0;
            //判断newlims中基因类型为常染色体和混合
            if (TransferConstants.GENE_NORMAL.equals(sgim.getGeneType())
                    || TransferConstants.GENE_MIXED.equals(sgim.getGeneType())) {
                sgim.setGeneType(TransferConstants.GENE_TYPE_STR);
            }else if (TransferConstants.GENE_YSTR.equals(sgim.getGeneType())) {//判断newlims中基因类型为Y基因型
                sgim.setGeneType(TransferConstants.GENE_TYPE_YSTR);
            }

            Map<String, Object> strMap = TransferSpecialMarkerUtil.analysisGene(sgim.getGeneInfo());

            if (strMap != null) {
                alleleCount = strMap.entrySet().size();
                if (sgim.getAlleleCount() == 0 && alleleCount != 0) {
                    sgim.setAlleleCount(alleleCount);
                }

                //转换基因型
                String strDnaString = JSONObject.toJSONString(strMap);
                sgim.setGeneInfo(strDnaString);

            }
            sgim.setCreateUser(caseInfoModel.getAcceptorName());
            sgim.setCreateDatetime(caseInfoModel.getUpdateDatetime());
            sgim.setUpdateUser(caseInfoModel.getAcceptorName());
            if (sgim.getUpdateDatetime() != null) {
                sgim.setUpdateDatetime(sgim.getUpdateDatetime());
            }else {
                sgim.setUpdateDatetime(new Date());
            }
        }

        return sampleGeneInfoModelList;
    }

    /**
     * 构建基因信息list
     * @param sampleInfoModel
     * @return
     */
    private List<SampleGeneInfoModel> getSampleGeneInfoModelList(CaseInfoModel caseInfoModel, SampleInfoModel sampleInfoModel){
        if(sampleInfoModel == null){
            return null;
        }

        List<SampleGeneInfoModel> sampleGeneInfoModelList = new ArrayList<>();
        List<LimsMarkernames> limsMarkernamesList = sampleInfoService.selectStrMarkernames();

        List<StrDnaLibView> strDnaLibViewList = null;
        List<YstrDnaLibView> ystrDnaLibViewList = null;
        SampleGeneInfoModel sampleGeneInfoModel = null;

        /**
         * 查询STR
         */
        strDnaLibViewList = sampleInfoService.selectStrDnaLibViewBySampleBarcode(sampleInfoModel.getEvidenceCode());
        if(ListUtils.isNotEmptyList(strDnaLibViewList)){
            for(StrDnaLibView strDnaLibView : strDnaLibViewList){
                sampleGeneInfoModel = convertStrGeneInfoModel(strDnaLibView, limsMarkernamesList);
                sampleGeneInfoModel.setSampleId(sampleInfoModel.getId());
                sampleGeneInfoModel.setCreateUser(caseInfoModel.getAcceptorName());
                sampleGeneInfoModel.setCreateDatetime(caseInfoModel.getUpdateDatetime());
                sampleGeneInfoModel.setUpdateUser(caseInfoModel.getAcceptorName());
                if (sampleInfoModel.getUpdateDatetime() != null) {
                    sampleGeneInfoModel.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());
                }else {
                    sampleGeneInfoModel.setUpdateDatetime(new Date());
                }

                sampleGeneInfoModelList.add(sampleGeneInfoModel);
            }
        }

        /**
         * 查询Y-STR
         */
        ystrDnaLibViewList = sampleInfoService.selectYstrDnaLibViewBySampleBarcode(sampleInfoModel.getEvidenceCode());
        if(ListUtils.isNotEmptyList(ystrDnaLibViewList)){
            for(YstrDnaLibView ystrDnaLibView : ystrDnaLibViewList){
                sampleGeneInfoModel = new SampleGeneInfoModel();
                sampleGeneInfoModel.setId(ystrDnaLibView.getId());
                sampleGeneInfoModel.setSampleId(sampleInfoModel.getId());
                sampleGeneInfoModel.setGeneType(TransferConstants.GENE_TYPE_YSTR);
                sampleGeneInfoModel.setCreateUser(caseInfoModel.getAcceptorName());
                sampleGeneInfoModel.setCreateDatetime(caseInfoModel.getUpdateDatetime());
                sampleGeneInfoModel.setUpdateUser(caseInfoModel.getAcceptorName());
                if (sampleInfoModel.getUpdateDatetime() != null) {
                    sampleGeneInfoModel.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());
                }else {
                    sampleGeneInfoModel.setUpdateDatetime(new Date());
                }

                sampleGeneInfoModel = convertYserGeneInfoModel(sampleGeneInfoModel, ystrDnaLibView);
                sampleGeneInfoModelList.add(sampleGeneInfoModel);

            }
        }

        return sampleGeneInfoModelList;
    }


    private SampleGeneInfoModel convertStrGeneInfoModel(StrDnaLibView strDnaLibView, List<LimsMarkernames> limsMarkernamesList){
        SampleGeneInfoModel sampleGeneInfoModel = new SampleGeneInfoModel();
        sampleGeneInfoModel.setGeneType(TransferConstants.GENE_TYPE_STR);
        sampleGeneInfoModel.setReagentKit(StringUtils.isNotBlank(strDnaLibView.getRealPanelName()) ? strDnaLibView.getRealPanelName() : strDnaLibView.getPanelName() );

        Map<String, String> strMap = new HashMap<>();

        int alleleCount = 0;
        if(StringUtils.isNotBlank(strDnaLibView.getLocus01())
                && !"0".equals(strDnaLibView.getLocus01())){
            strMap.put(findRealMarkerName("locus01", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus01()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus02())
                && !"0".equals(strDnaLibView.getLocus02())){
            strMap.put(findRealMarkerName("locus02", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus02()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus03())
                && !"0".equals(strDnaLibView.getLocus03())){
            strMap.put(findRealMarkerName("locus03", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus03()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus04())
                && !"0".equals(strDnaLibView.getLocus04())){
            strMap.put(findRealMarkerName("locus04", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus04()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus05())
                && !"0".equals(strDnaLibView.getLocus05())){
            strMap.put(findRealMarkerName("locus05", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus05()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus06())
                && !"0".equals(strDnaLibView.getLocus06())){
            strMap.put(findRealMarkerName("locus06", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus06()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus07())
                && !"0".equals(strDnaLibView.getLocus07())){
            strMap.put(findRealMarkerName("locus07", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus07()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus08())
                && !"0".equals(strDnaLibView.getLocus08())){
            strMap.put(findRealMarkerName("locus08", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus08()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus09())
                && !"0".equals(strDnaLibView.getLocus09())){
            strMap.put(findRealMarkerName("locus09", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus09()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus10())
                && !"0".equals(strDnaLibView.getLocus10())){
            strMap.put(findRealMarkerName("locus10", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus10()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus11())
                && !"0".equals(strDnaLibView.getLocus11())){
            strMap.put(findRealMarkerName("locus11", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus11()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus12())
                && !"0".equals(strDnaLibView.getLocus12())){
            strMap.put(findRealMarkerName("locus12", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus12()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus13())
                && !"0".equals(strDnaLibView.getLocus13())){
            strMap.put(findRealMarkerName("locus13", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus13()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus14())
                && !"0".equals(strDnaLibView.getLocus14())){
            strMap.put(findRealMarkerName("locus14", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus14()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus15())
                && !"0".equals(strDnaLibView.getLocus15())){
            strMap.put(findRealMarkerName("locus15", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus15()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus16())
                && !"0".equals(strDnaLibView.getLocus16())){
            strMap.put(findRealMarkerName("locus16", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus16()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus17())
                && !"0".equals(strDnaLibView.getLocus17())){
            strMap.put(findRealMarkerName("locus17", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus17()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus18())
                && !"0".equals(strDnaLibView.getLocus18())){
            strMap.put(findRealMarkerName("locus18", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus18()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus19())
                && !"0".equals(strDnaLibView.getLocus19())){
            strMap.put(findRealMarkerName("locus19", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus19()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus20())
                && !"0".equals(strDnaLibView.getLocus20())){
            strMap.put(findRealMarkerName("locus20", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus20()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus21())
                && !"0".equals(strDnaLibView.getLocus21())){
            strMap.put(findRealMarkerName("locus21", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus21()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus22())
                && !"0".equals(strDnaLibView.getLocus22())){
            strMap.put(findRealMarkerName("locus22", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus22()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus23())
                && !"0".equals(strDnaLibView.getLocus23())){
            strMap.put(findRealMarkerName("locus23", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus23()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus24())
                && !"0".equals(strDnaLibView.getLocus24())){
            strMap.put(findRealMarkerName("locus24", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus24()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus25())
                && !"0".equals(strDnaLibView.getLocus25())){
            strMap.put(findRealMarkerName("locus25", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus25()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus26())
                && !"0".equals(strDnaLibView.getLocus26())){
            strMap.put(findRealMarkerName("locus26", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus26()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus27())
                && !"0".equals(strDnaLibView.getLocus27())){
            strMap.put(findRealMarkerName("locus27", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus27()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus28())
                && !"0".equals(strDnaLibView.getLocus28())){
            strMap.put(findRealMarkerName("locus28", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus28()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus29())
                && !"0".equals(strDnaLibView.getLocus29())){
            strMap.put(findRealMarkerName("locus29", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus29()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus30())
                && !"0".equals(strDnaLibView.getLocus30())){
            strMap.put(findRealMarkerName("locus30", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus30()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus31())
                && !"0".equals(strDnaLibView.getLocus31())){
            strMap.put(findRealMarkerName("locus31", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus31()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus32())
                && !"0".equals(strDnaLibView.getLocus32())){
            strMap.put(findRealMarkerName("locus32", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus32()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus33())
                && !"0".equals(strDnaLibView.getLocus33())){
            strMap.put(findRealMarkerName("locus33", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus33()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus34())
                && !"0".equals(strDnaLibView.getLocus34())){
            strMap.put(findRealMarkerName("locus34", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus34()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus35())
                && !"0".equals(strDnaLibView.getLocus35())){
            strMap.put(findRealMarkerName("locus35", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus35()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus36())
                && !"0".equals(strDnaLibView.getLocus36())){
            strMap.put(findRealMarkerName("locus36", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus36()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus37())
                && !"0".equals(strDnaLibView.getLocus37())){
            strMap.put(findRealMarkerName("locus37", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus37()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus38())
                && !"0".equals(strDnaLibView.getLocus38())){
            strMap.put(findRealMarkerName("locus38", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus38()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus39())
                && !"0".equals(strDnaLibView.getLocus39())){
            strMap.put(findRealMarkerName("locus39", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus39()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus40())
                && !"0".equals(strDnaLibView.getLocus40())){
            strMap.put(findRealMarkerName("locus40", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus40()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus41())
                && !"0".equals(strDnaLibView.getLocus41())){
            strMap.put(findRealMarkerName("locus41", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus41()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus42())
                && !"0".equals(strDnaLibView.getLocus42())){
            strMap.put(findRealMarkerName("locus42", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus42()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus43())
                && !"0".equals(strDnaLibView.getLocus43())){
            strMap.put(findRealMarkerName("locus43", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus43()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus44())
                && !"0".equals(strDnaLibView.getLocus44())){
            strMap.put(findRealMarkerName("locus44", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus44()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus45())
                && !"0".equals(strDnaLibView.getLocus45())){
            strMap.put(findRealMarkerName("locus45", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus45()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus46())
                && !"0".equals(strDnaLibView.getLocus46())){
            strMap.put(findRealMarkerName("locus46", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus46()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus47())
                && !"0".equals(strDnaLibView.getLocus47())){
            strMap.put(findRealMarkerName("locus47", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus47()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus48())
                && !"0".equals(strDnaLibView.getLocus48())){
            strMap.put(findRealMarkerName("locus48", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus48()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus49())
                && !"0".equals(strDnaLibView.getLocus49())){
            strMap.put(findRealMarkerName("locus49", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus49()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus50())
                && !"0".equals(strDnaLibView.getLocus50())){
            strMap.put(findRealMarkerName("locus50", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus50()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus51())
                && !"0".equals(strDnaLibView.getLocus51())){
            strMap.put(findRealMarkerName("locus51", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus51()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus52())
                && !"0".equals(strDnaLibView.getLocus52())){
            strMap.put(findRealMarkerName("locus52", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus52()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus53())
                && !"0".equals(strDnaLibView.getLocus53())){
            strMap.put(findRealMarkerName("locus53", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus53()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus54())
                && !"0".equals(strDnaLibView.getLocus54())){
            strMap.put(findRealMarkerName("locus54", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus54()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus55())
                && !"0".equals(strDnaLibView.getLocus55())){
            strMap.put(findRealMarkerName("locus55", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus55()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus56())
                && !"0".equals(strDnaLibView.getLocus56())){
            strMap.put(findRealMarkerName("locus56", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus56()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus57())
                && !"0".equals(strDnaLibView.getLocus57())){
            strMap.put(findRealMarkerName("locus57", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus57()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus58())
                && !"0".equals(strDnaLibView.getLocus58())){
            strMap.put(findRealMarkerName("locus58", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus58()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus59())
                && !"0".equals(strDnaLibView.getLocus59())){
            strMap.put(findRealMarkerName("locus59", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus59()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus60())
                && !"0".equals(strDnaLibView.getLocus60())){
            strMap.put(findRealMarkerName("locus60", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus60()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus61())
                && !"0".equals(strDnaLibView.getLocus61())){
            strMap.put(findRealMarkerName("locus61", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus61()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus62())
                && !"0".equals(strDnaLibView.getLocus62())){
            strMap.put(findRealMarkerName("locus62", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus62()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus63())
                && !"0".equals(strDnaLibView.getLocus63())){
            strMap.put(findRealMarkerName("locus63", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus63()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus64())
                && !"0".equals(strDnaLibView.getLocus64())){
            strMap.put(findRealMarkerName("locus64", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus64()));
        }

        if(StringUtils.isNotBlank(strDnaLibView.getLocus65())
                && !"0".equals(strDnaLibView.getLocus65())){
            strMap.put(findRealMarkerName("locus65", limsMarkernamesList), convertAlleleValues(strDnaLibView.getLocus65()));
        }

        alleleCount = strMap.entrySet().size();
        sampleGeneInfoModel.setAlleleCount(alleleCount);

        String strDnaString = JSONObject.toJSONString(strMap);
        sampleGeneInfoModel.setGeneInfo(strDnaString);
        sampleGeneInfoModel.setId(strDnaLibView.getId());

        return sampleGeneInfoModel;
    }


    private String findRealMarkerName(String locusName, List<LimsMarkernames> limsMarkernamesList){
        for(LimsMarkernames markernames : limsMarkernamesList){
            if(markernames.getLocusName().equalsIgnoreCase(locusName)){
                return TransferSpecialMarkerUtil.transgerSpecialMarker(markernames.getFieldName());
            }
        }

        return null;
    }

    private String convertAlleleValues(String alleleValue){
        String prefixStr = "<";
        String suffixStr = ">";


        String alleleValeStr = "";

        String[] alleleValueArr = alleleValue.split(">");
        for(String av : alleleValueArr){
            alleleValeStr += av.substring(1) + "/";
        }

        if(alleleValeStr.endsWith("/")){
            alleleValeStr = alleleValeStr.substring(0, alleleValeStr.length()-1);
        }
        return alleleValeStr;
    }


    private String createRelationPersonInfo(String sampleA, String sampleB) {
        return sampleA + sampleB;
    }

    private SampleGeneInfoModel convertYserGeneInfoModel(SampleGeneInfoModel sampleGeneInfoModel, YstrDnaLibView ystrDnaLibView){
        String geneInfo = ystrDnaLibView.getGeneInfo();

        if(StringUtils.isNotBlank(geneInfo)) {
            String[] geneInfoArr = geneInfo.split("\\[");

            Map<String, String> strMap = new HashMap<>();
            String locusInfo = null;
            String locusName = null;
            String alleleValuesInfo = null;
            String alleleValuesStr = "";
            for (int idx = 1; idx < geneInfoArr.length; idx++) {
                locusInfo = geneInfoArr[idx];
                int sepatorIdx = locusInfo.indexOf("]");
                locusName = locusInfo.substring(0, sepatorIdx);

                locusName = TransferSpecialMarkerUtil.transgerSpecialMarker(locusName);

                alleleValuesInfo = locusInfo.substring(sepatorIdx + 1);

                String[] alleleValuesArr = alleleValuesInfo.split("<");
                alleleValuesStr = "";
                for (int i = 1; i < alleleValuesArr.length; i++) {
                    String tempAv = alleleValuesArr[i];
                    alleleValuesStr += tempAv.substring(0, tempAv.indexOf(",")) + "/";
                }

                if (alleleValuesStr.endsWith("/")) {
                    alleleValuesStr = alleleValuesStr.substring(0, alleleValuesStr.length() - 1);
                }

                strMap.put(locusName, alleleValuesStr);
            }

            sampleGeneInfoModel.setAlleleCount(strMap.entrySet().size());

            String strDnaString = JSONObject.toJSONString(strMap);
            sampleGeneInfoModel.setGeneInfo(strDnaString);
        }

        sampleGeneInfoModel.setId(ystrDnaLibView.getId());
        sampleGeneInfoModel.setReagentKit(StringUtils.isNotBlank(ystrDnaLibView.getRealPanelName()) ? ystrDnaLibView.getRealPanelName() : ystrDnaLibView.getPanelName() );
        return sampleGeneInfoModel;
    }


    /**
     * 构建样本信息list
     * @param sampleInfoModel
     * @return
     */
    private SampleInfoModel getSampleInfoModel(CaseInfoModel caseInfoModel, SampleInfoModel sampleInfoModel){
        InstoreSampleInfo instoreSampleInfo = null;
        String instoreSampleType = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            instoreSampleInfo = sampleInfoService.selectInstoreSampleInfoBySampleBarcode(sampleInfoModel.getEvidenceCode());
            if(instoreSampleInfo == null){
                return null;
            }

            instoreSampleType = String.valueOf(instoreSampleInfo.getSampletype());
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            if (StringUtils.isNotBlank(sampleInfoModel.getInstoreSampleType())) {
                instoreSampleType = sampleInfoModel.getInstoreSampleType();
            }
        }

        if(StringUtils.isNotBlank(sampleInfoModel.getGender())){
            sampleInfoModel.setGender(TransferHelper.convertNationGener(transferNationParamsConfig.getLimsVersion(), sampleInfoModel.getGender()));
        }else{
            sampleInfoModel.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
        }

        if(StringUtils.isNotBlank(sampleInfoModel.getSampleType())){
            sampleInfoModel.setSampleType(TransferHelper.convertNationSampleType(transferNationParamsConfig.getLimsVersion(), sampleInfoModel.getSampleType(), caseInfoConfigs));
        }else{
            sampleInfoModel.setSampleType(caseInfoConfigs.getSampleTypeDefault());
        }

        sampleInfoModel.setSampleStatus(TransferConstants.DEFAULT_NATION_SAMPLE_STATUS);
        sampleInfoModel.setCreateUser(caseInfoModel.getCreateUser());
        sampleInfoModel.setCreateDatetime(caseInfoModel.getCreateDatetime());
        sampleInfoModel.setUpdateUser(caseInfoModel.getAcceptorName());
        if (sampleInfoModel.getUpdateDatetime() != null) {
            sampleInfoModel.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());
        }else {
            sampleInfoModel.setUpdateDatetime(new Date());
        }

        //判断样本入库类型是否为物证、或物证YSTR
        if(TransferHelper.isInstoreSampleTypeEvidence(transferNationParamsConfig.getLimsVersion(), instoreSampleType, sampleInfoModel.getEvidenceNo())){
            sampleInfoModel.setPhysicalEvidenceId(sampleInfoModel.getId());
            sampleInfoModel.setPersonnelNo(sampleInfoModel.getId());
            sampleInfoModel.setPersonnelName(sampleInfoModel.getEvidenceName());
            sampleInfoModel.setPersonnelType(TransferConstants.NATION_SAMPLE_CATEGORY_EVIDENCE);        //物证
            sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
        }else{
            //人员样本处理
            //人员信息存在时，查询出人员信息和国家库样本类型
            if(StringUtils.isNotBlank(sampleInfoModel.getPersonnelNo())
                    && !sampleInfoModel.getPersonnelNo().equals("-1")) {
                sampleInfoModel.setPersonnelNo(sampleInfoModel.getPersonnelNo());
                sampleInfoModel.setSelfObjectId(sampleInfoModel.getPersonnelNo());
                sampleInfoModel.setPersonnelType(TransferHelper.convertNationSampleCategory(transferNationParamsConfig.getLimsVersion(), String.valueOf(instoreSampleType)));

                if(StringUtils.isNotBlank(sampleInfoModel.getEvidenceNo())) {
                    sampleInfoModel.setExternalPersonnelNo(sampleInfoModel.getEvidenceNo());
                    sampleInfoModel.setExternalSysType(caseInfoConfigs.getExternalSysTypeXk());
                }


                if(sampleInfoModel.getInvolvedCaseNo() == null) {
                    sampleInfoModel.setInvolvedCaseNo(caseInfoModel.getCaseNo());
                } else {
                    sampleInfoModel.setInvolvedCaseNo(sampleInfoModel.getInvolvedCaseNo());
                    sampleInfoModel.setCaseName(caseInfoModel.getCaseName());
                    sampleInfoModel.setCaseProperty(caseInfoModel.getCaseProperty());
                }

            }else{
                //没有人员信息，创建
                sampleInfoModel.setPersonnelNo(sampleInfoModel.getId());
                sampleInfoModel.setPersonnelName(sampleInfoModel.getEvidenceName());
                sampleInfoModel.setPersonnelType(TransferHelper.convertNationSampleCategory(transferNationParamsConfig.getLimsVersion(), String.valueOf(instoreSampleType)));
                sampleInfoModel.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                sampleInfoModel.setIdCardNo("无");
            }

            //亲缘样本处理
            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                if(TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_OLD.equals(instoreSampleType)
                        || TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_OLD.equals(instoreSampleType)
                        || TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_OLD.equals(instoreSampleType)){
                    List<RelativeSampleInfo> relativeSampleInfoList = sampleInfoService.selectRelativeSampleBySampleBarcode(sampleInfoModel.getEvidenceCode());
                    if(ListUtils.isNotEmptyList(relativeSampleInfoList)){
                        for(RelativeSampleInfo relativeSampleInfo : relativeSampleInfoList){
                            if(relativeSampleInfo.getSampleAid().equals(sampleInfoModel.getEvidenceCode())
                                    && !relativeSampleInfo.getSampleBid().equals(sampleInfoModel.getEvidenceCode())){

                                RelativeDefModel relativeDef = this.sampleInfoService.selectRelationByBarcode(relativeSampleInfo.getSampleAid());
                                if(relativeDef.getRelative1Id() != null && !relativeDef.getRelative1Id().equals("-1")) {
                                    sampleInfoModel.setRelationObjectId(relativeDef.getRelative1Id());
                                } else {
                                    sampleInfoModel.setRelationObjectId(sampleInfoModel.getPersonnelNo());
                                }

                                if(sampleInfoModel.getRelativeType() != null) {
                                    if(relativeSampleInfo.getRelationType() == 2) {
                                        sampleInfoModel.setRelation("3");
                                    } else if(relativeSampleInfo.getRelationType() == 3) {
                                        sampleInfoModel.setRelation("2");
                                    }
                                } else {
                                    sampleInfoModel.setRelation("无");
                                }

                                break;
                            }
                        }
                    }
                }
            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){

            }


        }

        sampleInfoModel.setSelfObjectType("0");

        if(StringUtils.isBlank(sampleInfoModel.getCertificateName())){
            sampleInfoModel.setCertificateName(caseInfoConfigs.getCredentialTypeIdcard());
        }

        if(StringUtils.isBlank(sampleInfoModel.getCertificateNo())){
            sampleInfoModel.setCertificateNo("无");
        }


        return sampleInfoModel;
    }

    private SampleInfoModel setRelationsInfo(CaseInfoModel caseInfoModel, SampleInfoModel sampleInfoModel){
        SampleInfoModel childSampleInfoModel1 = new SampleInfoModel();
        String relationTypeId = sampleInfoService.selectSingleRelation(sampleInfoModel.getId());
        logger.info("sampleBarcode: " + sampleInfoModel.getId() + ", relationTypeId: " + relationTypeId);
        //非单亲
        if(StringUtils.isNotBlank(relationTypeId)
                && !TransferConstants.RELATION_TYPE_SINGLE.equals(relationTypeId)){
            List<RelativeSampleInfo> relativeSampleInfoList = sampleInfoService.selectRelativeSampleBySampleBarcode(sampleInfoModel.getEvidenceCode());
            if(ListUtils.isNotEmptyList(relativeSampleInfoList)){
                RelativeSampleInfo relativeSampleInfo = relativeSampleInfoList.get(0);
                String sampleAId = relativeSampleInfo.getSampleAid();
                String sampleBId = relativeSampleInfo.getSampleBid();

                SampleInfoModel tempSampleMode = null;
                String refMemberId = null;

                if(sampleAId.equals(sampleInfoModel.getEvidenceCode())){
                    tempSampleMode = this.sampleInfoService.selectSampleInfoByBarcode(sampleAId);
                    if(tempSampleMode != null){
                        refMemberId = tempSampleMode.getPersonnelNo();
                        if(refMemberId != null && !"-1".equals(refMemberId)) {
                            sampleInfoModel.setSelfObjectId(refMemberId);
                        } else {
                            sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                        }
                    }

                    if("1".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("2".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MOTHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("3".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("4".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("5".equals(tempSampleMode.getRelativeType())) {
                        if(sampleInfoModel.getGender() != null
                                && !"null".equals(sampleInfoModel.getGender())) {
                            if(TransferConstants.NATION_GENDER_MALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                            }else if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                            }
                        } else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else {
                        if(!"6".equals(tempSampleMode.getRelativeType())) {
                            return null;
                        }

                        if(relativeSampleInfo.getRelationType() == 3) {
                            sampleInfoModel.setRelation("1");
                        } else if(relativeSampleInfo.getRelationType() == 2) {
                            sampleInfoModel.setRelation("8");
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    }


                    logger.info("retiveType: " + tempSampleMode.getRelativeType() + ", relation: " + sampleInfoModel.getRelation());

                    childSampleInfoModel1.setPersonnelName("被寻人");
                    childSampleInfoModel1.setPersonnelType(TransferConstants.NATION_SAMPLE_CATEGORY_MISSING);
                    childSampleInfoModel1.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                    childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    childSampleInfoModel1.setIdCardNo("无");
                    childSampleInfoModel1.setCreateUser(caseInfoModel.getCreateUser());
                    childSampleInfoModel1.setCreateDatetime(caseInfoModel.getCreateDatetime());
                    childSampleInfoModel1.setUpdateUser(caseInfoModel.getAcceptorName());
                    childSampleInfoModel1.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());
                    /*List refMemberId1 = this.memberService.getMemberList(this.caseInfoModel.getId(), "14");
                    if(refMemberId1 != null && refMemberId1.size() > 0) {
                        childSampleInfoModel1.setPersonnelName(((PersonInfo)refMemberId1.get(0)).getPerson_name());
                        childSampleInfoModel1.setGender(((PersonInfo)refMemberId1.get(0)).getGender());
                    }*/

                    sampleInfoModel.setChildSampleInfoModel(childSampleInfoModel1);
                }else if(sampleBId.equals(sampleInfoModel.getEvidenceCode())){
                    tempSampleMode = this.sampleInfoService.selectSampleInfoByBarcode(sampleBId);
                    if(tempSampleMode != null){
                        refMemberId = tempSampleMode.getPersonnelNo();
                        if(refMemberId != null && !"-1".equals(refMemberId)) {
                            sampleInfoModel.setSelfObjectId(refMemberId);
                        } else {
                            sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                        }
                    }

                    if("1".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("2".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MOTHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("3".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("4".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("5".equals(tempSampleMode.getRelativeType())) {
                        if(sampleInfoModel.getGender() != null
                                && !"null".equals(sampleInfoModel.getGender())) {
                            if(TransferConstants.NATION_GENDER_MALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                            }else if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                            }
                        } else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else {
                        if(!"6".equals(tempSampleMode.getRelativeType())) {
                            return null;
                        }

                        if(relativeSampleInfo.getRelationType() == 3) {
                            sampleInfoModel.setRelation("2");
                        } else if(relativeSampleInfo.getRelationType() == 2) {
                            sampleInfoModel.setRelation("3");
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel1.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    }

                    logger.info("retiveType: " + tempSampleMode.getRelativeType() + ", relation: " + sampleInfoModel.getRelation());

                    sampleInfoModel.setChildSampleInfoModel(childSampleInfoModel1);
                }
            }

        }else{
            //单亲
            SampleInfoModel childSampleInfoModel2 = new SampleInfoModel();
            if(sampleInfoModel != null) {
                String personId = sampleInfoModel.getPersonnelNo();
                if(StringUtils.isNotBlank(personId) && !"-1".equals(personId)) {
                    sampleInfoModel.setSelfObjectId(personId);
                } else {
                    sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                }
            }

            if("1".equals(sampleInfoModel.getRelativeType())) {
                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            } else if("2".equals(sampleInfoModel.getRelativeType())) {
                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MOTHER);
                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            } else if("3".equals(sampleInfoModel.getRelativeType())) {
                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            } else if("4".equals(sampleInfoModel.getRelativeType())) {
                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            } else if("5".equals(sampleInfoModel.getRelativeType())) {
                if(sampleInfoModel.getGender() != null
                        && !"null".equals(sampleInfoModel.getGender())) {
                    if(TransferConstants.NATION_GENDER_MALE.equals(sampleInfoModel.getGender())){
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                    }else if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                    }
                } else {
                    sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                }

                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            } else {
                if(!"6".equals(sampleInfoModel.getRelativeType())) {
                    return null;
                }

                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                sampleInfoModel.setRelationObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setPersonnelNo(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
                childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getEvidenceCode() + sampleInfoModel.getEvidenceCode());
            }

            childSampleInfoModel2.setPersonnelName("被寻人");
            childSampleInfoModel2.setPersonnelType("010306");
            childSampleInfoModel2.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
            childSampleInfoModel2.setSelfObjectId(sampleInfoModel.getId());
            childSampleInfoModel2.setIdCardNo("无");
            childSampleInfoModel2.setCreateUser(caseInfoModel.getCreateUser());
            childSampleInfoModel2.setCreateDatetime(caseInfoModel.getCreateDatetime());
            childSampleInfoModel2.setUpdateUser(caseInfoModel.getAcceptorName());
            childSampleInfoModel2.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());

            sampleInfoModel.setChildSampleInfoModel(childSampleInfoModel2);
        }

        return sampleInfoModel;
    }

    /**
     * 获取委托信息Model
     * @param caseId
     * @return
     */
    private ConsignmentModel getConsignmentModel(String caseId, String sampleBarcode){
        ConsignmentModel consignmentModel = null;
        String consignmentId = null;
        List<String> instoreSampleTypeList = null;
        if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
            consignmentModel = caseInfoService.selectConsignmentModelByCaseId(caseId);
            if(consignmentModel == null){
                return null;
            }
            consignmentId = caseInfoService.selectConsignmentIdByCaseId(caseId);
            instoreSampleTypeList = sampleInfoService.selectSampleTypeByCaseId(caseId);
        }else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
            consignmentModel = caseInfoService.selectNewConsignmentModelByCaseId(caseId);
            if(consignmentModel == null){
                return null;
            }

            instoreSampleTypeList = sampleInfoService.selectBySampleBarcode(sampleBarcode);
            consignmentId = consignmentModel.getId();
        }
        if(consignmentModel == null){
            return null;
        }

        consignmentModel.setId(consignmentId+"HHH");

        //通过样本入库类型判断当前委托是否为失踪人口委托
        boolean isMissingPeopleConsignment = true;
        if(ListUtils.isNotEmptyList(instoreSampleTypeList)){
            for(String sampleType : instoreSampleTypeList){
                if(!TransferHelper.isSampleTypeMissingPeople(transferNationParamsConfig.getLimsVersion(), sampleType)){
                    isMissingPeopleConsignment = false;
                    break;
                }
            }
        }

        /**
         * 委托类型
         */
        if(isMissingPeopleConsignment){
            consignmentModel.setCategory(caseInfoConfigs.getConsignmentTypeMissing());
        }else{
            consignmentModel.setCategory(caseInfoConfigs.getConsignmentTypePerson());
        }

        /**
         * 判断委托编号
         */
        if(StringUtils.isBlank(consignmentModel.getConsignmentNo())){
            if(StringUtils.isNotBlank(consignmentModel.getAcceptNo())){
                consignmentModel.setConsignmentNo(consignmentModel.getAcceptNo());
            }else{
                consignmentModel.setConsignmentNo(consignmentModel.getId());
            }
        }

        /**
         * 证件类型
         */
        if(StringUtils.isNotBlank(consignmentModel.getConsignerCertificateType())){
            consignmentModel.setConsignerCertificateType(TransferHelper.convertToNationCredentialType(transferNationParamsConfig.getLimsVersion(), consignmentModel.getConsignerCertificateType(), caseInfoConfigs));
        }else{
            consignmentModel.setConsignerCertificateType(caseInfoConfigs.getCredentialTypeDefault());
        }
        if(StringUtils.isBlank(consignmentModel.getConsignerCertificateNo())){
            consignmentModel.setConsignerCertificateNo("无");
        }

        if(StringUtils.isNotBlank(consignmentModel.getConsignerCertificateType2())){
            consignmentModel.setConsignerCertificateType2(TransferHelper.convertToNationCredentialType(transferNationParamsConfig.getLimsVersion(), consignmentModel.getConsignerCertificateType2(), caseInfoConfigs));
        }else{
            consignmentModel.setConsignerCertificateType2(caseInfoConfigs.getCredentialTypeDefault());
        }
        if(StringUtils.isBlank(consignmentModel.getConsignerCertificateNo2())){
            consignmentModel.setConsignerCertificateNo2("无");
        }

        String identifiyRequestStr = "";
        String identifiyRequest = consignmentModel.getIdentifiyRequest();
        if(StringUtils.isNotBlank(identifiyRequest)){
            if(identifiyRequest.contains(",")){
                String[] arr0 = identifiyRequest.split(",");
                for(String arr : arr0){
                    identifiyRequestStr += TransferHelper.convertIdentifyRequest(transferNationParamsConfig.getLimsVersion(), arr) + "  ";
                }
            }else{
                identifiyRequestStr = TransferHelper.convertIdentifyRequest(transferNationParamsConfig.getLimsVersion(), identifiyRequest);
            }
        }else{
            identifiyRequestStr = "DNA检验";
        }
        consignmentModel.setIdentifiyRequest(identifiyRequestStr);
        consignmentModel.setAcceptRegionalism(transferNationParamsConfig.getLabServerNo());
        consignmentModel.setAcceptOrgName(transferNationParamsConfig.getLabServerName());

        if(StringUtils.isBlank(consignmentModel.getConsignerName2())){
            consignmentModel.setConsignerName2(consignmentModel.getConsignerName());
        }
        if(StringUtils.isBlank(consignmentModel.getConsignerPhone2())){
            consignmentModel.setConsignerPhone2(consignmentModel.getConsignerPhone());
        }

        return consignmentModel;
    }

    /**
     * 标记队列处理失败
     * @param transferPersonQueue
     * @param transferStatus
     * @param failedMsg
     */
    private void markQueueError(TransferPersonQueue transferPersonQueue, int transferStatus, String failedMsg){
        try {
            transferPersonQueue.setTransferStatus(transferStatus);
            transferPersonQueue.setFailedMsg(failedMsg);
            transferPersonQueue.setTransferDatetime(new Date());

            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                oldLimsPersonQueueService.updateStatus(transferPersonQueue);
            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                newLimsPersonQueueService.updateStatus(transferPersonQueue);
            }
        }catch(Exception ex){
            logger.error("markQueueError标记建库上报队列处理失败时出现错误！", ex);
        }
    }

}
