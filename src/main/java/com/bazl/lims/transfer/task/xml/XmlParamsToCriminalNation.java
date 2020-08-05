package com.bazl.lims.transfer.task.xml;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.ExternalSysInfo;
import com.bazl.lims.transfer.model.po.*;
import com.bazl.lims.transfer.utils.DateUtils;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2019/6/10.
 */
public class XmlParamsToCriminalNation {

    public String getXmlBasicInfo() {
        StringBuffer str = new StringBuffer();
        str.append("<fileInfo>");
        str.append("<version>").append("1.0").append("</version>");
        str.append("<system>").append("DNA Lims").append("</system>");
        str.append("</fileInfo>");
        return str.toString();
    }

    public String getXmlConsignment(ConsignmentModel consignment) {
        StringBuffer str = new StringBuffer();
        str.append("<consignment>");
        if(consignment != null) {
            str.append("<consignmentId>").append(consignment.getId() == null?"":consignment.getId()).append("</consignmentId>");
            str.append("<consignmentType>").append(consignment.getCategory() == null?"无":consignment.getCategory()).append("</consignmentType>");
            str.append("<consignOrgRegionalism>").append(consignment.getAgencyRegionalism() == null?"无":consignment.getAgencyRegionalism()).append("</consignOrgRegionalism>");
            str.append("<consignOrgName>").append(consignment.getAgencyName() == null?"无":consignment.getAgencyName()).append("</consignOrgName>");
            str.append("<consignerName>").append(consignment.getConsignerName() == null?"无":consignment.getConsignerName()).append("</consignerName>");
            str.append("<consignerPhone>").append(consignment.getConsignerPhone() == null?"无":consignment.getConsignerPhone()).append("</consignerPhone>");
            str.append("<consignerCertificateType>").append(consignment.getConsignerCertificateType() == null?"5":consignment.getConsignerCertificateType()).append("</consignerCertificateType>");
            str.append("<consignerCertificateNo>").append(consignment.getConsignerCertificateNo() == null?"无":consignment.getConsignerCertificateNo()).append("</consignerCertificateNo>");
            str.append("<consignerName2>").append(consignment.getConsignerName2() == null?"无":consignment.getConsignerName2()).append("</consignerName2>");
            str.append("<consignerPhone2>").append(consignment.getConsignerPhone2() == null?"无":consignment.getConsignerPhone2()).append("</consignerPhone2>");
            str.append("<consignerCertificateType2>").append(consignment.getConsignerCertificateType2() == null?"5":consignment.getConsignerCertificateType2()).append("</consignerCertificateType2>");
            str.append("<consignerCertificateNo2>").append(consignment.getConsignerCertificateNo2() == null?"无":consignment.getConsignerCertificateNo2()).append("</consignerCertificateNo2>");
            str.append("<identifyRequest>").append(consignment.getIdentifiyRequest() == null?"无":consignment.getIdentifiyRequest()).append("</identifyRequest>");

            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

            String consignDatetime = null;
            if(consignment.getConsignDate() != null){
                consignDatetime = DateUtils.dateToString(consignment.getConsignDate(), "yyyyMMddHHmmss");
            }else{
                consignDatetime = currentDatetime;
            }
            str.append("<consignDatetime>").append(consignDatetime).append("</consignDatetime>");

            str.append("<acceptNo>").append(consignment.getAcceptNo() == null?"":consignment.getAcceptNo()).append("</acceptNo>");
            str.append("<acceptDatetime>").append(consignment.getAcceptDatetime() == null ? currentDatetime : DateUtils.dateToString(consignment.getAcceptDatetime(),"yyyyMMddHHmmss")).append("</acceptDatetime>");
            str.append("<acceptorName>").append(consignment.getReceptionManName() == null ? "无" : consignment.getReceptionManName()).append("</acceptorName>");
            str.append("<isAppend>").append(consignment.getIsAppend() == null ? "" : consignment.getIsAppend()).append("</isAppend>");
            str.append("<createUser>").append(consignment.getCreateUser() == null ? "无" : consignment.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(consignment.getCreateDatetime() == null ? consignDatetime : DateUtils.dateToString(consignment.getCreateDatetime(), "yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(consignment.getUpdateUser() == null ? "无" : consignment.getUpdateUser()).append("</updateUser>");
            str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
            str.append("<consignmentNo>").append(consignment.getConsignmentNo() == null?"":consignment.getConsignmentNo()).append("</consignmentNo>");
            str.append("<consignOrgZipCode>").append(consignment.getConsignOrgZipCode() == null?"":consignment.getConsignOrgZipCode()).append("</consignOrgZipCode>");
            str.append("<consignOrgAddress>").append(consignment.getConsignOrgAddress() == null?"":consignment.getConsignOrgAddress()).append("</consignOrgAddress>");
            str.append("<acceptRegionalism>").append(consignment.getAcceptRegionalism() == null?"":consignment.getAcceptRegionalism()).append("</acceptRegionalism>");
            str.append("<consignOrgPhone>").append(consignment.getAgencyPhone() == null?"":consignment.getAgencyPhone()).append("</consignOrgPhone>");
            str.append("<consignOrgFaxNo>").append(consignment.getConsignOrgFaxNo() == null?"":consignment.getConsignOrgFaxNo()).append("</consignOrgFaxNo>");
            str.append("<consignerDuty>").append(consignment.getConsignerDuty() == null?"":consignment.getConsignerDuty()).append("</consignerDuty>");
            str.append("<consignerDuty2>").append(consignment.getConsignerDuty2() == null?"":consignment.getConsignerDuty2()).append("</consignerDuty2>");
            str.append("<consignBrief>").append(consignment.getConsignBrief() == null?"":consignment.getConsignBrief()).append("</consignBrief>");
            str.append("<originalIdentyInfo>").append(consignment.getPreIdentifiyInfo() == null?"":consignment.getPreIdentifiyInfo()).append("</originalIdentyInfo>");
            str.append("<reIdentyReason>").append(consignment.getReIdentyReason() == null?"":consignment.getReIdentyReason()).append("</reIdentyReason>");
            str.append("<remark>").append(consignment.getRemark() == null?"":consignment.getRemark()).append("</remark>");
            str.append("<acceptOpinion>").append(consignment.getAcceptOpinion() == null?"":consignment.getAcceptOpinion()).append("</acceptOpinion>");
            str.append("<acceptOrgName>").append(consignment.getAcceptOrgName() == null?"":consignment.getAcceptOrgName()).append("</acceptOrgName>");
            str.append("<acceptOrgPhone>").append(consignment.getReceptionTel() == null?"":consignment.getReceptionTel()).append("</acceptOrgPhone>");
            str.append("<identifyResult>").append(consignment.getIdentifyResult() == null?"无":consignment.getIdentifyResult()).append("</identifyResult>");
        }

        str.append("</consignment>");
        return str.toString();
    }

    public String getXmlCaseInfoModel(CaseInfoModel caseInfoModel) {
        StringBuffer str = new StringBuffer();
        str.append("<caseInfo>");
        if(caseInfoModel != null) {
            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

            str.append("<caseId>").append(caseInfoModel.getId() == null ? "" : caseInfoModel.getId()).append("</caseId>");
            str.append("<caseName>").append(" <![CDATA[ ").append(caseInfoModel.getCaseName() == null?"":caseInfoModel.getCaseName()).append("]]>").append("</caseName>");
            str.append("<caseType>").append(caseInfoModel.getCaseType() == null ? "" : caseInfoModel.getCaseType()).append("</caseType>");
            str.append("<caseProperty>").append(caseInfoModel.getCaseProperty() == null ? "00" : caseInfoModel.getCaseProperty()).append("</caseProperty>");
            str.append("<occurrenceDatetime>").append(caseInfoModel.getOccurrenceDate() == null ? DateUtils.dateToString(caseInfoModel.getCreateDatetime(),"yyyyMMddHHmmss") : DateUtils.dateToString(caseInfoModel.getOccurrenceDate(),"yyyyMMddHHmmss")).append("</occurrenceDatetime>");
            str.append("<sceneRegionalism>").append(caseInfoModel.getSceneRegionalism() == null ? "无" : caseInfoModel.getSceneRegionalism()).append("</sceneRegionalism>");
            str.append("<scenePlace>").append(caseInfoModel.getScenePlace() == null ? "无" : caseInfoModel.getScenePlace()).append("</scenePlace>");
            str.append("<caseBrief>").append(" <![CDATA[ ").append(caseInfoModel.getCaseSummary() == null?"无":caseInfoModel.getCaseSummary()).append("]]>").append("</caseBrief>");
            str.append("<createUser>").append(caseInfoModel.getCreateUser() == null ? "无" : caseInfoModel.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(caseInfoModel.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(caseInfoModel.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(caseInfoModel.getAcceptorName() == null ? "无" : caseInfoModel.getAcceptorName()).append("</updateUser>");
            str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
            if(ListUtils.isNotEmptyList(caseInfoModel.getExternalSysInfoList())) {
                str.append("<externalInfoList>");
                List<ExternalSysInfo> externalSysInfoList  = caseInfoModel.getExternalSysInfoList();
                for(int i = 0; i < externalSysInfoList.size(); i++) {
                    str.append("<externalInfo>");
                    str.append("<externalSysType>").append(externalSysInfoList.get(i).getExternalSysType()).append("</externalSysType>");
                    str.append("<externalSysNo>").append(externalSysInfoList.get(i).getExternalSysNo()).append("</externalSysNo>");
                    str.append("</externalInfo>");
                }

                str.append("</externalInfoList>");
            }

            str.append("<caseLevel>").append(caseInfoModel.getCaseLevel() == null?"":caseInfoModel.getCaseLevel()).append("</caseLevel>");
            str.append("<caseStatus>").append(caseInfoModel.getCaseStatus() == null?"":caseInfoModel.getCaseStatus()).append("</caseStatus>");
            str.append("<category>").append(caseInfoModel.getCategory() == null?"无":caseInfoModel.getCategory()).append("</category>");
            str.append("<sceneAreaType>").append(caseInfoModel.getSceneAreaType() == null?"":caseInfoModel.getSceneAreaType()).append("</sceneAreaType>");
            str.append("<estimatedDeath>").append(caseInfoModel.getEstimatedDeath() == null?"":caseInfoModel.getEstimatedDeath()).append("</estimatedDeath>");
            str.append("<estimatedWounded>").append(caseInfoModel.getEstimatedWounded() == null?"":caseInfoModel.getEstimatedWounded()).append("</estimatedWounded>");
            str.append("<xckyCallbackURL>").append(caseInfoModel.getXckyCallbackURL() == null?"":caseInfoModel.getXckyCallbackURL()).append("</xckyCallbackURL>");
            str.append("<xckyConsignNo>").append(caseInfoModel.getXckyConsignNo() == null?"":caseInfoModel.getXckyConsignNo()).append("</xckyConsignNo>");
        }

        str.append("</caseInfo>");
        return str.toString();
    }

    public String getXmlPersonInfoList(List<SampleInfoModel> personInfoList) {
        StringBuffer str = new StringBuffer();
        str.append("<personInfoList>");
        String isExistPersonId = "";
        if(personInfoList != null && personInfoList.size() > 0) {

            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

            for (SampleInfoModel personInfo : personInfoList) {
                if(personInfo.getPersonnelNo() != null && !personInfo.getPersonnelNo().equals("-1")) {
                    str.append("<personInfo>");
                    str.append("<personId>").append(personInfo.getPersonnelNo() == null?"":personInfo.getPersonnelNo() + personInfo.getSampleLabNo()).append("</personId>");
                    str.append("<category>").append(personInfo.getPersonnelType() == null?"":personInfo.getPersonnelType()).append("</category>");
                    str.append("<personName>").append(" <![CDATA[ ").append(personInfo.getPersonnelName() == null?"":personInfo.getPersonnelName()).append("]]>").append("</personName>");
                    if (StringUtils.isNotBlank(personInfo.getGender())) {
                        if ("3".equals(personInfo.getGender())) {
                            personInfo.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                        }
                    }
                    str.append("<gender>").append(StringUtils.isBlank(personInfo.getGender()) ? TransferConstants.NATION_GENDER_UNKNOWN : personInfo.getGender()).append("</gender>");
                    str.append("<idCardNo>").append(personInfo.getIdCardNo() == null?"无":personInfo.getIdCardNo()).append("</idCardNo>");
                    str.append("<certificateType>").append(personInfo.getCertificateName() == null?"":personInfo.getCertificateName()).append("</certificateType>");
                    str.append("<certificateNo>").append(personInfo.getCertificateNo() == null?"":personInfo.getCertificateNo()).append("</certificateNo>");
                    str.append("<prisonNo>").append(personInfo.getPrisonNo() == null?"":personInfo.getPrisonNo()).append("</prisonNo>");
                    str.append("<createUser>").append(personInfo.getCreateUser() == null?"":personInfo.getCreateUser()).append("</createUser>");
                    str.append("<createDatetime>").append(personInfo.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(personInfo.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
                    str.append("<updateUser>").append(personInfo.getUpdateUser() == null?"无":personInfo.getUpdateUser()).append("</updateUser>");
                    str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
                    if(personInfo.getExternalSysType() != null && !"".equals(personInfo.getExternalSysType()) && personInfo.getExternalPersonnelNo() != null && !"".equals(personInfo.getExternalPersonnelNo()) && !"无".equals(personInfo.getExternalPersonnelNo())) {
                        str.append("<externalInfoList>");
                        str.append("<externalInfo>");
                        str.append("<externalSysType>").append(personInfo.getExternalSysType()).append("</externalSysType>");
                        str.append("<externalSysNo>").append(personInfo.getExternalPersonnelNo()).append("</externalSysNo>");
                        str.append("</externalInfo>");
                        str.append("</externalInfoList>");
                    }

                    str.append("<race>").append(personInfo.getRace() == null?"":personInfo.getRace()).append("</race>");
                    str.append("<personLabel>").append(personInfo.getSpeciality() == null?"":personInfo.getSpeciality()).append("</personLabel>");
                    str.append("<birthDatetime>").append(personInfo.getBirthDate() == null?"":DateUtils.dateToString(personInfo.getBirthDate(),"yyyyMMddHHmmss")).append("</birthDatetime>");
                    str.append("<nationality>").append(personInfo.getNationality() == null?"":personInfo.getNationality()).append("</nationality>");
                    str.append("<alias>").append(personInfo.getAlias() == null?"":personInfo.getAlias()).append("</alias>");
                    str.append("<nativePlaceRegionalism>").append(personInfo.getNativePlaceRegionalism() == null?"":personInfo.getNativePlaceRegionalism()).append("</nativePlaceRegionalism>");
                    str.append("<nativePlaceAddr>").append(personInfo.getNativePlaceAddr() == null?"":personInfo.getNativePlaceAddr()).append("</nativePlaceAddr>");
                    str.append("<residenceRegionalism>").append(personInfo.getResidenceRegionalism() == null?"":personInfo.getResidenceRegionalism()).append("</residenceRegionalism>");
                    str.append("<residenceAddr>").append(personInfo.getResidenceAddr() == null?"":personInfo.getResidenceAddr()).append("</residenceAddr>");
                    str.append("<personRemark>").append(personInfo.getPersonRemark() == null?"":personInfo.getPersonRemark()).append("</personRemark>");
                    str.append("<roughAge>").append(personInfo.getRoughAge() == null?"":personInfo.getRoughAge()).append("</roughAge>");
                    str.append("<extrinsicSign>").append(personInfo.getExtrinsicSign() == null?"":personInfo.getExtrinsicSign()).append("</extrinsicSign>");
                    str.append("<missingTime>").append(personInfo.getMissingTime() == null?"":personInfo.getMissingTime()).append("</missingTime>");
                    str.append("<missingPlace>").append(personInfo.getMissingPlace() == null?"":personInfo.getMissingPlace()).append("</missingPlace>");
                    str.append("<prisonType>").append(personInfo.getPrisonType() == null?"":personInfo.getPrisonType()).append("</prisonType>");
                    str.append("<involvedCaseNo>").append(personInfo.getInvolvedCaseNo() == null?"":personInfo.getInvolvedCaseNo()).append("</involvedCaseNo>");
                    str.append("<caseName>").append(personInfo.getCaseName() == null?"":personInfo.getCaseName()).append("</caseName>");
                    str.append("<caseProperty>").append(personInfo.getCaseProperty() == null?"":personInfo.getCaseProperty()).append("</caseProperty>");
                    str.append("<mobilePhone>").append(personInfo.getMobilePhone() == null?"":personInfo.getMobilePhone()).append("</mobilePhone>");
                    str.append("<familyName>").append(personInfo.getFamilyName() == null?"":personInfo.getFamilyName()).append("</familyName>");
                    str.append("<familyNo>").append(personInfo.getFamilyNo() == null?"":personInfo.getFamilyNo()).append("</familyNo>");
                    str.append("<age>").append(personInfo.getAge() == null?"":personInfo.getAge()).append("</age>");
                    str.append("<homePhone>").append(personInfo.getHomePhone() == null?"":personInfo.getHomePhone()).append("</homePhone>");
                    str.append("<email>").append(personInfo.getEmail() == null?"":personInfo.getEmail()).append("</email>");
                    str.append("<educationLevel>").append(personInfo.getEducationLevel() == null?"":personInfo.getEducationLevel()).append("</educationLevel>");
                    str.append("<identity>").append(personInfo.getIdentity() == null?"":personInfo.getIdentity()).append("</identity>");
                    str.append("<occupation>").append(personInfo.getOccupation() == null?"":personInfo.getOccupation()).append("</occupation>");
                    str.append("</personInfo>");
                }

                if(personInfo.getPersonnelNo() != null && !personInfo.getPersonnelNo().equals("-1")
                        && !"".equals(personInfo.getRelationObjectId()) && personInfo.getRelationObjectId() != null
                        && personInfo.getChildSampleInfoModel() != null) {
                    SampleInfoModel childPersonInfo = personInfo.getChildSampleInfoModel();
                    //判断是否已经拼接此被寻人,存在跳过，不存在继续拼接
                    if (org.apache.commons.lang3.StringUtils.isNotBlank(isExistPersonId)) {
                        if (isExistPersonId.equals(childPersonInfo.getPersonnelNo())) {
                            continue;
                        }
                    }
                    str.append("<personInfo>");
                    str.append("<personId>").append(childPersonInfo.getPersonnelNo() == null?"":childPersonInfo.getPersonnelNo()).append("</personId>");
                    str.append("<category>").append(childPersonInfo.getPersonnelType() == null?"":childPersonInfo.getPersonnelType()).append("</category>");
                    str.append("<personName>").append(childPersonInfo.getPersonnelName() == null?"":childPersonInfo.getPersonnelName()).append("</personName>");
                    if (StringUtils.isNotBlank(childPersonInfo.getGender())) {
                        if ("3".equals(childPersonInfo.getGender())) {
                            childPersonInfo.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                        }
                    }
                    str.append("<gender>").append(StringUtils.isBlank(childPersonInfo.getGender()) ? TransferConstants.NATION_GENDER_UNKNOWN : childPersonInfo.getGender()).append("</gender>");
                    str.append("<idCardNo>").append(childPersonInfo.getIdCardNo() == null?"无":childPersonInfo.getIdCardNo()).append("</idCardNo>");
                    str.append("<certificateType>").append(childPersonInfo.getCertificateName() == null?"":childPersonInfo.getCertificateName()).append("</certificateType>");
                    str.append("<certificateNo>").append(childPersonInfo.getCertificateNo() == null?"":childPersonInfo.getCertificateNo()).append("</certificateNo>");
                    str.append("<prisonNo>").append(childPersonInfo.getPrisonNo() == null?"":childPersonInfo.getPrisonNo()).append("</prisonNo>");
                    str.append("<createUser>").append(childPersonInfo.getCreateUser() == null?"无":childPersonInfo.getCreateUser()).append("</createUser>");
                    str.append("<createDatetime>").append(childPersonInfo.getCreateDatetime() == null ? currentDatetime :DateUtils.dateToString(childPersonInfo.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
                    str.append("<updateUser>").append(childPersonInfo.getUpdateUser() == null?"":childPersonInfo.getUpdateUser()).append("</updateUser>");
                    str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
                    if(childPersonInfo.getExternalSysType() != null && !"".equals(childPersonInfo.getExternalSysType()) && childPersonInfo.getExternalPersonnelNo() != null && !"".equals(childPersonInfo.getExternalSysType()) && !"无".equals(childPersonInfo.getExternalSysType())) {
                        str.append("<externalInfoList>");
                        str.append("<externalInfo>");
                        str.append("<externalSysType>").append(childPersonInfo.getExternalSysType()).append("</externalSysType>");
                        str.append("<externalSysNo>").append(childPersonInfo.getExternalPersonnelNo()).append("</externalSysNo>");
                        str.append("</externalInfo>");
                        str.append("</externalInfoList>");
                    }

                    str.append("<race>").append(childPersonInfo.getRace() == null?"":childPersonInfo.getRace()).append("</race>");
                    str.append("<personLabel>").append(childPersonInfo.getSpeciality() == null?"":childPersonInfo.getSpeciality()).append("</personLabel>");
                    str.append("<birthDatetime>").append(childPersonInfo.getBirthDateStr() == null?"":childPersonInfo.getBirthDateStr()).append("</birthDatetime>");
                    str.append("<nationality>").append(childPersonInfo.getNationality() == null?"":childPersonInfo.getNationality()).append("</nationality>");
                    str.append("<alias>").append(childPersonInfo.getAlias() == null?"":childPersonInfo.getAlias()).append("</alias>");
                    str.append("<nativePlaceRegionalism>").append(childPersonInfo.getNativePlaceRegionalism() == null?"":childPersonInfo.getNativePlaceRegionalism()).append("</nativePlaceRegionalism>");
                    str.append("<nativePlaceAddr>").append(childPersonInfo.getNativePlaceAddr() == null?"":childPersonInfo.getNativePlaceAddr()).append("</nativePlaceAddr>");
                    str.append("<residenceRegionalism>").append(childPersonInfo.getResidenceRegionalism() == null?"":childPersonInfo.getResidenceRegionalism()).append("</residenceRegionalism>");
                    str.append("<residenceAddr>").append(childPersonInfo.getResidenceAddr() == null?"":childPersonInfo.getResidenceAddr()).append("</residenceAddr>");
                    str.append("<personRemark>").append(childPersonInfo.getPersonRemark() == null?"":childPersonInfo.getPersonRemark()).append("</personRemark>");
                    str.append("<roughAge>").append(childPersonInfo.getRoughAge() == null?"":childPersonInfo.getRoughAge()).append("</roughAge>");
                    str.append("<extrinsicSign>").append(childPersonInfo.getExtrinsicSign() == null?"":childPersonInfo.getExtrinsicSign()).append("</extrinsicSign>");
                    str.append("<missingTime>").append(childPersonInfo.getMissingTime() == null?"":childPersonInfo.getMissingTime()).append("</missingTime>");
                    str.append("<missingPlace>").append(childPersonInfo.getMissingPlace() == null?"":childPersonInfo.getMissingPlace()).append("</missingPlace>");
                    str.append("<prisonType>").append(childPersonInfo.getPrisonType() == null?"":childPersonInfo.getPrisonType()).append("</prisonType>");
                    str.append("<involvedCaseNo>").append(childPersonInfo.getInvolvedCaseNo() == null?"":childPersonInfo.getInvolvedCaseNo()).append("</involvedCaseNo>");
                    str.append("<caseName>").append(childPersonInfo.getCaseName() == null?"":childPersonInfo.getCaseName()).append("</caseName>");
                    str.append("<caseProperty>").append(childPersonInfo.getCaseProperty() == null?"":childPersonInfo.getCaseProperty()).append("</caseProperty>");
                    str.append("<mobilePhone>").append(childPersonInfo.getMobilePhone() == null?"":childPersonInfo.getMobilePhone()).append("</mobilePhone>");
                    str.append("<familyName>").append(childPersonInfo.getFamilyName() == null?"":childPersonInfo.getFamilyName()).append("</familyName>");
                    str.append("<familyNo>").append(childPersonInfo.getFamilyNo() == null?"":childPersonInfo.getFamilyNo()).append("</familyNo>");
                    str.append("<age>").append(childPersonInfo.getAge() == null?"":childPersonInfo.getAge()).append("</age>");
                    str.append("<homePhone>").append(childPersonInfo.getHomePhone() == null?"":childPersonInfo.getHomePhone()).append("</homePhone>");
                    str.append("<email>").append(childPersonInfo.getEmail() == null?"":childPersonInfo.getEmail()).append("</email>");
                    str.append("<educationLevel>").append(childPersonInfo.getEducationLevel() == null ? "" : childPersonInfo.getEducationLevel()).append("</educationLevel>");
                    str.append("<identity>").append(childPersonInfo.getIdentity() == null?"":childPersonInfo.getIdentity()).append("</identity>");
                    str.append("<occupation>").append(childPersonInfo.getOccupation() == null?"":childPersonInfo.getOccupation()).append("</occupation>");
                    str.append("</personInfo>");

                    //给被寻人赋值
                    isExistPersonId = childPersonInfo.getPersonnelNo();
                }
            }
        }

        str.append("</personInfoList>");
        return str.toString();
    }

    public String getXmlPhysicalEvidence(List<PhysicalEvidenceModel> physicalEvidenceList) {

        String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

        StringBuffer str = new StringBuffer();
        str.append("<physicalEvidenceList>");
        if(physicalEvidenceList != null && physicalEvidenceList.size() > 0) {
            Iterator var4 = physicalEvidenceList.iterator();

            while(var4.hasNext()) {
                PhysicalEvidenceModel physicalEvidence = (PhysicalEvidenceModel)var4.next();
                str.append("<physicalEvidence>");
                str.append("<physicalEvidenceId>").append(physicalEvidence.getId() == null?"":physicalEvidence.getId()).append("</physicalEvidenceId>");
                str.append("<physicalEvidenceType>").append(physicalEvidence.getPhysicalEvidenceType() == null?"":physicalEvidence.getPhysicalEvidenceType()).append("</physicalEvidenceType>");
                str.append("<physicalEvidenceName>").append(physicalEvidence.getPhysicalEvidenceName() == null?"":physicalEvidence.getPhysicalEvidenceName()).append("</physicalEvidenceName>");
                str.append("<createUser>").append(physicalEvidence.getCreateUser() == null?"无":physicalEvidence.getCreateUser()).append("</createUser>");
                str.append("<createDatetime>").append(physicalEvidence.getCreateDatatime() == null ? currentDatetime : DateUtils.dateToString(physicalEvidence.getCreateDatatime(),"yyyyMMddHHmmss")).append("</createDatetime>");
                str.append("<updateUser>").append(physicalEvidence.getUpdateUser() == null?"无":physicalEvidence.getUpdateUser()).append("</updateUser>");
                str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
                if(StringUtils.isNotBlank(physicalEvidence.getExternalSysType())
                        && !StringUtils.equals("无", physicalEvidence.getExternalSysType())
                        && StringUtils.isNotBlank(physicalEvidence.getExternalSysNo())) {
                    str.append("<externalInfoList>");
                    str.append("<externalInfo>");
                    str.append("<externalSysType>").append(physicalEvidence.getExternalSysType()).append("</externalSysType>");
                    str.append("<externalSysNo>").append(physicalEvidence.getExternalSysNo()).append("</externalSysNo>");
                    str.append("</externalInfo>");
                    str.append("</externalInfoList>");
                }

                str.append("<physicalEvidenceDesc>").append(physicalEvidence.getDescription() == null?"":physicalEvidence.getDescription()).append("</physicalEvidenceDesc>");
                str.append("<physicalEvidenceRemark>").append(physicalEvidence.getRemark() == null?"":physicalEvidence.getRemark()).append("</physicalEvidenceRemark>");
                str.append("</physicalEvidence>");
            }
        }

        str.append("</physicalEvidenceList>");
        return str.toString();
    }

    public String getSampleInfoModelList(List<SampleInfoModel> sampleInfoList) {

        String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

        StringBuffer str = new StringBuffer();
        str.append("<sampleInfoList>");
        for(SampleInfoModel sampleInfo : sampleInfoList) {
            str.append("<sampleInfo>");
            str.append("<sampleId>").append(sampleInfo.getId() == null ? "" : sampleInfo.getId()).append("</sampleId>");
            str.append("<sampleLabNo>").append(sampleInfo.getSampleLabNo() == null ? "无" : sampleInfo.getSampleLabNo()).append("</sampleLabNo>");
            str.append("<sampleName>").append(" <![CDATA[ ").append(sampleInfo.getEvidenceName() == null ? "" : sampleInfo.getEvidenceName()).append("]]>").append("</sampleName>");
            str.append("<sampleType>").append(sampleInfo.getSampleType() == null ? "" : sampleInfo.getSampleType()).append("</sampleType>");
            str.append("<sampleStatus>").append(sampleInfo.getSampleStatus() == null ? "7" : sampleInfo.getSampleStatus()).append("</sampleStatus>");
            str.append("<createUser>").append(sampleInfo.getCreateUser() == null ? "无" : sampleInfo.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(sampleInfo.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(sampleInfo.getCreateDatetime(), "yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(sampleInfo.getUpdateUser() == null ? "无" : sampleInfo.getUpdateUser()).append("</updateUser>");
            str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
            str.append("<physicalEvidenceId>").append(sampleInfo.getPhysicalEvidenceId() == null ? "" : sampleInfo.getPhysicalEvidenceId()).append("</physicalEvidenceId>");
            str.append("<sampleDesc>").append(sampleInfo.getSampleDescription() == null ? "" : sampleInfo.getSampleDescription()).append("</sampleDesc>");
            str.append("<sampleRemark>").append(sampleInfo.getSampleRemark() == null ? "" : sampleInfo.getSampleRemark()).append("</sampleRemark>");
            str.append("<collectReason>").append(sampleInfo.getCollectReason() == null ? "" : sampleInfo.getCollectReason()).append("</collectReason>");
            str.append("<acceptOpinion>").append(sampleInfo.getAcceptOpinion() == null ? "" : sampleInfo.getAcceptOpinion()).append("</acceptOpinion>");
            str.append("<selfObjectId>").append(sampleInfo.getSelfObjectId() == null ? "" : sampleInfo.getSelfObjectId() + sampleInfo.getSampleLabNo()).append("</selfObjectId>");
            str.append("<selfObjectType>").append(sampleInfo.getSelfObjectType() == null ? "" : sampleInfo.getSelfObjectType()).append("</selfObjectType>");
            str.append("<relationObjectId>").append(sampleInfo.getRelationObjectId() == null ? "" : sampleInfo.getRelationObjectId()).append("</relationObjectId>");
            str.append("<relation>").append(sampleInfo.getRelation() == null ? "" : sampleInfo.getRelation()).append("</relation>");
            str.append("</sampleInfo>");
        }
        str.append("</sampleInfoList>");
        return str.toString();
    }

    public String getXmlSampleDnaGeneList(List<SampleGeneInfoModel> sampleDnaGeneList) {

        String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

        StringBuffer str = new StringBuffer();
        str.append("<sampleDnaGeneList>");
        if(sampleDnaGeneList != null && sampleDnaGeneList.size() > 0) {
            Iterator var4 = sampleDnaGeneList.iterator();

            while(var4.hasNext()) {
                SampleGeneInfoModel sampleDnaGene = (SampleGeneInfoModel)var4.next();
                str.append("<sampleDnaGene>");
                str.append("<geneId>").append(sampleDnaGene.getId() == null?"":sampleDnaGene.getId() + "HHH").append("</geneId>");
                str.append("<sampleId>").append(sampleDnaGene.getSampleId() == null?"":sampleDnaGene.getSampleId()).append("</sampleId>");
                str.append("<alleleCount>").append(sampleDnaGene.getAlleleCount()).append("</alleleCount>");
                str.append("<geneType>").append(sampleDnaGene.getGeneType() == null?"1":sampleDnaGene.getGeneType()).append("</geneType>");
                str.append("<reagentKit>").append(sampleDnaGene.getReagentKit() == null ? "无" : sampleDnaGene.getReagentKit()).append("</reagentKit>");
                str.append("<geneInfo>").append(sampleDnaGene.getGeneInfo() == null?"":sampleDnaGene.getGeneInfo()).append("</geneInfo>");
                str.append("<createUser>").append(sampleDnaGene.getCreateUser() == null ? "无" : sampleDnaGene.getCreateUser()).append("</createUser>");
                str.append("<createDatetime>").append(sampleDnaGene.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(sampleDnaGene.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
                str.append("<updateUser>").append(sampleDnaGene.getUpdateUser() == null ? "无" : sampleDnaGene.getUpdateUser()).append("</updateUser>");
                str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
                str.append("</sampleDnaGene>");
            }
        }

        str.append("</sampleDnaGeneList>");
        return str.toString();
    }
}
