package com.bazl.lims.transfer.model.bo;

import com.bazl.lims.transfer.model.po.*;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2019/6/9.
 */
public class SubmitNationDataModel implements Serializable {

    private CaseInfoModel caseInfoModel;

    private ConsignmentModel consignmentModel;

    private SampleInfoModel sampleInfoModel;

    private List<PhysicalEvidenceModel> physicalEvidenceModelList;

    private List<SampleInfoModel> sampleInfoModelList;

    private List<PersonInfoModel> personInfoModelList;

    private List<SampleGeneInfoModel> sampleGeneInfoModelList;


    public CaseInfoModel getCaseInfoModel() {
        return caseInfoModel;
    }

    public void setCaseInfoModel(CaseInfoModel caseInfoModel) {
        this.caseInfoModel = caseInfoModel;
    }

    public ConsignmentModel getConsignmentModel() {
        return consignmentModel;
    }

    public void setConsignmentModel(ConsignmentModel consignmentModel) {
        this.consignmentModel = consignmentModel;
    }

    public List<PhysicalEvidenceModel> getPhysicalEvidenceModelList() {
        return physicalEvidenceModelList;
    }

    public void setPhysicalEvidenceModelList(List<PhysicalEvidenceModel> physicalEvidenceModelList) {
        this.physicalEvidenceModelList = physicalEvidenceModelList;
    }

    public List<SampleInfoModel> getSampleInfoModelList() {
        return sampleInfoModelList;
    }

    public void setSampleInfoModelList(List<SampleInfoModel> sampleInfoModelList) {
        this.sampleInfoModelList = sampleInfoModelList;
    }

    public List<PersonInfoModel> getPersonInfoModelList() {
        return personInfoModelList;
    }

    public void setPersonInfoModelList(List<PersonInfoModel> personInfoModelList) {
        this.personInfoModelList = personInfoModelList;
    }

    public List<SampleGeneInfoModel> getSampleGeneInfoModelList() {
        return sampleGeneInfoModelList;
    }

    public void setSampleGeneInfoModelList(List<SampleGeneInfoModel> sampleGeneInfoModelList) {
        this.sampleGeneInfoModelList = sampleGeneInfoModelList;
    }

    public SampleInfoModel getSampleInfoModel() {
        return sampleInfoModel;
    }

    public void setSampleInfoModel(SampleInfoModel sampleInfoModel) {
        this.sampleInfoModel = sampleInfoModel;
    }
}
