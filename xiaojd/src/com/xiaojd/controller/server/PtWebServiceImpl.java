package com.xiaojd.controller.server;

import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.jws.WebService;

import com.xiaojd.entity.hospital.EngPtCf;
import com.xiaojd.entity.hospital.EngPtDispense;
import com.xiaojd.entity.hospital.EngPtDrug;
import com.xiaojd.entity.hospital.EngPtMessage;
import com.xiaojd.entity.util.PTSTATUS;
import com.xiaojd.service.hospital.EngPtCfService;
import com.xiaojd.service.hospital.EngPtDeliveryAddressService;
import com.xiaojd.service.hospital.EngPtDeliveryService;
import com.xiaojd.service.hospital.EngPtDispenseService;
import com.xiaojd.service.hospital.EngPtDrugService;
import com.xiaojd.service.hospital.EngPtMessageService;
import com.xiaojd.service.hospital.EngPtPharmacyService;
import com.xiaojd.service.hospital.EngPtUserService;

@WebService(endpointInterface = "com.xiaojd.controller.server.PtWebService")
public class PtWebServiceImpl implements  PtWebService  {
	@Resource
	EngPtCfService engPtCfService;
	@Resource
	EngPtDrugService engPtDrugService;
	@Resource
	EngPtMessageService engPtMessageService;
	@Resource
	EngPtDispenseService engPtDispenseService;
	@Resource
	EngPtUserService engPtUserService;
	@Resource
	EngPtDeliveryAddressService engPtDeliveryAddressService;
	@Resource
	EngPtDeliveryService engPtDeliveryService;
	@Resource
	EngPtPharmacyService engPtPharmacyService;
	//处方状态
	@Override
	public String runGetCf(String cardNo, String name, String idNo, String phoneNo) {
		if(idNo ==null) {
			idNo="";
		}
		idNo = idNo.replaceAll(" ", "");
		if(cardNo == null) {
			cardNo ="";
		} 
		cardNo = cardNo.replaceAll(" ", "");
		if(phoneNo ==null) {
			phoneNo ="";
		}
		phoneNo = phoneNo.replaceAll(" ", "");
		if(name ==null) {
			name ="";
		}
		   name = name.replaceAll(" ", "");
		   String xml = "<roots>";
		   if("".equals(cardNo)&&"".equals(idNo)&&"".equals(phoneNo)&&"".equals(name)) {
			   xml +="<isSuccess>0</isSuccess></roots>";
			   return xml;
		   }

		
			List<EngPtCf> ptCfList = engPtCfService.loadByCardAndName(cardNo, name, idNo,phoneNo);
			if(ptCfList == null ||ptCfList.size()  ==0) {
				xml +="<isSuccess>0</isSuccess></roots>";
			} else {
				for(int i=0;i<ptCfList.size();i++) {
					   xml +="<root>";
					   xml +=ptCfList.get(i).toXml();
					  List<EngPtDrug> ptDrugList = engPtDrugService.loadByCfid(ptCfList.get(i).getId());
					  xml += " <prescriptions>";
					  for(int j=0;j<ptDrugList.size();j++) {
						  xml += ptDrugList.get(j).toXml("社区医院");
					  }
					  xml += " </prescriptions>";
					  
					 List<EngPtMessage> ptMessageList = engPtMessageService.loadByCfid(ptCfList.get(i).getId());
					 xml += " <infos>";
					 for(int n=0;n<ptMessageList.size();n++) {
						  xml += ptMessageList.get(n).toXml();
					 }
					 xml += " </infos>";
					 xml +="</root>";
				}
				xml +="<isSuccess>1</isSuccess></roots>";
			}
			return xml;
		/*// TODO Auto-generated method stub
		return "<roots>"
				+ "<root><patient>	<departID>科室ID(门急诊时传入, 住院医嘱时可不传)"
				+ "</departID><department>科室(门急诊时传入, 住院医嘱时可不传)</department>"
				+ "<bedNo>住院床号(门急诊时可不传入)</bedNo>"
			+ "<presType>处方类型</presType>"
			+ "<presSource>来源</presSource>"
			+ "<presDatetime>处方时间</presDatetime>"
			+ "<payType>付款类型</payType>"
			+ "<patientNo>病人号(需要病人唯一标识)</patientNo>"
			+ "<presNo>处方号/医嘱号(住院时传入Z0)</presNo>"
			+ "<name>姓名</name>"
			+ "<diagnose>诊断</diagnose>"
			+ "<address>地址</address>"
			+ "<IDCard>身份证号码</IDCard>"
			+ "<phoneNo>电话</phoneNo>"
          	+ "<age>年龄</age>"
			+ "<sex>性别</sex>"
 			+ "<weight>体重</weight>"
			+ "<birthWeight>出生时体重</birthWeight>"
			+ "<ccr>内生肌酐清除率</ccr>"
			+ "<allergyList>过敏药品列表</allergyList>"
			+ "<pregnancy>是否怀孕</pregnancy>"
			+ "<timeOfPreg>孕期</timeOfPreg>"
			+ "<breastFeeding>是否哺乳</breastFeeding>"
 			+ "<proxName>代办人姓名</proxName>"
			+ "<proxIDCard>代办人身份证号码</proxIDCard>"
			+ "<docID>医生工号(门急诊时传入, 住院医嘱时可不传)</docID>"
			+ "<docName>医生姓名(门急诊时传入,住院医嘱时可不传)</docName>"
			+ "<docTitle>医生职称(门急诊时传入, 住院医嘱时可不传)</docTitle>"
            	+ "<pharmChkId>审核药师工号</pharmChkId>"
	+ "<pharmDelvId>发药药师工号</pharmDelvId >"
	+ "<pharmChkName>审核药师名称</pharmChkName>"
	+ "<pharmDelvName>发药药师名称</pharmDelvName>"
	+ "<pharmDelvTitle>发药药师职称</pharmDelvName>"
	+ "<totalAmount>处方总额</totalAmount>"
	+ "<scr>血肌酐浓度</scr>"
	+ "<alt>丙氨酸转氨酶</alt>"
	+ "<ast>天门冬氨酸转氨酶</ast>"
	+ "<bsa>bsa</bsa>"
	+ "<drugSensivity>菌检</drugSensivity>"
	+ "	</patient>"
		+ "<prescriptions>"
		+ "	<prescription>"
				+ "<drug>药品ID</drug>"
				+ "<drugName>药品名称</drugName>"
				+ "<regName>厂家</regName>"
				+ "<specification>规格</specification>"
				+ "<package>包装</package>"
				+ "<quantity>数量</quantity>"
				+ "<packUnit>销售单位</packUnit>"
				+ "<unitPrice>单价</unitPrice>"
				+ "<amount>金额</amount>"
				+ "<groupNo>组号</groupNo>"
				+ "<firstUse>首剂使用</firstUse>"
				+ "<prepForm>剂型</prepForm>"
				+ "<adminRoute>给药途径</adminRoute>"
				+ "<adminArea>给药部位</adminArea>"
				+ "<adminFrequency>给药频率</adminFrequency>"
				+ "<adminDose>给药剂量</adminDose>"
				+ "<adminMethod>给药时机</adminMethod>"
				+ "<type>类型（住院医嘱时传入）</type> "
				+ "<adminGoal>给药目的</adminGoal>"
             	+ "<docID>医生工号(住院医嘱时传入,门急诊时可不传)</DocID>"
				+ "<docName>医生姓名(住院医嘱时传入,门急诊时可不传)</docName>"
				+ "<docTitle>医生职称(住院医嘱时传入,门急诊时可不传)</docTitle>"
				+ "<departID>科室ID</departID>"
				+ "<department>科室名称(住院医嘱时传,门急诊可不传)</department>"
				+ "<nurseName>护士</nurseName>"
				+ "<startTime>开始时间</startTime>"
				+ "<endTime>结束时间</endTime>"
			+ "</prescription>"
		+ "</prescriptions>"
	+ "</root><isSuccess>1<isSuccess></roots>";*/
	}

	@Override
	public String runBackCf(String presNo, String status, String hospital, String dept, String doctor, String comment) {
		String xml =  "<root><isSuccess>1</isSuccess></root>";
		if(presNo ==null ||"".endsWith(presNo)||status ==null ||"".endsWith(status)||hospital ==null ||"".endsWith(hospital)||dept ==null ||"".endsWith(dept)||doctor ==null ||"".endsWith(doctor)) {
			xml ="<root><isSuccess>0</isSuccess></root>";
		} else {
			EngPtCf  ptCf = engPtCfService.loadByIdToHospital(presNo);
			if("社区".equals(ptCf.getHospitalOrPharmacy())&&ptCf!=null &&PTSTATUS.CF_NEW.getStatus().equals(ptCf.getStatus())) {
				EngPtDispense dispense = new EngPtDispense(ptCf.getPresNo(),ptCf.getStatus());
				ptCf.setStatus(status);//修改处方状态
				engPtCfService.saveOrUpdate(ptCf);
				dispense.setHospital(hospital);
				dispense.setComment(comment);
				dispense.setDepartment(dept);
				dispense.setDoctor(doctor);
			    engPtDispenseService.saveOrUpdate(dispense);
			} else {
				xml ="<root><isSuccess>0</isSuccess></root>";//不是新开的药，无法修改
			}
		}
		return xml;
	   //	return "<root><isSuccess>1</isSuccess></root>";
	}

	@Override
	public String runBackEmr(String patientNo) {
		// TODO Auto-generated method stub
		return "<root>"+
					  "<emr>"+
					  " <patientName>姓名</patientName>"+
					  " <patientNo>患者号</patientNo>"+
					  " <sex>性别</sex>"+
					  " <birthday>出生日期</birthday>"+
					  " <address>家庭地址</address>"+
					  " <chiefComplaint>主诉</chiefComplaint>"+
					  "  <historyPresent>现病史</historyPresent>"+
					  " <physicalExam>体格检查</physicalExam>"+
					  " <diagnosis>门诊诊断</diagnosis>"+
					  "  <treatment>处理措施</treatment>"+
					  "</emr>"+
					  "<isSuccess>1|0</isSuccess>"+
					 "</root>";
	}

	@Override
	public String runBackLis(String patientNo) {
		// TODO Auto-generated method stub
		return "<root>"+
                 " <exams>"+
				 "  <exam>"+
				 "    <patientNo>患者号</patientNo>"+
				 "   <name>姓名</name>"+
				 "   <sampleName>样本名称</sampleName>"+
				 "  <examID>检验项目ID<examID>"+
				 "  <examName>检验名称</examName>"+
				 "  <department>检验科室</department>"+
				 "   <doctor>医生</doctor>"+
				 "   <sendTime>开具时间</sendTime>"+
				 "   <receiveTime>接收时间</receiveTime>"+
				 "  <reportTime>报告时间</reportTime>"+
				 " <reportDoc>报告人</reportDoc>"+
				 " <checkDoc>审核人</checkDoc>"+
				 " <items>"+
				 "  <item>"+
				 " <examItemID>检验详细项目ID</examItemID>"+
				 "   <examItemName>检验详细项目名</examItemName>"+
				 "   <examItemResult>检验结果</examItemResult>"+
				 "   <examUnit>检验单位/examUnit>"+
				 "   <standardValue>标准值区间</standardValue>"+
				 " </item>"+
				 "    <item>"+
				 "   <examItemID>检验详细项目ID</examItemID>"+
				 "  <examItemName>检验详细项目名</examItemName>"+
				 "   <examItemResult>检验结果</examItemResult>"+
				 "  <examUnit>检验单位/examUnit>"+
				 "   <standardValue>标准值区间</standardValue>"+
				 "    </item>"+
				 "  </items>"+
				 " </exam>"+
				 " </exams>"+
				 " <isSuccess>1|0</isSuccess>"+
				 " </root>";
	}

}
