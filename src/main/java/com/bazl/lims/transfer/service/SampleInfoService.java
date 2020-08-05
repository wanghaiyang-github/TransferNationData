package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.*;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface SampleInfoService {

    /**
     * 查询dna库中所有样本的入库类型
     */
     public List<String> selectSampleTypeByCaseId(String caseId);

    /**
     * 查询newlims中所有样本入库类型
     * @param caseId
     * @return
     */
    public List<String> selectNewSampleTypeByCaseId(String caseId);

    /**
     * 查询newlims中所有建库样本入库类型
     * @param sampleBarcode
     * @return
     */
    public List<String> selectBySampleBarcode(String sampleBarcode);


    /***
     * 根据案件队列ID获取物证列表
     * @param queueId
     * @return
     */
    public List<PhysicalEvidenceModel> selectPhysicalEvidenceByCaseQueueId(String queueId);

    /**
     * 根据队列id查询newlims物证信息
     * @param queueId
     * @return
     */
    public List<PhysicalEvidenceModel> selectNewPhysicalEvidenceByCaseQueueId(String queueId);

    /***
     * 根据案件队列ID获取样本列表
     * @param queueId
     * @return
     */
    public List<SampleInfoModel> selectSampleInfoListByCaseQueueId(String queueId);


    /**
     * 根据案件队列ID获取newlims样本列表
     * @param queueId
     * @return
     */
    public List<SampleInfoModel> selectNewSampleInfoListByCaseQueueId(String queueId);

    SampleInfoModel selectSampleInfoByBarcode(String barcode);

    /**
     * 根据编号查询newlims检材信息
     * @param barcode
     * @return
     */
    public SampleInfoModel selectNewSampleInfoByBarcode(String barcode);

    InstoreSampleInfo selectInstoreSampleInfoBySampleBarcode(String sampleBarcode);


    String selectSingleRelation(String sampleId);

    List<RelativeSampleInfo> selectRelativeSampleBySampleBarcode(String sampleBarcode);

    /**
     * 根据检材编号获取newlims的亲缘检材信息
     * @param sampleBarcode
     * @return
     */
    public List<RelativeSampleInfo> selectNewRelativeSampleBySampleBarcode(String sampleBarcode);

    List<StrDnaLibView> selectStrDnaLibViewBySampleBarcode(String sampleBarcode);

    List<YstrDnaLibView> selectYstrDnaLibViewBySampleBarcode(String sampleBarcode);

    List<LimsMarkernames> selectStrMarkernames();

    RelativeDefModel selectRelationByBarcode(String barcode);

     /**
     * 根据检材编号更新状态和国家库编号
     * @param nationSampleNo
     * @param state
     * @param barcode
     */
    public void updateSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode);

     /**
     * 根据newlims检材编号更新状态和国家库编号
     * @param nationSampleNo
     * @param state
     * @param barcode
     */
    public void updateNewSampleNoAndStateByBarcode(String nationSampleNo, int state, String barcode);

    /**
     * 查询Ystr信息
     * @param startCount
     * @param endCount
     * @return
     */
    public List<YstrDnaLibView> selectYstrDnaLibViewList(int startCount, int endCount);

    /**
     * 查询已经入库的刑事检材列表
     * @param startCount
     * @param endCount
     * @return
     */
    public List<SampleInfoModel> selectInstoreSampleList(int startCount, int endCount);

    /**
     * 根据案件id和人员类型查询人员信息
     * @param caseId
     * @param personType
     * @return
     */
    public List<PersonInfoModel> getMemberList (String caseId, String personType);

    /**
     * 根据队列id查询newlims基因型信息
     * @param queueId
     * @return
     */
    public List<SampleGeneInfoModel> getNewSampleGeneListByQueueId(String queueId);

    /**
     * 根据队列id查询newlims基因型信息
     * @param queueId
     * @return
     */
    public List<SampleGeneInfoModel> getNewSampleGeneInfoListByQueueId(String queueId);

    public HttpSampleInfoModel getSampleinfo(String caseId);

    void updateSampleNoAndStateByBarcode2(String nationSampleNo, int nationSampleStateTrue, String sampleLabNo);
}
