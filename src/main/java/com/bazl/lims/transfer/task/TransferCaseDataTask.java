package com.bazl.lims.transfer.task;

import com.alibaba.fastjson.JSONObject;
import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.ExternalSysInfo;
import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.CaseInfoService;
import com.bazl.lims.transfer.service.NewLimsCaseQueueService;
import com.bazl.lims.transfer.service.OldLimsCaseQueueService;
import com.bazl.lims.transfer.service.SampleInfoService;
import com.bazl.lims.transfer.task.service.SubmitToNation2CriminalService;
import com.bazl.lims.transfer.task.service.SubmitToNation2PersonService;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import com.bazl.lims.transfer.utils.TransferSpecialMarkerUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.*;

/**
 * @author
 *
 * 上报案件数据定时任务
 *
 * Created by lizhihua on 2019-05-08.
 */
public class TransferCaseDataTask extends QuartzJobBean {

    private final Logger logger = LoggerFactory.getLogger(TransferCaseDataTask.class);

   /* @Autowired
    TransferCaseQueueService transferCaseQueueService;*/
    @Autowired
    OldLimsCaseQueueService oldLimsCaseQueueService;
    @Autowired
    NewLimsCaseQueueService newLimsCaseQueueService;
    @Autowired
    CaseInfoService caseInfoService;
    @Autowired
    SampleInfoService sampleInfoService;
    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;
    @Autowired
    CaseInfoConfigs caseInfoConfigs;
    @Autowired
    SubmitToNation2CriminalService submitToNation2CriminalService;

    @Autowired
    SubmitToNation2PersonService submitToNation2PersonService;

    @Autowired
    NationDataConverterMap nationDataConverterMap;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        if(!transferNationParamsConfig.isTransferCaseActived()){
            logger.info("******************案件上报任务未开启！********************" + System.currentTimeMillis());
            return;
        }

        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            oldVersionTransfer();
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            shunyiVersionTransfer();
        }

    }

    private void shunyiVersionTransfer(){
        if(!TaskStaticFlag.TRANSFER_CASE_RUUNING) {
            logger.info("******************开始执行newLims案件上报队列任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.TRANSFER_CASE_RUUNING = true;

            List<TransferCaseQueue> transferCaseQueueList = null;
            /**
             *  先获取待处理队列，优先进行处理
             */
            try {
                logger.info("******************执行newLims待处理案件上报队列任务！********************" + System.currentTimeMillis());

                transferCaseQueueList = newLimsCaseQueueService.selectPendingList(transferNationParamsConfig.getLabServerNo());
                if (ListUtils.isNotEmptyList(transferCaseQueueList)) {
                    executeTransferCaseQueue(transferCaseQueueList);
                }

                logger.info("******************完成newLims待处理案件上报队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("newLims待处理案件队列执行过程中出现错误！", ex);
            }

            TaskStaticFlag.TRANSFER_CASE_RUUNING = false;
            logger.info("******************完成newLims执行委托反馈任务！********************" + System.currentTimeMillis());
        }
    }

    private void oldVersionTransfer() {
        if(!TaskStaticFlag.TRANSFER_CASE_RUUNING) {
            logger.info("******************开始执行案件上报队列任务！********************" + System.currentTimeMillis());

            TaskStaticFlag.TRANSFER_CASE_RUUNING = true;

            List<TransferCaseQueue> transferCaseQueueList = null;
            /**
             *  先获取待处理队列，优先进行处理
             */
            try {
                logger.info("******************执行待处理案件上报队列任务！********************" + System.currentTimeMillis());

                transferCaseQueueList = oldLimsCaseQueueService.selectPendingList(transferNationParamsConfig.getLabServerNo());
                if (ListUtils.isNotEmptyList(transferCaseQueueList)) {
                    executeTransferCaseQueue(transferCaseQueueList);
                }

                logger.info("******************完成待处理案件上报队列任务！********************" + System.currentTimeMillis());
            } catch (Exception ex) {
                logger.error("待处理案件队列执行过程中出现错误！", ex);
            }

            TaskStaticFlag.TRANSFER_CASE_RUUNING = false;
            logger.info("******************完成执行委托反馈任务！********************" + System.currentTimeMillis());
        }
    }


    /**
     * 执行案件数据上报
     * @throws Exception
     */
    private void executeTransferCaseQueue(List<TransferCaseQueue> transferCaseQueueList) throws Exception {
        logger.info("****************加载案件上报队列数量：" + transferCaseQueueList.size() + "条！*****************");

        if(transferCaseQueueList.size()>0){
            logger.info("****************开始执行案件上报队列*****************");

            SubmitNationDataModel submitNationDataModel = null;
            CaseInfoModel caseInfoModel = null;

            //违法犯罪人员样本列表
            List<SampleInfoModel> wffzrySampleInfoModelList = null;
            List<SampleGeneInfoModel> wffzrySampleGeneInfoModelList = null;

            for(TransferCaseQueue transferCaseQueue : transferCaseQueueList){
                submitNationDataModel = getSubmitNationDataModel(transferCaseQueue);
                logger.info("TransferCaseDataTask.submitNationDataModel(132)="+submitNationDataModel);
                if(submitNationDataModel == null){
                    continue;
                }

                // edit by lizhihua , 2020-05-17
                // 将包含“违法犯罪人员”的数据拆分出来
                wffzrySampleInfoModelList = new ArrayList<>();
                wffzrySampleGeneInfoModelList = new ArrayList<>();

                List<SampleInfoModel> sampleInfoModelList = submitNationDataModel.getSampleInfoModelList();
                SampleInfoModel tempSampleInfoModel = null;
                for(Iterator<SampleInfoModel> it = sampleInfoModelList.iterator(); it.hasNext();){
                    tempSampleInfoModel = it.next();
                    if(StringUtils.isNotBlank(tempSampleInfoModel.getPersonnelType())
                            && TransferConstants.NATION_SAMPLE_CATEGORY_OFFENDER.equals(tempSampleInfoModel.getPersonnelType())){
                        wffzrySampleInfoModelList.add(tempSampleInfoModel);

                        it.remove();
                    }
                }
                submitNationDataModel.setSampleInfoModelList(sampleInfoModelList);

                if(ListUtils.isNotEmptyList(wffzrySampleInfoModelList)){
                    List<SampleGeneInfoModel> sampleGeneInfoModelList = submitNationDataModel.getSampleGeneInfoModelList();
                    SampleGeneInfoModel tempSampleGeneInfoModel = null;
                    for(Iterator<SampleGeneInfoModel> it = sampleGeneInfoModelList.iterator(); it.hasNext();){
                        tempSampleGeneInfoModel = it.next();

                        for(SampleInfoModel sim : wffzrySampleInfoModelList){
                            if(tempSampleGeneInfoModel.getSampleId().equals(sim.getId())){
                                wffzrySampleGeneInfoModelList.add(tempSampleGeneInfoModel);
                                it.remove();

                                break;
                            }
                        }
                    }

                    submitNationDataModel.setSampleGeneInfoModelList(sampleGeneInfoModelList);
                }

                /**
                 * 如果上报案件中包含失踪人员，则将委托类型修改为失踪人口委托
                 */
                boolean hasMissPerson = false;
                for(SampleInfoModel sim : sampleInfoModelList){
                    if(TransferConstants.NATION_SAMPLE_CATEGORY_MISSING.equals(sim.getPersonnelType())){
                        hasMissPerson = true;
                        break;
                    }
                }
                if(hasMissPerson){
                    //将委托类型修改为失踪人口委托
                    ConsignmentModel consignmentModel = submitNationDataModel.getConsignmentModel();
                    consignmentModel.setCategory(caseInfoConfigs.getConsignmentTypeMissing());

                    submitNationDataModel.setConsignmentModel(consignmentModel);
                }

                Map<String, String> transferMap = null;
                if(ListUtils.isNotEmptyList(sampleInfoModelList)) {
                    /**
                     * edit by lizhihua 2020-05-26
                     * 拆出违法犯罪人员后，如果还有其他类型的案件样本则继续上报，如果没有其他样本，则不再上报案件。
                     */
                    transferMap = submitToNation2CriminalService.submitToNation(submitNationDataModel);
                    //transferCase(transferCaseQueue, submitNationDataModel);
                }

                if(transferMap != null){
                    updateTransferCaseQueue(transferCaseQueue, transferMap);
                }

                /**
                 * 将拆分出来的违法犯罪人员样本，调用建库上报service进行处理
                 */
                if(ListUtils.isNotEmptyList(wffzrySampleInfoModelList)
                        && ListUtils.isNotEmptyList(wffzrySampleGeneInfoModelList)){
                    logger.info("案件 [ "+submitNationDataModel.getConsignmentModel().getAcceptNo()+" ] 中拆分出违法犯罪人员 "+wffzrySampleInfoModelList.size()+" 个，以建库人员类型进行上报。");

                    submitNationDataModel.setSampleInfoModelList(wffzrySampleInfoModelList);
                    submitNationDataModel.setSampleGeneInfoModelList(wffzrySampleGeneInfoModelList);
                    List<Map<String, String>> transferMapList = transferCriminalPerson(submitNationDataModel);

                    if(transferMap == null){
                        if(ListUtils.isNotEmptyList(transferMapList)){
                            updateTransferCaseQueue(transferCaseQueue, transferMapList.get(0));
                        }
                        /*for(Map<String, String> map : transferMapList){
                            updateTransferCaseQueue(transferCaseQueue, map);
                        }*/
                    }
                }
            }
        }
    }

    private void updateTransferCaseQueue(TransferCaseQueue transferCaseQueue, Map<String, String> transferMap) throws Exception {
        try {
            String transferResult = transferMap.get("result");
            String[] filepath = (transferMap.get("message")).split("\\\\");
            String filename = filepath[filepath.length - 1];
            if (transferResult.equals("-1")) {
                transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_ERROR);
                transferCaseQueue.setFailedMsg(filename);
                transferCaseQueue.setTransferFileName(filename);
                transferCaseQueue.setTransferDatetime(new Date());
                if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
                    oldLimsCaseQueueService.updateStatus(transferCaseQueue);
                } else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
                    newLimsCaseQueueService.updateStatus(transferCaseQueue);
                }
                logger.info("---STNL2---案件：" + filename + "，上报失败！----");
            } else if (transferResult.equals("1")) {
                transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_SUCCEED);
                transferCaseQueue.setTransferFileName(filename);
                transferCaseQueue.setTransferDatetime(new Date());
                if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
                    oldLimsCaseQueueService.updateStatus(transferCaseQueue);
                } else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
                    newLimsCaseQueueService.updateStatus(transferCaseQueue);
                }
                logger.info("---STNL2---案件：" + filename + "，上报国家库2期生成文件成功！------");
                logger.info((String) transferMap.get("message") == null ? "" : (String) transferMap.get("message"));
            } else if (transferResult.equals("-3")) {
                transferCaseQueue.setTransferStatus(TransferConstants.TRANSFER_STATUS_GENERATE_ERROR);
                transferCaseQueue.setTransferFileName(filename);
                transferCaseQueue.setTransferDatetime(new Date());
                if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
                    oldLimsCaseQueueService.updateStatus(transferCaseQueue);
                } else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
                    newLimsCaseQueueService.updateStatus(transferCaseQueue);
                }
            }
        }catch(Exception ex){
            logger.error("invoke TransferCaseDataTask.transferCase error.", ex);
        }
    }

    private List<Map<String, String>> transferCriminalPerson(SubmitNationDataModel submitNationDataModel){
        List<Map<String, String>> transferMapList = new ArrayList<>();

        try {
            Map<String, String> transferMap = null;

            List<SampleInfoModel> wffzrySampleInfoModelList = submitNationDataModel.getSampleInfoModelList();

            ConsignmentModel consignmentModel = submitNationDataModel.getConsignmentModel();
            String consignmentId = consignmentModel.getId();
            for(int i = 1; i <= wffzrySampleInfoModelList.size(); i++){
                consignmentModel.setCategory(caseInfoConfigs.getConsignmentTypePerson());
                consignmentModel.setId(consignmentId + "2003-FY1301-" + i);
                consignmentModel.setAcceptNo("2003-FY1301");
                consignmentModel.setConsignmentNo("2003-FY1301");
                consignmentModel.setIdentifiyRequest("DNA检验");

                submitNationDataModel.setConsignmentModel(consignmentModel);
                submitNationDataModel.setSampleInfoModel(wffzrySampleInfoModelList.get(i-1));

                transferMap = submitToNation2PersonService.submitToNation(submitNationDataModel);

                if(transferMap != null){
                    transferMapList.add(transferMap);
                }
            }
        }catch(Exception ex){
            logger.error("invoke TransferCaseDataTask.transferCriminalPerson error.", ex);
        }

        return transferMapList;
    }


    private SubmitNationDataModel getSubmitNationDataModel(TransferCaseQueue transferCaseQueue){
        String queueId = transferCaseQueue.getId();
        String caseId = transferCaseQueue.getSampleBarcode();
        SubmitNationDataModel submitNationDataModel = new SubmitNationDataModel();
        logger.info("---STNL2---案件id="+caseId);
        /**
         * 获取案件信息
         */
        CaseInfoModel caseInfoModel = getCaseInfoModel(caseId);
        logger.info("---STNL2---案件：获取案件信息="+caseInfoModel);
        if(caseInfoModel == null){
            logger.info("---STNL2---案件：获取案件信息失败-- ");
            markQueueError(transferCaseQueue, TransferConstants.TRANSFER_STATUS_GENERATE_ERROR, "caseId[" + caseId + "]对应案件信息不存在");
            return null;
        }
        submitNationDataModel.setCaseInfoModel(caseInfoModel);

        /**
         * 获取委托信息
         */
        ConsignmentModel consignmentModel = getConsignmentModel(caseId);
        logger.info("---STNL2---案件：获取案件委托信息="+consignmentModel);
        if(consignmentModel == null){
            logger.info("---STNL2---案件：获取案件委托信息失败-- ");
            markQueueError(transferCaseQueue, TransferConstants.TRANSFER_STATUS_GENERATE_ERROR, "caseId[" + caseId + "]对应委托信息不存在");
            return null;
        }
        submitNationDataModel.setConsignmentModel(consignmentModel);

        /**
         * 获取物证信息
         */
        List<PhysicalEvidenceModel> physicalEvidenceModelList = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            physicalEvidenceModelList = sampleInfoService.selectPhysicalEvidenceByCaseQueueId(queueId);
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            physicalEvidenceModelList = sampleInfoService.selectNewPhysicalEvidenceByCaseQueueId(queueId);
        }
        if(ListUtils.isNotEmptyList(physicalEvidenceModelList)){
            physicalEvidenceModelList = getPhysicalEvidenceModelList(caseInfoModel, physicalEvidenceModelList);
        }else{
            physicalEvidenceModelList = new ArrayList<>();
        }
        submitNationDataModel.setPhysicalEvidenceModelList(physicalEvidenceModelList);

        /**
         * 获取样本信息
         */
        List<SampleInfoModel> sampleInfoModelList = null;
        List<SampleInfoModel> queueSampleInfoModelList = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            queueSampleInfoModelList = sampleInfoService.selectSampleInfoListByCaseQueueId(queueId);
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            queueSampleInfoModelList = sampleInfoService.selectNewSampleInfoListByCaseQueueId(queueId);
        }
        if(ListUtils.isNotEmptyList(queueSampleInfoModelList)){
            sampleInfoModelList = getSampleInfoModelList(caseInfoModel, queueSampleInfoModelList);
        }else{
            sampleInfoModelList = new ArrayList<>();
        }
        submitNationDataModel.setSampleInfoModelList(sampleInfoModelList);

        /**
         * 获取基因信息
         */
        List<SampleGeneInfoModel> sampleGeneInfoModelList = new ArrayList<>();
        if(ListUtils.isNotEmptyList(queueSampleInfoModelList)){
            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                sampleGeneInfoModelList = getSampleGeneInfoModelList(caseInfoModel, queueSampleInfoModelList);
            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                sampleGeneInfoModelList = getNewSampleGeneInfoModelList(caseInfoModel, queueId);
            }
        }
        submitNationDataModel.setSampleGeneInfoModelList(sampleGeneInfoModelList);

        return submitNationDataModel;
    }


    /**
     * 获取案件信息Model
     * @param caseId
     * @return
     */
    private CaseInfoModel getCaseInfoModel(String caseId){
        CaseInfoModel caseInfoModel = null;
        if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)) {
            caseInfoModel = caseInfoService.selectCaseInfoModelByCaseId(caseId);
        }else if (transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)) {
            caseInfoModel = caseInfoService.selectNewCaseInfoModelByCaseId(caseId);
        }
        if(caseInfoModel == null){
            return null;
        }

        List<ExternalSysInfo> externalSysInfoList = new ArrayList<>();

        if(StringUtils.isNotBlank(caseInfoModel.getCaseXkSn())){
            ExternalSysInfo xkExternalSysInfo = new ExternalSysInfo();
            xkExternalSysInfo.setExternalSysType(caseInfoConfigs.getExternalSysTypeXk());
            xkExternalSysInfo.setExternalSysNo(caseInfoModel.getCaseXkSn());

            externalSysInfoList.add(xkExternalSysInfo);
        }

        if(StringUtils.isNotBlank(caseInfoModel.getCaseOldSn())){
            ExternalSysInfo jzExternalSysInfo = new ExternalSysInfo();
            jzExternalSysInfo.setExternalSysType(caseInfoConfigs.getExternalSysTypeJz());
            jzExternalSysInfo.setExternalSysNo(caseInfoModel.getCaseOldSn());

            externalSysInfoList.add(jzExternalSysInfo);
        }

        if(ListUtils.isNotEmptyList(externalSysInfoList)){
            caseInfoModel.setExternalSysInfoList(externalSysInfoList);
        }

        /**
         * 案件类型 转换
         */
        if(StringUtils.isNotBlank(caseInfoModel.getCaseType())){
            if(TransferHelper.isCaseTypeCriminal(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseType())){
                caseInfoModel.setCaseType(caseInfoConfigs.getCaseTypeCriminal());
            }else if(TransferHelper.isCaseTypeNotCriminal(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseType())){
                caseInfoModel.setCaseType(caseInfoConfigs.getCaseTypeNotCriminal());
            }else if(TransferHelper.isCaseTypeCivil(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseType())){
                caseInfoModel.setCaseType(caseInfoConfigs.getCaseTypeCivil());
            }
        }else{
            caseInfoModel.setCaseType(caseInfoConfigs.getDefaultCaseType());
        }

        /**
         * 案件性质转换
         */
        if(StringUtils.isNotBlank(caseInfoModel.getCaseProperty())){
            Map<String, String> casePropertyMap = null;
            if(TransferConstants.LIMS_VERSION_OLD.equals(transferNationParamsConfig.getLimsVersion())) {
                casePropertyMap = nationDataConverterMap.getOldLimsCasePropertyMap();
            }else{
                casePropertyMap = nationDataConverterMap.getNewLimsCasePropertyMap();
            }

            if(casePropertyMap != null
                    &&casePropertyMap.containsKey(caseInfoModel.getCaseProperty())){
                caseInfoModel.setCaseProperty(casePropertyMap.get(caseInfoModel.getCaseProperty()));
            }else {
                caseInfoModel.setCaseProperty(TransferHelper.convertToNationCaseProperty(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseProperty(), caseInfoConfigs));
            }
        }else{
            caseInfoModel.setCaseProperty(caseInfoConfigs.getDefaultCaseProperty());
        }

        //logger.info("------------caseProperty is " + caseInfoModel.getCaseProperty() + "-----------------");

        /**
         * 案件状态转换
         */
        if(StringUtils.isNotBlank(caseInfoModel.getCaseStatus())){
            caseInfoModel.setCaseStatus(TransferHelper.convertToNationCaseStatus(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseStatus(), caseInfoConfigs));
        }else{
            caseInfoModel.setCaseStatus(caseInfoConfigs.getCaseStatusDefault());
        }

        /**
         * 案件级别转换
         */
        if(StringUtils.isNotBlank(caseInfoModel.getCaseLevel())){
            caseInfoModel.setCaseLevel(TransferHelper.convertToNationCaseLevel(transferNationParamsConfig.getLimsVersion(), caseInfoModel.getCaseLevel(), caseInfoConfigs));
        }else{
            caseInfoModel.setCaseLevel(null);
        }

        if(StringUtils.isBlank(caseInfoModel.getSceneRegionalism())){
            if(StringUtils.isNotBlank(caseInfoModel.getDelegateOrgCode())){
                if(caseInfoModel.getDelegateOrgCode().length()>6) {
                    caseInfoModel.setSceneRegionalism(caseInfoModel.getDelegateOrgCode().substring(0,6));
                }else{
                    caseInfoModel.setSceneRegionalism(caseInfoModel.getDelegateOrgCode());
                }
            }else{
                caseInfoModel.setSceneRegionalism("无");
            }
        }

        caseInfoModel.setXckyCallbackURL(TransferHelper.getXckyCallbackUrlByServerNoAndDelegateOrgCode(caseInfoModel.getDelegateOrgCode(), transferNationParamsConfig));

        return caseInfoModel;
    }

    /**
     * 获取委托信息Model
     * @param caseId
     * @return
     */
    private ConsignmentModel getConsignmentModel(String caseId){
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
            consignmentId = consignmentModel.getId();
            instoreSampleTypeList = sampleInfoService.selectNewSampleTypeByCaseId(caseId);
        }
        consignmentModel.setId(consignmentId);

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
            String consignmentType = TransferHelper.convertToNationConsignmentType(transferNationParamsConfig.getLimsVersion(), consignmentModel.getCategory(), caseInfoConfigs);
            consignmentModel.setCategory(consignmentType);
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

        return consignmentModel;
    }

    /**
     * 构建物证信息list
     * @param physicalEvidenceModelList
     * @return
     */
    private List<PhysicalEvidenceModel> getPhysicalEvidenceModelList(CaseInfoModel caseInfoModel, List<PhysicalEvidenceModel> physicalEvidenceModelList){
        List<PhysicalEvidenceModel> physicalEvidenceModelList1 = new ArrayList<>();

        for(PhysicalEvidenceModel physicalEvidenceModel : physicalEvidenceModelList){
            logger.info("------------physicalEvidenceModel is " + physicalEvidenceModel.getId() + "---------------");

            if(StringUtils.isNotBlank(physicalEvidenceModel.getPhysicalEvidenceType())){
                physicalEvidenceModel.setPhysicalEvidenceType(TransferHelper.convertEvidenceCarrierType(transferNationParamsConfig.getLimsVersion(), physicalEvidenceModel.getPhysicalEvidenceType(), caseInfoConfigs));
            }else{
                physicalEvidenceModel.setPhysicalEvidenceType(caseInfoConfigs.getEvidenceCarrierTypeDefault());
            }

            physicalEvidenceModel.setCreateUser(caseInfoModel.getCreateUser());
            physicalEvidenceModel.setCreateDatatime(caseInfoModel.getCreateDatetime());
            physicalEvidenceModel.setUpdateUser(caseInfoModel.getAcceptorName());
            physicalEvidenceModel.setUpdateDatetime(caseInfoModel.getUpdateDatetime());

            physicalEvidenceModel.setExternalSysNo(physicalEvidenceModel.getExternalSysNo() == null ? "无" : physicalEvidenceModel.getExternalSysNo());
            physicalEvidenceModel.setExternalSysType(physicalEvidenceModel.getExternalSysType() == null ? "无" : "1");

            physicalEvidenceModelList1.add(physicalEvidenceModel);
        }

        return physicalEvidenceModelList1;
    }


    /**
     * 筛选出嫌疑人样本列表
     * @param sampleInfoModelList
     * @return
     */
    private List<SampleInfoModel> getSuspectPersonSampleList(List<SampleInfoModel> sampleInfoModelList){
        List<SampleInfoModel> suspectPersonSampleList = new ArrayList<>();

        InstoreSampleInfo instoreSampleInfo = null;
        for(SampleInfoModel sampleInfoModel : sampleInfoModelList) {
            instoreSampleInfo = sampleInfoService.selectInstoreSampleInfoBySampleBarcode(sampleInfoModel.getEvidenceCode());
            if (instoreSampleInfo == null) {
                continue;
            }

            if(TransferConstants.INSTORE_SAMPLE_TYPE_OFFENDER_OLD.equals(String.valueOf(instoreSampleInfo.getSampletype()))
                    || TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_OLD.equals(String.valueOf(instoreSampleInfo.getSampletype()))) {
                suspectPersonSampleList.add(sampleInfoModel);
            }
        }

        return suspectPersonSampleList;
    }


    /**
     * 构建物证信息list
     * @param sampleInfoModelList
     * @return
     */
    private List<SampleInfoModel> getSampleInfoModelList(CaseInfoModel caseInfoModel, List<SampleInfoModel> sampleInfoModelList){
        List<SampleInfoModel> sampleInfoModelList1 = new ArrayList<>();

        InstoreSampleInfo instoreSampleInfo = null;
        String instoreSampleType = null;
        for(SampleInfoModel sampleInfoModel : sampleInfoModelList){
            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                instoreSampleInfo = sampleInfoService.selectInstoreSampleInfoBySampleBarcode(sampleInfoModel.getEvidenceCode());
                if(instoreSampleInfo == null){
                    continue;
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
                        SampleInfoModel infoModel = setRelationsInfo(caseInfoModel, sampleInfoModel);
                        if (infoModel != null) {
                            sampleInfoModel = infoModel;
                        }
                    }
                }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                    if (TransferConstants.INSTORE_SAMPLE_TYPE_SUSPECT_RELATIVE_NEW.equals(String.valueOf(instoreSampleType))
                            || TransferConstants.INSTORE_SAMPLE_TYPE_VICTIM_RELATIVE_NEW.equals(String.valueOf(instoreSampleType))
                            || TransferConstants.INSTORE_SAMPLE_TYPE_MISSING_RELATIVE_NEW.equals(String.valueOf(instoreSampleType))) {
                        SampleInfoModel infoModel = setRelationsInfo(caseInfoModel, sampleInfoModel);
                        if (infoModel != null) {
                            sampleInfoModel = infoModel;
                        }
                    }
                }
            }

            sampleInfoModel.setSelfObjectType("0");
            sampleInfoModelList1.add(sampleInfoModel);
        }

        return sampleInfoModelList1;
    }

    private SampleInfoModel setRelationsInfo(CaseInfoModel caseInfoModel, SampleInfoModel sampleInfoModel){
        logger.info("sampleBarcode: " + sampleInfoModel.getEvidenceCode());

        List<RelativeSampleInfo> relativeSampleInfoList = null;
        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
            relativeSampleInfoList = sampleInfoService.selectRelativeSampleBySampleBarcode(sampleInfoModel.getEvidenceCode());
        }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
            relativeSampleInfoList = sampleInfoService.selectNewRelativeSampleBySampleBarcode(sampleInfoModel.getEvidenceCode());
            if (ListUtils.isNotEmptyList(relativeSampleInfoList)) {
                //此步判断是寻找孩子还是配偶
                for (RelativeSampleInfo rsi : relativeSampleInfoList) {
                    rsi.setRelationType(getRelationTypeByRole(rsi.getSampleaRole(), rsi.getSamplebRole()));
                }
            }
        }

        if(ListUtils.isNotEmptyList(relativeSampleInfoList)){
            for (RelativeSampleInfo relativeSampleInfo : relativeSampleInfoList) {
                //3:寻找子女,2:寻找配偶,如果不等于跳过
                if ((relativeSampleInfo.getRelationType() != 3) && (relativeSampleInfo.getRelationType() != 2)) {
                    continue;
                }
                // 构造失踪人员（即 子 的人员信息 ），判断父母的人员信息是否存在，存在则构造孩子，不存在则都要构造
                SampleInfoModel childSampleInfoModel = new SampleInfoModel();

                String sampleAId = relativeSampleInfo.getSampleAid();
                String sampleBId = relativeSampleInfo.getSampleBid();

                SampleInfoModel tempSampleMode = null;
                String refMemberId = null;

                if(sampleAId.equals(sampleInfoModel.getEvidenceCode())){
                    if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                        tempSampleMode = this.sampleInfoService.selectSampleInfoByBarcode(sampleAId);
                    }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                        tempSampleMode = this.sampleInfoService.selectNewSampleInfoByBarcode(sampleAId);
                    }
                    if(tempSampleMode != null){
                        refMemberId = tempSampleMode.getPersonnelNo();
                        if(refMemberId != null && !"-1".equals(refMemberId)) {
                            sampleInfoModel.setSelfObjectId(refMemberId);
                        } else {
                            sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                        }
                        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                            //新版lims需要转换亲缘关系
                            if (StringUtils.isNotBlank(tempSampleMode.getRelativeType())) {
                                tempSampleMode.setRelativeType(TransferRelativeType(tempSampleMode.getRelativeType()));
                            }
                        }
                    }

                    if("1".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("2".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MOTHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("3".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("4".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("5".equals(tempSampleMode.getRelativeType())) {
                        if(sampleInfoModel.getGender() != null
                                && !"null".equals(sampleInfoModel.getGender())) {
                            if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                            }else {
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                            }
                        } else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else {
                        if(!"6".equals(tempSampleMode.getRelativeType())) {
                            return null;
                        }

                        if(relativeSampleInfo.getRelationType() == 3) {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        } else if(relativeSampleInfo.getRelationType() == 2) {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                        }else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_OTHER);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    }


                    logger.info("retiveType: " + tempSampleMode.getRelativeType() + ", relation: " + sampleInfoModel.getRelation());
                }else if(sampleBId.equals(sampleInfoModel.getEvidenceCode())){
                    if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                        tempSampleMode = this.sampleInfoService.selectSampleInfoByBarcode(sampleBId);
                    }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                        tempSampleMode = this.sampleInfoService.selectNewSampleInfoByBarcode(sampleBId);
                    }
                    if(tempSampleMode != null){
                        refMemberId = tempSampleMode.getPersonnelNo();
                        if(refMemberId != null && !"-1".equals(refMemberId)) {
                            sampleInfoModel.setSelfObjectId(refMemberId);
                        } else {
                            sampleInfoModel.setSelfObjectId(sampleInfoModel.getId());
                        }
                        if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                            //新版lims需要转换亲缘关系
                            if (StringUtils.isNotBlank(tempSampleMode.getRelativeType())) {
                                tempSampleMode.setRelativeType(TransferRelativeType(tempSampleMode.getRelativeType()));
                            }
                        }
                    }

                    if("1".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else if("2".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MOTHER);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("3".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("4".equals(tempSampleMode.getRelativeType())) {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_CHILDREN);
                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    } else if("5".equals(tempSampleMode.getRelativeType())) {
                        if(sampleInfoModel.getGender() != null
                                && !"null".equals(sampleInfoModel.getGender())) {
                            if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                            }else {
                                sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                            }
                        } else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleBId, sampleAId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleBId, sampleAId));
                    } else {
                        if(!"6".equals(tempSampleMode.getRelativeType())) {
                            return null;
                        }

                        if(relativeSampleInfo.getRelationType() == 3) {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FATHER);
                        } else if(relativeSampleInfo.getRelationType() == 2) {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                        }else {
                            sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_OTHER);
                        }

                        sampleInfoModel.setRelationObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setPersonnelNo(this.createRelationPersonInfo(sampleAId, sampleBId));
                        childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                    }

                    logger.info("retiveType: " + tempSampleMode.getRelativeType() + ", relation: " + sampleInfoModel.getRelation());
                }


                childSampleInfoModel.setPersonnelName("被寻人");
                //edit by lizhihua ,  2020-05-19， 因国家库接口变动，案件类型的数据中不能有失踪人员类型
                // childSampleInfoModel.setPersonnelType(TransferConstants.NATION_SAMPLE_CATEGORY_MISSING);
                childSampleInfoModel.setPersonnelType(TransferConstants.NATION_SAMPLE_CATEGORY_MISSING_VICTIM);
                childSampleInfoModel.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                childSampleInfoModel.setSelfObjectId(this.createRelationPersonInfo(sampleAId, sampleBId));
                childSampleInfoModel.setIdCardNo("无");
                childSampleInfoModel.setCreateUser(caseInfoModel.getCreateUser());
                childSampleInfoModel.setCreateDatetime(caseInfoModel.getCreateDatetime());
                childSampleInfoModel.setUpdateUser(caseInfoModel.getAcceptorName());
                childSampleInfoModel.setUpdateDatetime(sampleInfoModel.getUpdateDatetime());

                sampleInfoModel.setChildSampleInfoModel(childSampleInfoModel);
            }
        }

        return sampleInfoModel;
    }

    /**
     * 根据亲缘关系角色判断寻找配偶还是子女
     * @param sampleaRole
     * @param samplebRole
     * @return
     */
    private int getRelationTypeByRole(String sampleaRole, String samplebRole) {
        int relationType = 0;
        if (TransferConstants.RELATION_TYPE_01.equals(sampleaRole) || TransferConstants.RELATION_TYPE_03.equals(sampleaRole)) {
            if (TransferConstants.RELATION_TYPE_05.equals(samplebRole) || TransferConstants.RELATION_TYPE_06.equals(samplebRole)) {
                relationType = TransferConstants.FIND_SPOUSE;
            }
            if (TransferConstants.RELATION_TYPE_02.equals(samplebRole) || TransferConstants.RELATION_TYPE_04.equals(samplebRole)) {
                relationType = TransferConstants.FIND_CHILD;
            }
        }else if (TransferConstants.RELATION_TYPE_02.equals(sampleaRole) || TransferConstants.RELATION_TYPE_04.equals(sampleaRole)) {
            if (TransferConstants.RELATION_TYPE_05.equals(samplebRole) || TransferConstants.RELATION_TYPE_06.equals(samplebRole)) {
                relationType = TransferConstants.FIND_SPOUSE;
            }
            if (TransferConstants.RELATION_TYPE_01.equals(samplebRole) || TransferConstants.RELATION_TYPE_03.equals(samplebRole)) {
                relationType = TransferConstants.FIND_CHILD;
            }
        }else if (TransferConstants.RELATION_TYPE_05.equals(sampleaRole) || TransferConstants.RELATION_TYPE_06.equals(sampleaRole)) {
            if (TransferConstants.RELATION_TYPE_01.equals(samplebRole) || TransferConstants.RELATION_TYPE_03.equals(samplebRole)) {
                relationType = TransferConstants.FIND_SPOUSE;
            }
            if (TransferConstants.RELATION_TYPE_02.equals(samplebRole) || TransferConstants.RELATION_TYPE_04.equals(samplebRole)) {
                relationType = TransferConstants.FIND_SPOUSE;
            }
        }

        return relationType;
    }

    /*private SampleInfoModel setRelationsInfo(CaseInfoModel caseInfoModel, SampleInfoModel sampleInfoModel){
        SampleInfoModel childSampleInfoModel1 = new SampleInfoModel();
        String relationTypeId = sampleInfoService.selectSingleRelation(sampleInfoModel.getId());
        logger.info("sampleBarcode: " + sampleInfoModel.getId() + ", relationTypeId: " + relationTypeId);
        //非单亲
        if(StringUtils.isNotBlank(relationTypeId)
                && !TransferConstants.RELATION_TYPE_SINGLE.equals(relationTypeId)){

            List<RelativeSampleInfo> relativeSampleInfoList = sampleInfoService.selectRelativeSampleBySampleBarcode(sampleInfoModel.getEvidenceCode());
            if(ListUtils.isNotEmptyList(relativeSampleInfoList)){
                for (RelativeSampleInfo relativeSampleInfo : relativeSampleInfoList) {
                    if ((relativeSampleInfo.getRelationType() != 3) && (relativeSampleInfo.getRelationType() != 2)) {
                        continue;
                    }
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
                                if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                    sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                                }else {
                                    sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
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
                        childSampleInfoModel1.setUpdateDatetime(caseInfoModel.getUpdateDatetime());
                        List refMemberId1 = sampleInfoService.getMemberList(caseInfoModel.getId(), "14");
                        if(refMemberId1 != null && refMemberId1.size() > 0) {
                            childSampleInfoModel1.setPersonnelName(((PersonInfoModel)refMemberId1.get(0)).getPersonName());
                            childSampleInfoModel1.setGender(((PersonInfoModel)refMemberId1.get(0)).getGender());
                        }

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
                                if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                                    sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                                }else {
                                    sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
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

                        sampleInfoModel.setChildSampleInfoModel(null);
                    }
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
                    if(TransferConstants.NATION_GENDER_FEMALE.equals(sampleInfoModel.getGender())){
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_FEMALE_HALF);
                    }else {
                        sampleInfoModel.setRelation(TransferConstants.RELATION_WITH_TARGET_MALE_HALF);
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
            childSampleInfoModel2.setUpdateDatetime(caseInfoModel.getUpdateDatetime());
            List refMemberId1 = sampleInfoService.getMemberList(caseInfoModel.getId(), "14");
            if(refMemberId1 != null && refMemberId1.size() > 0) {
                childSampleInfoModel1.setPersonnelName(((PersonInfoModel)refMemberId1.get(0)).getPersonName());
                childSampleInfoModel1.setGender(((PersonInfoModel)refMemberId1.get(0)).getGender());
            }

            sampleInfoModel.setChildSampleInfoModel(childSampleInfoModel2);
        }

        return sampleInfoModel;
    }*/


    /**
     * 构建基因信息list
     * @param queueSampleInfoModelList
     * @return
     */
    private List<SampleGeneInfoModel> getSampleGeneInfoModelList(CaseInfoModel caseInfoModel, List<SampleInfoModel> queueSampleInfoModelList){
        if(ListUtils.isEmptyList(queueSampleInfoModelList)){
            return null;
        }

        List<SampleGeneInfoModel> sampleGeneInfoModelList = new ArrayList<>();

        List<LimsMarkernames> limsMarkernamesList = sampleInfoService.selectStrMarkernames();

        List<StrDnaLibView> strDnaLibViewList = null;
        List<YstrDnaLibView> ystrDnaLibViewList = null;
        SampleGeneInfoModel sampleGeneInfoModel = null;
        for(SampleInfoModel sampleInfoModel : queueSampleInfoModelList){
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
        }

        return sampleGeneInfoModelList;
    }

    /**
     * 获取newlims检材基因型信息
     * @param caseInfoModel
     * @param queueId
     * @return
     */
    private List<SampleGeneInfoModel> getNewSampleGeneInfoModelList(CaseInfoModel caseInfoModel, String queueId) {
        List<SampleGeneInfoModel> sampleGeneInfoModelList = sampleInfoService.getNewSampleGeneListByQueueId(queueId);

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

                strMap.put(TransferSpecialMarkerUtil.transgerSpecialMarker(locusName), alleleValuesStr);
            }

            sampleGeneInfoModel.setAlleleCount(strMap.entrySet().size());

            String strDnaString = JSONObject.toJSONString(strMap);
            sampleGeneInfoModel.setGeneInfo(strDnaString);
        }

        sampleGeneInfoModel.setId(ystrDnaLibView.getId());
        sampleGeneInfoModel.setReagentKit(StringUtils.isNotBlank(ystrDnaLibView.getRealPanelName()) ? ystrDnaLibView.getRealPanelName() : ystrDnaLibView.getPanelName() );
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

    /**
     * 标记队列处理失败
     * @param transferCaseQueue
     * @param transferStatus
     * @param failedMsg
     */
    private void markQueueError(TransferCaseQueue transferCaseQueue, int transferStatus, String failedMsg){
        try {
            transferCaseQueue.setTransferStatus(transferStatus);
            transferCaseQueue.setFailedMsg(failedMsg);
            transferCaseQueue.setTransferDatetime(new Date());

            if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_OLD)){
                oldLimsCaseQueueService.updateStatus(transferCaseQueue);
            }else if(transferNationParamsConfig.getLimsVersion().equals(TransferConstants.LIMS_VERSION_SHUNYI)){
                newLimsCaseQueueService.updateStatus(transferCaseQueue);
            }
        }catch(Exception ex){
            logger.error("markQueueError标记案件上报队列处理失败时出现错误！", ex);
        }
    }

    /**
     * 转换新版lims亲缘关系
     * @param relativeType
     * @return
     */
    private String TransferRelativeType(String relativeType) {
        String transferRelativeType = null;
        if (TransferConstants.RELATION_TYPE_01.equals(relativeType)) {
            //父
            transferRelativeType = "1";
        }else if (TransferConstants.RELATION_TYPE_02.equals(relativeType)) {
            //母
            transferRelativeType = "2";
        }else if (TransferConstants.RELATION_TYPE_03.equals(relativeType)) {
            //配偶
            transferRelativeType = "5";
        }else if (TransferConstants.RELATION_TYPE_04.equals(relativeType)) {
            //配偶
            transferRelativeType = "5";
        }else if (TransferConstants.RELATION_TYPE_05.equals(relativeType)) {
            //子
            transferRelativeType = "3";
        }else if (TransferConstants.RELATION_TYPE_06.equals(relativeType)) {
            //女
            transferRelativeType = "4";
        }else {
            transferRelativeType = "6";
        }

        return transferRelativeType;
    }

}
