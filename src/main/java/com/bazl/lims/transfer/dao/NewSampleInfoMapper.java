package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface NewSampleInfoMapper {

    //查询dna库中所有样本的入库类型
    List<String> selectSampleTypeByCaseId(String caseId);

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

    List<PhysicalEvidenceModel> selectPhysicalEvidenceByQueueId(String queueId);

    /**
     * 根据队列id查询newlims物证信息
     * @param queueId
     * @return
     */
    public List<PhysicalEvidenceModel> selectNewPhysicalEvidenceByCaseQueueId(String queueId);


    List<SampleInfoModel> selectSampleInfoByQueueId(String queueId);

    /**
     * 根据案件id获取newlims样本列表
     * @param queueId
     * @return
     */
    public List<SampleInfoModel> selectNewSampleInfoListByCaseQueueId(String queueId);

    List<SampleInfoModel> selectSampleInfoByBarcode(String barcode);

    /**
     * 根据编号查询newlims检材信息
     * @param barcode
     * @return
     */
    public List<SampleInfoModel> selectNewSampleInfoByBarcode(String barcode);

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
     * @param instoreSampleInfo
     */
    public void updateSampleNoAndStateByBarcode(InstoreSampleInfo instoreSampleInfo);

    /**
     * 根据newlims检材编号更新状态和国家库编号
     * @param instoreSampleInfo
     */
    public void updateNewSampleNoAndStateByBarcode(InstoreSampleInfo instoreSampleInfo);

    /**
     * 查询Ystr信息
     * @param offset
     * @param rows
     * @return
     */
    public List<YstrDnaLibView> selectYstrDnaLibViewList(@Param("offset") int offset, @Param("rows") int rows);

    /**
     * 查询已经入库的刑事检材列表
     * @param offset
     * @param rows
     * @return
     */
    public List<SampleInfoModel> selectInstoreSampleList(@Param("offset") int offset, @Param("rows") int rows);

    /**
     * 根据案件id和人员类型查询人员信息
     * @param caseId
     * @param personType
     * @return
     */
    public List<PersonInfoModel> getMemberList(@Param("caseId") String caseId, @Param("personType") String personType);

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

    HttpSampleInfoModel getSampleinfo(String caseId);
}
