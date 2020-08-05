package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.dao.NewSampleInfoMapper;
import com.bazl.lims.transfer.dao.SampleInfoMapper;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.service.SampleInfoService;
import com.bazl.lims.transfer.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class SampleInfoServiceImpl extends BaseService implements SampleInfoService {

    @Autowired
    SampleInfoMapper sampleInfoMapper;
    @Autowired
    NewSampleInfoMapper newSampleInfoMapper;

    /**
     * 查询dna库中所有样本的入库类型
     */
    @Override
    public List<String> selectSampleTypeByCaseId(String caseId) {
        try {
            return sampleInfoMapper.selectSampleTypeByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleTypeByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 查询newlims中所有样本入库类型
     * @param caseId
     * @return
     */
    @Override
    public List<String> selectNewSampleTypeByCaseId(String caseId) {
        try {
            return sampleInfoMapper.selectNewSampleTypeByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectNewSampleTypeByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 查询newlims中所有建库样本入库类型
     * @param sampleBarcode
     * @return
     */
    @Override
    public List<String> selectBySampleBarcode(String sampleBarcode) {
        try {
            return newSampleInfoMapper.selectBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    /***
     * 根据案件队列ID获取物证列表
     * @param queueId
     * @return
     */
    @Override
    public List<PhysicalEvidenceModel> selectPhysicalEvidenceByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectPhysicalEvidenceByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectPhysicalEvidenceByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }

    /**
     * 根据队列id查询newlims物证信息
     * @param queueId
     * @return
     */
    @Override
    public List<PhysicalEvidenceModel> selectNewPhysicalEvidenceByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectNewPhysicalEvidenceByCaseQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectNewPhysicalEvidenceByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }

    /***
     * 根据案件队列ID获取样本列表
     * @param queueId
     * @return
     */
    @Override
    public List<SampleInfoModel> selectSampleInfoListByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectSampleInfoByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleInfoListByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }

    /**
     * 根据案件id获取newlims样本列表
     * @param queueId
     * @return
     */
    @Override
    public List<SampleInfoModel> selectNewSampleInfoListByCaseQueueId(String queueId) {
        try {
            return sampleInfoMapper.selectNewSampleInfoListByCaseQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectNewSampleInfoListByCaseQueueId error, param is " + queueId + ".", ex);
            return null;
        }
    }

    @Override
    public SampleInfoModel selectSampleInfoByBarcode(String barcode) {
        try {
            List<SampleInfoModel> sampleInfoModelList = sampleInfoMapper.selectSampleInfoByBarcode(barcode);
            SampleInfoModel sampleInfoModel = null;
            if (ListUtils.isNotEmptyList(sampleInfoModelList)) {
                sampleInfoModel = sampleInfoModelList.get(0);
            }
            return sampleInfoModel;
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSampleInfoByBarcode error, param is " + barcode + ".", ex);
            return null;
        }
    }

    /**
     * 根据编号查询newlims检材信息
     * @param barcode
     * @return
     */
    @Override
    public SampleInfoModel selectNewSampleInfoByBarcode(String barcode) {
        try {
            List<SampleInfoModel> sampleInfoModelList = newSampleInfoMapper.selectNewSampleInfoByBarcode(barcode);
            SampleInfoModel sampleInfoModel = null;
            if (ListUtils.isNotEmptyList(sampleInfoModelList)) {
                sampleInfoModel = sampleInfoModelList.get(0);
            }
            return sampleInfoModel;
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectNewSampleInfoByBarcode error, param is " + barcode + ".", ex);
            return null;
        }
    }

    @Override
    public InstoreSampleInfo selectInstoreSampleInfoBySampleBarcode(String sampleBarcode){
        try {
            return sampleInfoMapper.selectInstoreSampleInfoBySampleBarcode(sampleBarcode.toUpperCase());
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectInstoreSampleInfoBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public String selectSingleRelation(String sampleId) {
        try {
            return sampleInfoMapper.selectSingleRelation(sampleId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectSingleRelation error, param is " + sampleId + ".", ex);
            return null;
        }
    }

    @Override
    public List<RelativeSampleInfo> selectRelativeSampleBySampleBarcode(String sampleBarcode){
        try {
            return sampleInfoMapper.selectRelativeSampleBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectRelativeSampleBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    /**
     * 根据检材编号获取newlims的亲缘检材信息
     * @param sampleBarcode
     * @return
     */
    @Override
    public List<RelativeSampleInfo> selectNewRelativeSampleBySampleBarcode(String sampleBarcode) {
        try {
            return sampleInfoMapper.selectNewRelativeSampleBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectNewRelativeSampleBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<StrDnaLibView> selectStrDnaLibViewBySampleBarcode(String sampleBarcode) {
        try {
            return sampleInfoMapper.selectStrDnaLibViewBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectStrDnaLibViewBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<YstrDnaLibView> selectYstrDnaLibViewBySampleBarcode(String sampleBarcode) {
        try {
            return sampleInfoMapper.selectYstrDnaLibViewBySampleBarcode(sampleBarcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectYstrDnaLibViewBySampleBarcode error, param is " + sampleBarcode + ".", ex);
            return null;
        }
    }

    @Override
    public List<LimsMarkernames> selectStrMarkernames() {
        try {
            return sampleInfoMapper.selectStrMarkernames();
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectStrMarkernames error.", ex);
            return null;
        }
    }

    @Override
    public RelativeDefModel selectRelationByBarcode(String barcode) {
        try {
            return sampleInfoMapper.selectRelationByBarcode(barcode);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectRelationByBarcode error.", ex);
            return null;
        }
    }

    @Override
    public void updateSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(barcode);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(state);
            sampleInfoMapper.updateSampleNoAndStateByBarcode(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateSampleNoAndStateByBarcode error.", ex);
        }
    }

    /**
     * 根据newlims检材编号更新状态和国家库编号
     * @param nationSampleNo
     * @param state
     * @param barcode
     */
    @Override
    public void updateNewSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(barcode);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(state);
            sampleInfoMapper.updateNewSampleNoAndStateByBarcode(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateNewSampleNoAndStateByBarcode error.", ex);
        }
    }

    @Override
    public List<YstrDnaLibView> selectYstrDnaLibViewList(int startCount, int endCount) {
        try {
            return sampleInfoMapper.selectYstrDnaLibViewList(startCount, endCount);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectYstrDnaLibViewList error.", ex);
            return null;
        }
    }

    @Override
    public List<SampleInfoModel> selectInstoreSampleList(int startCount, int endCount) {
        try {
            return sampleInfoMapper.selectInstoreSampleList(startCount, endCount);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.selectInstoreSampleList error.", ex);
            return null;
        }
    }

    @Override
    public List<PersonInfoModel> getMemberList (String caseId, String personType) {
        try {
            return sampleInfoMapper.getMemberList(caseId, personType);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getMemberList error.", ex);
            return null;
        }
    }

    /**
     * 根据队列id查询newlims基因型信息
     * @param queueId
     * @return
     */
    @Override
    public List<SampleGeneInfoModel> getNewSampleGeneListByQueueId(String queueId) {
        try {
            return sampleInfoMapper.getNewSampleGeneListByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getNewSampleGeneListByQueueId error.", ex);
            return null;
        }
    }

    /**
     * 根据队列id查询newlims基因型信息
     * @param queueId
     * @return
     */
    @Override
    public List<SampleGeneInfoModel> getNewSampleGeneInfoListByQueueId(String queueId) {
        try {
            return newSampleInfoMapper.getNewSampleGeneInfoListByQueueId(queueId);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.getNewSampleGeneInfoListByQueueId error.", ex);
            return null;
        }
    }

    @Override
    public HttpSampleInfoModel getSampleinfo(String caseId) {
        return sampleInfoMapper.getSampleinfo(caseId);
    }

    @Override
    public void updateSampleNoAndStateByBarcode2(String nationSampleNo, int state, String barcode) {
        try {
            InstoreSampleInfo instoreSampleInfo = new InstoreSampleInfo();
            instoreSampleInfo.setSampleid(barcode);
            instoreSampleInfo.setSampleNo(nationSampleNo);
            instoreSampleInfo.setState(state);
            sampleInfoMapper.updateSampleNoAndStateByBarcode2(instoreSampleInfo);
        }catch (Exception ex) {
            logger.error("invoke SampleInfoService.updateSampleNoAndStateByBarcode2 error.", ex);
        }
    }
}
