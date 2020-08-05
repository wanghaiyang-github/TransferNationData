package com.bazl.lims.transfer.service.impl;

import com.bazl.lims.transfer.dao.CaseInfoMapper;
import com.bazl.lims.transfer.model.po.CaseInfoModel;
import com.bazl.lims.transfer.model.po.ConsignmentModel;
import com.bazl.lims.transfer.service.CaseInfoService;
import com.bazl.lims.transfer.utils.ListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
@Service
public class CaseInfoServiceImpl extends BaseService implements CaseInfoService {

    @Autowired
    CaseInfoMapper caseInfoMapper;

    /**
     * 根据案件id获取案件信息
     * @param caseId
     * @return
     */
    @Override
    public CaseInfoModel selectCaseInfoModelByCaseId(String caseId) {
        try {
            return caseInfoMapper.selectCaseInfoModelByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectCaseInfoModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 根据案件id查询新lims案件信息
     * @param caseId
     * @return
     */
    @Override
    public CaseInfoModel selectNewCaseInfoModelByCaseId(String caseId) {
        try {
            List<CaseInfoModel> caseInfoModelList = caseInfoMapper.selectNewCaseInfoModelByCaseId(caseId);
            CaseInfoModel caseInfoModel = null;
            if (ListUtils.isNotEmptyList(caseInfoModelList)) {
                caseInfoModel = caseInfoModelList.get(0);
            }
            return caseInfoModel;
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectNewCaseInfoModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }


    /**
     * 根据案件id获取委托信息
     * @param caseId
     * @return
     */
    @Override
    public ConsignmentModel selectConsignmentModelByCaseId(String caseId) {
        try {
            return caseInfoMapper.selectConsignmentModelByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectConsignmentModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 根据newlims案件id获取委托信息
     * @param caseId
     * @return
     */
    @Override
    public ConsignmentModel selectNewConsignmentModelByCaseId(String caseId) {
        try {
            List<ConsignmentModel> consignmentModelList = caseInfoMapper.selectNewConsignmentModelByCaseId(caseId);
            ConsignmentModel consignmentModel = null;
            if (ListUtils.isNotEmptyList(consignmentModelList)) {
                consignmentModel = consignmentModelList.get(0);
            }
            return consignmentModel;
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectNewConsignmentModelByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 根据案件id获取本地dna库中的consignmentId
     * @param caseId
     * @return
     */
    @Override
    public String selectConsignmentIdByCaseId(String caseId) {
        try {
            return caseInfoMapper.selectConsignmentIdByCaseId(caseId);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.selectConsignmentIdByCaseId error, param is " + caseId + ".", ex);
            return null;
        }
    }

    /**
     * 根据案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    @Override
    public void updateLabNoByCaseId(String labNo, String caseId) {
        try {
            CaseInfoModel caseInfoModel = new CaseInfoModel();
            caseInfoModel.setCaseId(caseId);
            caseInfoModel.setCaseLabNo(labNo);
            caseInfoMapper.updateLabNoByCaseId(caseInfoModel);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.updateLabNoByCaseId error, param is " + caseId + ".", ex);
        }
    }

    /**
     * 根据newlims案件id更新此案件的国家库编号
     * @param labNo
     * @param caseId
     */
    @Override
    public void updateNewLabNoByCaseId(String labNo, String caseId) {
        try {
            CaseInfoModel caseInfoModel = new CaseInfoModel();
            caseInfoModel.setCaseId(caseId);
            caseInfoModel.setCaseLabNo(labNo);
            caseInfoMapper.updateNewLabNoByCaseId(caseInfoModel);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.updateNewLabNoByCaseId error, param is " + caseId + ".", ex);
        }
    }

    @Override
    public void updateLabNoByCaseId2(String labNo, String caseId) {
        try {
            CaseInfoModel caseInfoModel = new CaseInfoModel();
            caseInfoModel.setCaseId(caseId);
            caseInfoModel.setCaseLabNo(labNo);
            caseInfoMapper.updateLabNoByCaseId2(caseInfoModel);
        }catch (Exception ex) {
            logger.error("invoke CaseInfoService.updateLabNoByCaseId error, param is " + caseId + ".", ex);
        }
    }
}
