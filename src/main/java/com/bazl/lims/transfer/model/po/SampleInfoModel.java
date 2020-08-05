package com.bazl.lims.transfer.model.po;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2019/6/9.
 */
public class SampleInfoModel implements Serializable {
    private String id;
    private String sampleFlag;
    private String sampleLabNo;
    private String evidenceCode;
    private String evidenceName;
    private String sampleType;
    private String sampleDescription;
    private String sampleRemark;
    private String collectReason;
    private String sampleStatus;
    private String acceptOpinion;
    private String createUser;
    private Date createDatetime;
    private String createDatetimeStr;
    private String updateUser;
    private Date updateDatetime;
    private String selfObjectId;
    private String selfObjectType;
    private String relationObjectId;
    private String relation;
    private String physicalEvidenceId;
    private String personnelNo;
    private String personnelType;
    private String externalSysType;
    private String externalPersonnelNo;
    private String externalSysNo;
    private String personnelName;
    private String gender;
    private String race;
    private String idCardNo;
    private String certificateName;
    private String certificateNo;
    private String speciality;
    private Date birthDate;
    private String birthDateStr;
    private String nationality;
    private String alias;
    private String nativePlaceRegionalism;
    private String nativePlaceAddr;
    private String residenceRegionalism;
    private String residenceAddr;
    private String personRemark;
    private String roughAge;
    private String extrinsicSign;
    private String missingTime;
    private String missingPlace;
    private String prisonType;
    private String prisonNo;
    private String involvedCaseNo;
    private String caseName;
    private String caseProperty;
    private String mobilePhone;
    private String isMoveOut;
    private String deathFlag;
    private String belongFamilyType;
    private String familyName;
    private String familyRegionalism;
    private String familyPlace;
    private String familyNo;
    private String surname;
    private String age;
    private String homePhone;
    private String email;
    private String educationLevel;
    private String identity;
    private String occupation;
    private String labId;
    private String initServerNo;
    private String consignmentId;
    private String externalSysName;
    private String externalSubType;
    private String nativePlaceType;
    private String harmedManner;
    private String distrustfulWarranty;
    private String handledManner;
    private String fingerprintNo;
    private String bloodType;
    private String stature;
    private String bodilyForm;
    private String frontalPictureId;
    private String prisonName;
    private String relativeType;
    private String relationWithTarget;
    private String relationWithCase;
    private String evidenceNo;
    private String carrierCode;
    private String carrierType;
    private String carrierName;
    private String evidenceSceneType;
    private String evidenceCaseType;
    private String samplePackaging;
    private String collectAgencyCode;
    private String collectAgencyName;
    private String collectPos;
    private String collectDateStr;
    private Date collectDate;
    private String collectBy;
    private String preExamInfo;
    private String color;
    private String pattern;
    private String texture;
    private String shape;
    private String amount;
    private String evidenceSize;
    private String specialSign;
    private String sampleCurPlace;
    private String sampleHandleMode;
    private String preservationLimit;
    private String retakeMan;
    private String retakeDateStr;
    private Date retakeDate;
    private String examineUser1;
    private String examineUser2;
    private String examineUser3;
    private String examineMethod1;
    private String examineMethod2;
    private String examineMethod3;
    private String samplePictureId;
    private String storeFlag;
    private Date transferDate;
    private String deleteFlag;
    private String remark;
    private SampleInfoModel childSampleInfoModel;
    private String[] externalSysTypes;
    private String[] externalSysNos;

    private String caseId;
    private String caseType;
    private String instoreSampleType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleFlag() {
        return sampleFlag;
    }

    public void setSampleFlag(String sampleFlag) {
        this.sampleFlag = sampleFlag;
    }

    public String getSampleLabNo() {
        return sampleLabNo;
    }

    public void setSampleLabNo(String sampleLabNo) {
        this.sampleLabNo = sampleLabNo;
    }

    public String getEvidenceCode() {
        return evidenceCode;
    }

    public void setEvidenceCode(String evidenceCode) {
        this.evidenceCode = evidenceCode;
    }

    public String getEvidenceName() {
        return evidenceName;
    }

    public void setEvidenceName(String evidenceName) {
        this.evidenceName = evidenceName;
    }

    public String getSampleType() {
        return sampleType;
    }

    public void setSampleType(String sampleType) {
        this.sampleType = sampleType;
    }

    public String getSampleDescription() {
        return sampleDescription;
    }

    public void setSampleDescription(String sampleDescription) {
        this.sampleDescription = sampleDescription;
    }

    public String getSampleRemark() {
        return sampleRemark;
    }

    public void setSampleRemark(String sampleRemark) {
        this.sampleRemark = sampleRemark;
    }

    public String getCollectReason() {
        return collectReason;
    }

    public void setCollectReason(String collectReason) {
        this.collectReason = collectReason;
    }

    public String getSampleStatus() {
        return sampleStatus;
    }

    public void setSampleStatus(String sampleStatus) {
        this.sampleStatus = sampleStatus;
    }

    public String getAcceptOpinion() {
        return acceptOpinion;
    }

    public void setAcceptOpinion(String acceptOpinion) {
        this.acceptOpinion = acceptOpinion;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getCreateDatetimeStr() {
        return createDatetimeStr;
    }

    public void setCreateDatetimeStr(String createDatetimeStr) {
        this.createDatetimeStr = createDatetimeStr;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(Date updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getSelfObjectId() {
        return selfObjectId;
    }

    public void setSelfObjectId(String selfObjectId) {
        this.selfObjectId = selfObjectId;
    }

    public String getSelfObjectType() {
        return selfObjectType;
    }

    public void setSelfObjectType(String selfObjectType) {
        this.selfObjectType = selfObjectType;
    }

    public String getRelationObjectId() {
        return relationObjectId;
    }

    public void setRelationObjectId(String relationObjectId) {
        this.relationObjectId = relationObjectId;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getPhysicalEvidenceId() {
        return physicalEvidenceId;
    }

    public void setPhysicalEvidenceId(String physicalEvidenceId) {
        this.physicalEvidenceId = physicalEvidenceId;
    }

    public String getPersonnelNo() {
        return personnelNo;
    }

    public void setPersonnelNo(String personnelNo) {
        this.personnelNo = personnelNo;
    }

    public String getPersonnelType() {
        return personnelType;
    }

    public void setPersonnelType(String personnelType) {
        this.personnelType = personnelType;
    }

    public String getExternalSysType() {
        return externalSysType;
    }

    public void setExternalSysType(String externalSysType) {
        this.externalSysType = externalSysType;
    }

    public String getExternalPersonnelNo() {
        return externalPersonnelNo;
    }

    public void setExternalPersonnelNo(String externalPersonnelNo) {
        this.externalPersonnelNo = externalPersonnelNo;
    }

    public String getExternalSysNo() {
        return externalSysNo;
    }

    public void setExternalSysNo(String externalSysNo) {
        this.externalSysNo = externalSysNo;
    }

    public String getPersonnelName() {
        return personnelName;
    }

    public void setPersonnelName(String personnelName) {
        this.personnelName = personnelName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getCertificateNo() {
        return certificateNo;
    }

    public void setCertificateNo(String certificateNo) {
        this.certificateNo = certificateNo;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthDateStr() {
        return birthDateStr;
    }

    public void setBirthDateStr(String birthDateStr) {
        this.birthDateStr = birthDateStr;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getNativePlaceRegionalism() {
        return nativePlaceRegionalism;
    }

    public void setNativePlaceRegionalism(String nativePlaceRegionalism) {
        this.nativePlaceRegionalism = nativePlaceRegionalism;
    }

    public String getNativePlaceAddr() {
        return nativePlaceAddr;
    }

    public void setNativePlaceAddr(String nativePlaceAddr) {
        this.nativePlaceAddr = nativePlaceAddr;
    }

    public String getResidenceRegionalism() {
        return residenceRegionalism;
    }

    public void setResidenceRegionalism(String residenceRegionalism) {
        this.residenceRegionalism = residenceRegionalism;
    }

    public String getResidenceAddr() {
        return residenceAddr;
    }

    public void setResidenceAddr(String residenceAddr) {
        this.residenceAddr = residenceAddr;
    }

    public String getPersonRemark() {
        return personRemark;
    }

    public void setPersonRemark(String personRemark) {
        this.personRemark = personRemark;
    }

    public String getRoughAge() {
        return roughAge;
    }

    public void setRoughAge(String roughAge) {
        this.roughAge = roughAge;
    }

    public String getExtrinsicSign() {
        return extrinsicSign;
    }

    public void setExtrinsicSign(String extrinsicSign) {
        this.extrinsicSign = extrinsicSign;
    }

    public String getMissingTime() {
        return missingTime;
    }

    public void setMissingTime(String missingTime) {
        this.missingTime = missingTime;
    }

    public String getMissingPlace() {
        return missingPlace;
    }

    public void setMissingPlace(String missingPlace) {
        this.missingPlace = missingPlace;
    }

    public String getPrisonType() {
        return prisonType;
    }

    public void setPrisonType(String prisonType) {
        this.prisonType = prisonType;
    }

    public String getPrisonNo() {
        return prisonNo;
    }

    public void setPrisonNo(String prisonNo) {
        this.prisonNo = prisonNo;
    }

    public String getInvolvedCaseNo() {
        return involvedCaseNo;
    }

    public void setInvolvedCaseNo(String involvedCaseNo) {
        this.involvedCaseNo = involvedCaseNo;
    }

    public String getCaseName() {
        return caseName;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseProperty() {
        return caseProperty;
    }

    public void setCaseProperty(String caseProperty) {
        this.caseProperty = caseProperty;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getIsMoveOut() {
        return isMoveOut;
    }

    public void setIsMoveOut(String isMoveOut) {
        this.isMoveOut = isMoveOut;
    }

    public String getDeathFlag() {
        return deathFlag;
    }

    public void setDeathFlag(String deathFlag) {
        this.deathFlag = deathFlag;
    }

    public String getBelongFamilyType() {
        return belongFamilyType;
    }

    public void setBelongFamilyType(String belongFamilyType) {
        this.belongFamilyType = belongFamilyType;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFamilyRegionalism() {
        return familyRegionalism;
    }

    public void setFamilyRegionalism(String familyRegionalism) {
        this.familyRegionalism = familyRegionalism;
    }

    public String getFamilyPlace() {
        return familyPlace;
    }

    public void setFamilyPlace(String familyPlace) {
        this.familyPlace = familyPlace;
    }

    public String getFamilyNo() {
        return familyNo;
    }

    public void setFamilyNo(String familyNo) {
        this.familyNo = familyNo;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getLabId() {
        return labId;
    }

    public void setLabId(String labId) {
        this.labId = labId;
    }

    public String getInitServerNo() {
        return initServerNo;
    }

    public void setInitServerNo(String initServerNo) {
        this.initServerNo = initServerNo;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }

    public String getExternalSysName() {
        return externalSysName;
    }

    public void setExternalSysName(String externalSysName) {
        this.externalSysName = externalSysName;
    }

    public String getExternalSubType() {
        return externalSubType;
    }

    public void setExternalSubType(String externalSubType) {
        this.externalSubType = externalSubType;
    }

    public String getNativePlaceType() {
        return nativePlaceType;
    }

    public void setNativePlaceType(String nativePlaceType) {
        this.nativePlaceType = nativePlaceType;
    }

    public String getHarmedManner() {
        return harmedManner;
    }

    public void setHarmedManner(String harmedManner) {
        this.harmedManner = harmedManner;
    }

    public String getDistrustfulWarranty() {
        return distrustfulWarranty;
    }

    public void setDistrustfulWarranty(String distrustfulWarranty) {
        this.distrustfulWarranty = distrustfulWarranty;
    }

    public String getHandledManner() {
        return handledManner;
    }

    public void setHandledManner(String handledManner) {
        this.handledManner = handledManner;
    }

    public String getFingerprintNo() {
        return fingerprintNo;
    }

    public void setFingerprintNo(String fingerprintNo) {
        this.fingerprintNo = fingerprintNo;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getStature() {
        return stature;
    }

    public void setStature(String stature) {
        this.stature = stature;
    }

    public String getBodilyForm() {
        return bodilyForm;
    }

    public void setBodilyForm(String bodilyForm) {
        this.bodilyForm = bodilyForm;
    }

    public String getFrontalPictureId() {
        return frontalPictureId;
    }

    public void setFrontalPictureId(String frontalPictureId) {
        this.frontalPictureId = frontalPictureId;
    }

    public String getPrisonName() {
        return prisonName;
    }

    public void setPrisonName(String prisonName) {
        this.prisonName = prisonName;
    }

    public String getRelativeType() {
        return relativeType;
    }

    public void setRelativeType(String relativeType) {
        this.relativeType = relativeType;
    }

    public String getRelationWithTarget() {
        return relationWithTarget;
    }

    public void setRelationWithTarget(String relationWithTarget) {
        this.relationWithTarget = relationWithTarget;
    }

    public String getRelationWithCase() {
        return relationWithCase;
    }

    public void setRelationWithCase(String relationWithCase) {
        this.relationWithCase = relationWithCase;
    }

    public String getEvidenceNo() {
        return evidenceNo;
    }

    public void setEvidenceNo(String evidenceNo) {
        this.evidenceNo = evidenceNo;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getCarrierType() {
        return carrierType;
    }

    public void setCarrierType(String carrierType) {
        this.carrierType = carrierType;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getEvidenceSceneType() {
        return evidenceSceneType;
    }

    public void setEvidenceSceneType(String evidenceSceneType) {
        this.evidenceSceneType = evidenceSceneType;
    }

    public String getEvidenceCaseType() {
        return evidenceCaseType;
    }

    public void setEvidenceCaseType(String evidenceCaseType) {
        this.evidenceCaseType = evidenceCaseType;
    }

    public String getSamplePackaging() {
        return samplePackaging;
    }

    public void setSamplePackaging(String samplePackaging) {
        this.samplePackaging = samplePackaging;
    }

    public String getCollectAgencyCode() {
        return collectAgencyCode;
    }

    public void setCollectAgencyCode(String collectAgencyCode) {
        this.collectAgencyCode = collectAgencyCode;
    }

    public String getCollectAgencyName() {
        return collectAgencyName;
    }

    public void setCollectAgencyName(String collectAgencyName) {
        this.collectAgencyName = collectAgencyName;
    }

    public String getCollectPos() {
        return collectPos;
    }

    public void setCollectPos(String collectPos) {
        this.collectPos = collectPos;
    }

    public String getCollectDateStr() {
        return collectDateStr;
    }

    public void setCollectDateStr(String collectDateStr) {
        this.collectDateStr = collectDateStr;
    }

    public Date getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(Date collectDate) {
        this.collectDate = collectDate;
    }

    public String getCollectBy() {
        return collectBy;
    }

    public void setCollectBy(String collectBy) {
        this.collectBy = collectBy;
    }

    public String getPreExamInfo() {
        return preExamInfo;
    }

    public void setPreExamInfo(String preExamInfo) {
        this.preExamInfo = preExamInfo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEvidenceSize() {
        return evidenceSize;
    }

    public void setEvidenceSize(String evidenceSize) {
        this.evidenceSize = evidenceSize;
    }

    public String getSpecialSign() {
        return specialSign;
    }

    public void setSpecialSign(String specialSign) {
        this.specialSign = specialSign;
    }

    public String getSampleCurPlace() {
        return sampleCurPlace;
    }

    public void setSampleCurPlace(String sampleCurPlace) {
        this.sampleCurPlace = sampleCurPlace;
    }

    public String getSampleHandleMode() {
        return sampleHandleMode;
    }

    public void setSampleHandleMode(String sampleHandleMode) {
        this.sampleHandleMode = sampleHandleMode;
    }

    public String getPreservationLimit() {
        return preservationLimit;
    }

    public void setPreservationLimit(String preservationLimit) {
        this.preservationLimit = preservationLimit;
    }

    public String getRetakeMan() {
        return retakeMan;
    }

    public void setRetakeMan(String retakeMan) {
        this.retakeMan = retakeMan;
    }

    public String getRetakeDateStr() {
        return retakeDateStr;
    }

    public void setRetakeDateStr(String retakeDateStr) {
        this.retakeDateStr = retakeDateStr;
    }

    public Date getRetakeDate() {
        return retakeDate;
    }

    public void setRetakeDate(Date retakeDate) {
        this.retakeDate = retakeDate;
    }

    public String getExamineUser1() {
        return examineUser1;
    }

    public void setExamineUser1(String examineUser1) {
        this.examineUser1 = examineUser1;
    }

    public String getExamineUser2() {
        return examineUser2;
    }

    public void setExamineUser2(String examineUser2) {
        this.examineUser2 = examineUser2;
    }

    public String getExamineUser3() {
        return examineUser3;
    }

    public void setExamineUser3(String examineUser3) {
        this.examineUser3 = examineUser3;
    }

    public String getExamineMethod1() {
        return examineMethod1;
    }

    public void setExamineMethod1(String examineMethod1) {
        this.examineMethod1 = examineMethod1;
    }

    public String getExamineMethod2() {
        return examineMethod2;
    }

    public void setExamineMethod2(String examineMethod2) {
        this.examineMethod2 = examineMethod2;
    }

    public String getExamineMethod3() {
        return examineMethod3;
    }

    public void setExamineMethod3(String examineMethod3) {
        this.examineMethod3 = examineMethod3;
    }

    public String getSamplePictureId() {
        return samplePictureId;
    }

    public void setSamplePictureId(String samplePictureId) {
        this.samplePictureId = samplePictureId;
    }

    public String getStoreFlag() {
        return storeFlag;
    }

    public void setStoreFlag(String storeFlag) {
        this.storeFlag = storeFlag;
    }

    public Date getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(Date transferDate) {
        this.transferDate = transferDate;
    }

    public String getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(String deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SampleInfoModel getChildSampleInfoModel() {
        return childSampleInfoModel;
    }

    public void setChildSampleInfoModel(SampleInfoModel childSampleInfoModel) {
        this.childSampleInfoModel = childSampleInfoModel;
    }

    public String[] getExternalSysTypes() {
        return externalSysTypes;
    }

    public void setExternalSysTypes(String[] externalSysTypes) {
        this.externalSysTypes = externalSysTypes;
    }

    public String[] getExternalSysNos() {
        return externalSysNos;
    }

    public void setExternalSysNos(String[] externalSysNos) {
        this.externalSysNos = externalSysNos;
    }

    public String getCaseId() {
        return caseId;
    }

    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getInstoreSampleType() {
        return instoreSampleType;
    }

    public void setInstoreSampleType(String instoreSampleType) {
        this.instoreSampleType = instoreSampleType;
    }
}
