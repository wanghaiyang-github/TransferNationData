package com.bazl.lims.transfer.dao;

import com.bazl.lims.transfer.model.po.CaseInfoModel;
import com.bazl.lims.transfer.model.po.ConsignmentModel;
import com.bazl.lims.transfer.model.po.TransferCaseQueue;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Repository
public interface CaseInfoMapper {

    CaseInfoModel selectCaseInfoModelByCaseId(String caseId);

    /**
     * 根据案件id查询新lims案件信息
     * @param caseId
     * @return
     */
    public List<CaseInfoModel> selectNewCaseInfoModelByCaseId(String caseId);

    ConsignmentModel selectConsignmentModelByCaseId(String caseId);


    /**
     * 根据newlims案件id获取委托信息
     * @param caseId
     * @return
     */
    public List<ConsignmentModel> selectNewConsignmentModelByCaseId(String caseId);

    String selectConsignmentIdByCaseId(String caseId);

    /**
     * 根据案件id更新此案件的国家库编号
     * @param caseInfoModel
     */
    public void updateLabNoByCaseId(CaseInfoModel caseInfoModel);

    /**
     * 根据newlims案件id更新此案件的国家库编号
     * @param caseInfoModel
     */
    public void updateNewLabNoByCaseId(CaseInfoModel caseInfoModel);

    void updateLabNoByCaseId2(CaseInfoModel caseInfoModel);
}
