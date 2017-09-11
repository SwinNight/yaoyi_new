package com.xiaojd.utils;
public class HisInfoPostTest {

    public static void main(String[] args) {
        //String param = "<root><patient><departID>001</departID><department>测试科</department><presType>基层常见病</presType><presSource>门诊</presSource><presDatetime>2015-09-0200:00:00</presDatetime><payType>医保</payType><presNo>FSCY20150800100</presNo><diagnose>慢性结膜炎</diagnose><age>666岁</age><sex>男</sex><name>张三</name><allergyList/><pregnancy/><breastFeeding/><dialysis/><docName>张医生</docName><pharmChkId>9791</pharmChkId><pharmDelvId>9784</pharmDelvId><pharmChkName>李彩霞</pharmChkName><pharmDelvName>孙少华</pharmDelvName><totalAmount>0.94</totalAmount><drugSensivity/></patient><operation/><prescriptions><prescription><drug>11902037DY0</drug><drugName>托吡卡胺滴眼液</drugName><regName>托吡卡胺滴眼液</regName><specification>8ml*1</specification><package>支</package><quantity>1</quantity><packUnit>支</packUnit><unitPrice>0.94</unitPrice><amount>0.94</amount><adminRoute>口服</adminRoute><adminFrequency>3次/天</adminFrequency><adminDose>0.5ml</adminDose><startTime>2015-08-3100:00:00</startTime><endTime>2015-09-0600:00:00</endTime><continueDays>6</continueDays></prescription></prescriptions></root>";
        //HisInfoPost.INSTANCE.runGetPrescription("http://127.0.0.1:8080/xiaojd/engine","J01XD04I351921");
        /*String info = HisInfoPost.INSTANCE.runxxxxx("http://127.0.0.1:8080/xiaojd/engine",param);
        System.out.println(info);*/
        String param = "<root><Pre><PreInfoPreNo=\"FSCY20150800100\"PreCode=\"FSCY20150800100\"PreType=\"1\"InDate=\"2015-09-02 00:00:00\"OutDate=\"\"DoctCode=\"\"DeptCode=\"\"PatientName=\"张三\"PatientType=\"自费\"Birthday=\"2000-09-02\"Gender=\"0\"LiverStatus=\"\"KidneyStatus=\"\"WomanStatus=\"\"AllegeInfo=\"\"BlCode=\"\"Branch=\"99\"/></Pre><ICD><ICDInfoPreNo=\"FSCY20150800100\"ICDCode=\"F28\"ICDName=\"高血压\"/></ICD><Drug><DrugInfoPreNo=\"FSCY20150800100\"OrderCode=\"FSCY20150800100\"OrderType=\"0\"OrderDate=\"2015-09-0200:00:00\"OrderDoctor=\"8076\"IsCurrent=\"是\"DrugCode=\"11902037DY0\"DrugName=\"托吡卡胺滴眼液\"DrugSpec=\"8ml*1\"UsingType=\"口服\"Frequency=\"qd\"FreqTimes=\"3\"Dcl=\"0.5\"DclUnit=\"ml\"Qnty=\"1\"QntyUnit=\"支\"Price=\"0.94\"GroupNo=\"\"BeginUseDate=\"\"EndUseDate=\"\"/></Drug>";
        int n = HisInfoPost.INSTANCE.VanAnaData("1","0",param);
        System.out.println(n);
        System.exit(0);
    }

}
