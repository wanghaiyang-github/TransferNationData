package com.bazl.lims.transfer.task.service.impl;

import com.bazl.lims.transfer.model.bo.SubmitNationDataModel;
import com.bazl.lims.transfer.model.po.SampleGeneInfoModel;
import com.bazl.lims.transfer.task.TransferNationParamsConfig;
import com.bazl.lims.transfer.task.service.SubmitToNation2CriminalService;
import com.bazl.lims.transfer.task.xml.XmlParamsToCriminalNation;
import com.bazl.lims.transfer.utils.DateUtils;
import com.bazl.lims.transfer.utils.StringUtils;
import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/10.
 */
@Service
public class SubmitToNation2CriminalServiceImpl implements SubmitToNation2CriminalService {

    private final Logger logger = LoggerFactory.getLogger(SubmitToNation2CriminalServiceImpl.class);


    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;

    @Override
    public Map<String, String> submitToNation(SubmitNationDataModel submitNationDataModel) throws Exception {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        String result = "-1";
        String params = "";
        String xmlParamStart = "<?xml version=\'1.0\' encoding=\'UTF-8\'?><ROOT>";
        String xmlParamEnd = "</ROOT>";
        XmlParamsToCriminalNation xmlParams = new XmlParamsToCriminalNation();
        String xmlBasicInfo = xmlParams.getXmlBasicInfo();
        String xmlConsignment = xmlParams.getXmlConsignment(submitNationDataModel.getConsignmentModel());
        logger.info("caseSubmit---xmlConsignment is : " + xmlConsignment);

        String xmlCaseInfoModel = xmlParams.getXmlCaseInfoModel(submitNationDataModel.getCaseInfoModel());
        logger.info("caseSubmit---xmlCaseInfoModel is : " + xmlCaseInfoModel);

        String xmlPersonInfo = xmlParams.getXmlPersonInfoList(submitNationDataModel.getSampleInfoModelList());
        logger.info("caseSubmit---xmlPersonInfo is : " + xmlPersonInfo);

        String xmlPhysicalEvidence = xmlParams.getXmlPhysicalEvidence(submitNationDataModel.getPhysicalEvidenceModelList());
        logger.info("caseSubmit---xmlPhysicalEvidence is : " + xmlPhysicalEvidence);

        String xmlSampleInfoModel = xmlParams.getSampleInfoModelList(submitNationDataModel.getSampleInfoModelList());
        logger.info("caseSubmit---xmlSampleInfoModel is : " + xmlSampleInfoModel);

        String resu = null;
        String fileName = null;

        if (submitNationDataModel.getSampleGeneInfoModelList().size() > 0) {
            for (SampleGeneInfoModel sampleGene : submitNationDataModel.getSampleGeneInfoModelList()) {
                if (StringUtils.isNotBlank(sampleGene.getGeneInfo())) {

                    String xmlDnaGene = xmlParams.getXmlSampleDnaGeneList(submitNationDataModel.getSampleGeneInfoModelList());
                    logger.info("caseSubmit---xmlDnaGene is : " + xmlDnaGene);
                    params = xmlParamStart + xmlBasicInfo + xmlConsignment + xmlCaseInfoModel + xmlPersonInfo + xmlPhysicalEvidence + xmlSampleInfoModel + xmlDnaGene + xmlParamEnd;

                    fileName = this.getFileName("1", submitNationDataModel.getConsignmentModel().getAcceptNo());
                    logger.info("文件路径名称==" + fileName);
                    System.out.println("文件路径名称==" + fileName);
                    resu = this.strChangeXML(params, fileName);
                } else {
                    result = "-3";
                    resultMap.put("message", "案件样本上报国家库2期生成文件失败！基因型不能为空");
                    logger.info("案件样本上报国家库2期生成文件失败：基因型不能为空");
                    resultMap.put("result", result);
                    return resultMap;
                }
            }
        } else {
            result = "-3";
            resu = "-3";
        }

        if (resu.equals("1")) {
            result = "1";
            resultMap.put("message", fileName);
        } else if (resu.indexOf(",") > 0 && resu.split(",")[0].equals("-1")) {
            resultMap.put("message", "案件样本上报国家库2期生成文件失败！" + resu.split(",")[1]);
            logger.info("案件样本上报国家库2期生成文件失败：111111111111111");
        } else if (resu.equals("-3")) {
            resultMap.put("message", "案件样本上报国家库2期生成文件失败！基因型不能为空");
            logger.info("案件样本上报国家库2期生成文件失败：基因型不能为空");
        } else {
            resultMap.put("message", "案件样本上报国家库2期生成文件失败！");
            logger.info("案件样本上报国家库2期生成文件失败：22222222222222");
        }

        resultMap.put("result", result);
        return resultMap;
    }

    public synchronized String strChangeXML(String str, String fileName) throws IOException {
        String result = "-1";
        SAXReader saxReader = new SAXReader();

        try {
            Document document = saxReader.read(new ByteArrayInputStream(str.getBytes("UTF-8")));
            OutputFormat e = OutputFormat.createPrettyPrint();
            e.setEncoding("UTF-8");
            FileOutputStream outStream = new FileOutputStream(fileName);
            XMLWriter writer = new XMLWriter(outStream, e);
            writer.write(document);
            writer.close();
            result = "1";
            logger.info("生成xml文件成功，文件路径：" + fileName);
        } catch (Exception var9) {
            var9.printStackTrace();
            logger.error("生成xml文件失败：" + var9.getMessage());
            result = "-1," + var9.getMessage();
        }

        return result;
    }


    public synchronized String getFileName(String fileType, String caseSn) {
        String createFileURL = transferNationParamsConfig.getCreateNationalLibrary2FileURL();
        String fileName = "";
        if (fileType.equals("1")) {
            if (caseSn.equals("")) {
                return null;
            }

            fileName = caseSn + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + System.currentTimeMillis();
        } else if (fileType.equals("2")) {
            fileName = "P" + DateUtils.dateToString(new Date(), "yyyyMMddHHmmss") + System.currentTimeMillis();
        }

        return createFileURL + fileName + ".xml";
    }
}
