package com.bazl.lims.transfer.task.xml;

import com.bazl.lims.transfer.constants.TransferConstants;
import com.bazl.lims.transfer.model.bo.ExternalSysInfo;
import com.bazl.lims.transfer.model.po.CaseInfoModel;
import com.bazl.lims.transfer.model.po.ConsignmentModel;
import com.bazl.lims.transfer.model.po.SampleGeneInfoModel;
import com.bazl.lims.transfer.model.po.SampleInfoModel;
import com.bazl.lims.transfer.utils.DateUtils;
import com.bazl.lims.transfer.utils.ListUtils;
import com.bazl.lims.transfer.utils.StringUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2019/6/10.
 */
public class XmlParamsToPersonNation {
    public String getXmlBasicInfo() {
        StringBuffer str = new StringBuffer();
        str.append("<fileInfo>");
        str.append("<version>").append("1.0").append("</version>");
        str.append("<system>").append("DNA Lims").append("</system>");
        str.append("</fileInfo>");
        return str.toString();
    }

    public synchronized String getXmlConsignment(ConsignmentModel consignment) {
        StringBuffer str = new StringBuffer();
        str.append("<consignment>");
        if(consignment != null) {
            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

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
            str.append("<consignDatetime>").append(consignment.getConsignDate() == null ? currentDatetime : DateUtils.dateToString(consignment.getConsignDate(),"yyyyMMddHHmmss")).append("</consignDatetime>");
            str.append("<acceptNo>").append(consignment.getAcceptNo() == null?"":consignment.getAcceptNo()).append("</acceptNo>");
            str.append("<acceptDatetime>").append(consignment.getAcceptDatetime() == null ? currentDatetime : DateUtils.dateToString(consignment.getAcceptDatetime(),"yyyyMMddHHmmss")).append("</acceptDatetime>");
            str.append("<acceptorName>").append(consignment.getReceptionManName() == null?"无":consignment.getReceptionManName()).append("</acceptorName>");
            str.append("<isAppend>").append(consignment.getIsAppend() == null?"0":consignment.getIsAppend()).append("</isAppend>");
            str.append("<createUser>").append(consignment.getCreateUser() == null ? "无" : consignment.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(consignment.getCreateDatetime() == null ? currentDatetime :DateUtils.dateToString(consignment.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
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

    public synchronized String getXmlCaseInfoModel(CaseInfoModel caseInfoModel) {
        StringBuffer str = new StringBuffer();
        str.append("<caseInfo>");
        if(caseInfoModel != null) {
            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

            str.append("<caseId>").append(caseInfoModel.getId() == null?"":caseInfoModel.getId()).append("</caseId>");
            str.append("<caseName>").append(" <![CDATA[ ").append(caseInfoModel.getCaseName() == null?"":caseInfoModel.getCaseName()).append("]]>").append("</caseName>");
            str.append("<caseType>").append(caseInfoModel.getCaseType() == null?"":caseInfoModel.getCaseType()).append("</caseType>");
            str.append("<caseProperty>").append(caseInfoModel.getCaseProperty() == null?"00":caseInfoModel.getCaseProperty()).append("</caseProperty>");
            str.append("<occurrenceDatetime>").append(caseInfoModel.getOccurrenceDateStr() == null?"":caseInfoModel.getOccurrenceDateStr()).append("</occurrenceDatetime>");
            str.append("<sceneRegionalism>").append(caseInfoModel.getSceneRegionalism() == null?"无":caseInfoModel.getSceneRegionalism()).append("</sceneRegionalism>");
            str.append("<scenePlace>").append(caseInfoModel.getScenePlace() == null?"无":caseInfoModel.getScenePlace()).append("</scenePlace>");
            str.append("<caseBrief>").append(" <![CDATA[ ").append(caseInfoModel.getCaseSummary() == null?"无":caseInfoModel.getCaseSummary()).append("]]>").append("</caseBrief>");
            str.append("<createUser>").append(caseInfoModel.getCreateUser() == null ? "无" : caseInfoModel.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(caseInfoModel.getCreateDatetimeStr() == null ? currentDatetime : caseInfoModel.getCreateDatetimeStr()).append("</createDatetime>");
            str.append("<updateUser>").append(caseInfoModel.getUpdateUser() == null ? "无" : caseInfoModel.getUpdateUser()).append("</updateUser>");
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
            str.append("<category>").append(caseInfoModel.getCategory() == null?"":caseInfoModel.getCategory()).append("</category>");
            str.append("<sceneAreaType>").append(caseInfoModel.getSceneAreaType() == null?"":caseInfoModel.getSceneAreaType()).append("</sceneAreaType>");
            str.append("<estimatedDeath>").append(caseInfoModel.getEstimatedDeath() == null?"":caseInfoModel.getEstimatedDeath()).append("</estimatedDeath>");
            str.append("<estimatedWounded>").append(caseInfoModel.getEstimatedWounded() == null?"":caseInfoModel.getEstimatedWounded()).append("</estimatedWounded>");
            str.append("<xckyCallbackURL>").append(caseInfoModel.getXckyCallbackURL() == null?"":caseInfoModel.getXckyCallbackURL()).append("</xckyCallbackURL>");
            str.append("<xckyConsignNo>").append(caseInfoModel.getXckyConsignNo() == null?"":caseInfoModel.getXckyConsignNo()).append("</xckyConsignNo>");
        }

        str.append("</caseInfo>");
        return str.toString();
    }

    public synchronized String getXmlSampleDnaGene(List<SampleGeneInfoModel> sampleDnaGenes) {
        StringBuffer str = new StringBuffer();
        String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

        str.append("<sampleDnaGeneList>");
        if(sampleDnaGenes.size() != 0) {
            Iterator var4 = sampleDnaGenes.iterator();

            while(var4.hasNext()) {
                SampleGeneInfoModel sampleDnaGene = (SampleGeneInfoModel)var4.next();
                str.append("<sampleDnaGene>");
                str.append("<geneId>").append(sampleDnaGene.getId() == null?"":sampleDnaGene.getId() + "HHH").append("</geneId>");
                str.append("<sampleId>").append(sampleDnaGene.getSampleId() == null?"":sampleDnaGene.getSampleId()).append("</sampleId>");
                str.append("<alleleCount>").append(sampleDnaGene.getAlleleCount()).append("</alleleCount>");
                str.append("<geneType>").append(sampleDnaGene.getGeneType() == null?"1":sampleDnaGene.getGeneType()).append("</geneType>");
                str.append("<reagentKit>").append(sampleDnaGene.getReagentKit() == null?"Identifiler":sampleDnaGene.getReagentKit()).append("</reagentKit>");
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

    public synchronized String getXmlPersonInfo(SampleInfoModel personInfo) {
        StringBuffer str = new StringBuffer();
        System.out.println("人员背景信息，条码号:" + personInfo.getSampleLabNo());

        String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

        if(personInfo.getPersonnelNo() != null && !"-1".equals(personInfo.getPersonnelNo())) {
            str.append("<personInfoList>");
            if(personInfo != null) {
                str.append("<personInfo>");
                str.append("<personId>").append(personInfo.getPersonnelNo() == null?"":personInfo.getPersonnelNo() + personInfo.getSampleLabNo()).append("</personId>");
                str.append("<category>").append(personInfo.getPersonnelType() == null?"":personInfo.getPersonnelType()).append("</category>");
                str.append("<personName>").append(personInfo.getPersonnelName() == null?"":personInfo.getPersonnelName()).append("</personName>");
                if (StringUtils.isNotBlank(personInfo.getGender())) {
                    if ("3".equals(personInfo.getGender())) {
                        personInfo.setGender(TransferConstants.NATION_GENDER_UNKNOWN);
                    }
                }
                str.append("<gender>").append(personInfo.getGender() == null?"0":personInfo.getGender()).append("</gender>");
                str.append("<idCardNo>").append(personInfo.getIdCardNo() == null?"无":personInfo.getIdCardNo()).append("</idCardNo>");
                str.append("<certificateType>").append(personInfo.getCertificateName() == null ? "" : personInfo.getCertificateName()).append("</certificateType>");
                str.append("<certificateNo>").append(personInfo.getCertificateNo() == null?"":personInfo.getCertificateNo()).append("</certificateNo>");
                str.append("<prisonNo>").append(personInfo.getPrisonNo() == null?"":personInfo.getPrisonNo()).append("</prisonNo>");
                str.append("<createUser>").append(personInfo.getCreateUser() == null ? "无" : personInfo.getCreateUser()).append("</createUser>");
                str.append("<createDatetime>").append(personInfo.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(personInfo.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
                str.append("<updateUser>").append(personInfo.getUpdateUser() == null ? "无" : personInfo.getUpdateUser()).append("</updateUser>");
                str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
                if(personInfo.getExternalSysTypes() != null && !"".equals(personInfo.getExternalSysType()) && personInfo.getExternalSysNos() != null && !"".equals(personInfo.getExternalSysType()) && !"无".equals(personInfo.getExternalSysType())) {
                    str.append("<externalInfoList>");

                    for(int i = 0; i < personInfo.getExternalSysTypes().length && personInfo.getExternalSysTypes()[i] != null && !"".equals(personInfo.getExternalSysTypes()[i]); ++i) {
                        str.append("<externalInfo>");
                        str.append("<externalSysType>").append(personInfo.getExternalSysTypes()[i]).append("</externalSysType>");
                        str.append("<externalSysNo>").append(personInfo.getExternalSysNos()[i]).append("</externalSysNo>");
                        str.append("</externalInfo>");
                    }

                    str.append("</externalInfoList>");
                }

                str.append("<race>").append(personInfo.getRace() == null?"":personInfo.getRace()).append("</race>");
                str.append("<personLabel>").append(personInfo.getSpeciality() == null?"":personInfo.getSpeciality()).append("</personLabel>");
                str.append("<birthDatetime>").append(personInfo.getBirthDateStr() == null?"":personInfo.getBirthDateStr()).append("</birthDatetime>");
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
                str.append("<involvedCaseNo>").append(personInfo.getInvolvedCaseNo() == null?"无":personInfo.getInvolvedCaseNo()).append("</involvedCaseNo>");
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

            str.append("</personInfoList>");
        }

        return str.toString();
    }

    public synchronized String getSampleInfoModel(SampleInfoModel sampleInfo) {
        StringBuffer str = new StringBuffer();
        str.append("<sampleInfoList>");
        if(sampleInfo != null) {
            String currentDatetime = DateUtils.dateToString(new Date(),"yyyyMMddHHmmss");

            str.append("<sampleInfo>");
            str.append("<sampleId>").append(sampleInfo.getId() == null?"":sampleInfo.getId()).append("</sampleId>");
            str.append("<sampleLabNo>").append(sampleInfo.getSampleLabNo() == null?"无":sampleInfo.getSampleLabNo()).append("</sampleLabNo>");
            str.append("<sampleName>").append(sampleInfo.getEvidenceName() == null?"":sampleInfo.getEvidenceName()).append("</sampleName>");
            str.append("<sampleType>").append(sampleInfo.getSampleType() == null?"":sampleInfo.getSampleType()).append("</sampleType>");
            str.append("<sampleStatus>").append(sampleInfo.getSampleStatus() == null?"7":sampleInfo.getSampleStatus()).append("</sampleStatus>");
            str.append("<createUser>").append(sampleInfo.getCreateUser() == null ? "无" : sampleInfo.getCreateUser()).append("</createUser>");
            str.append("<createDatetime>").append(sampleInfo.getCreateDatetime() == null ? currentDatetime : DateUtils.dateToString(sampleInfo.getCreateDatetime(),"yyyyMMddHHmmss")).append("</createDatetime>");
            str.append("<updateUser>").append(sampleInfo.getUpdateUser() == null ? "无" : sampleInfo.getUpdateUser()).append("</updateUser>");
            str.append("<updateDatetime>").append(currentDatetime).append("</updateDatetime>");
            str.append("<physicalEvidenceId>").append(sampleInfo.getPhysicalEvidenceId() == null?"":sampleInfo.getPhysicalEvidenceId()).append("</physicalEvidenceId>");
            str.append("<sampleDesc>").append(sampleInfo.getSampleDescription() == null?"":sampleInfo.getSampleDescription()).append("</sampleDesc>");
            str.append("<sampleRemark>").append(sampleInfo.getSampleRemark() == null?"":sampleInfo.getSampleRemark()).append("</sampleRemark>");
            str.append("<collectReason>").append(sampleInfo.getCollectReason() == null?"":sampleInfo.getCollectReason()).append("</collectReason>");
            str.append("<acceptOpinion>").append(sampleInfo.getAcceptOpinion() == null?"":sampleInfo.getAcceptOpinion()).append("</acceptOpinion>");
            str.append("<selfObjectId>").append(sampleInfo.getSelfObjectId() == null?"":sampleInfo.getSelfObjectId() + sampleInfo.getSampleLabNo()).append("</selfObjectId>");
            str.append("<selfObjectType>").append(sampleInfo.getSelfObjectType() == null?"":sampleInfo.getSelfObjectType()).append("</selfObjectType>");
            str.append("<relationObjectId>").append(sampleInfo.getRelationObjectId() == null?"":sampleInfo.getRelationObjectId()).append("</relationObjectId>");
            str.append("<relation>").append(sampleInfo.getRelation() == null?"":sampleInfo.getRelation()).append("</relation>");
            str.append("</sampleInfo>");
        }

        str.append("</sampleInfoList>");
        return str.toString();
    }

}
