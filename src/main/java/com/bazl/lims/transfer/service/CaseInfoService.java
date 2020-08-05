package com.bazl.lims.transfer.service;

import com.bazl.lims.transfer.model.po.CaseInfoModel;
import com.bazl.lims.transfer.model.po.ConsignmentModel;

/**
 * Created by Administrator on 2019/6/9.
 */
public interface CaseInfoService {

    /**
     * 根据案件id获取案件信息
     * @param caseId
     * @return
     */
    public CaseInfoModel selectCaseInfoModelByCaseId(String caseId);

    /**
     * 根据案件id查询新lims案件信息
     * @param caseId
     * @return
     */
    public CaseInfoModel selectNewCaseInfoModelByCaseId(String caseId);

    /**
     * 根据案件id获取委托信息
     * @param caseId
     * @return
     */
    public ConsignmentModel selectConsignmentModelByCaseId(String caseId);

    /**
     * 根据newlims案件id获取委托信息
     * @param caseId
     * @return
     */
    public ConsignmentModel selectNewConsignmentModelByCaseId(String caseId);

    /**
     * 根据案件id获取本地dna库中的consignmentId
     * @param caseId
     * @return
     */
    public String selectConsignmentIdByCaseId(String caseId);

    /**
     * 根据案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    public void updateLabNoByCaseId(String labNo, String caseId);

    /**
     * 根据newlims案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    public void updateNewLabNoByCaseId(String labNo, String caseId);

    void updateLabNoByCaseId2(String nationCaseNo, String caseId);
}
