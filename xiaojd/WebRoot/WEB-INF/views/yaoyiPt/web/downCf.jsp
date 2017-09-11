<%@page import="com.xiaojd.entity.hospital.EngPtUser"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtCf"%>
<%@page  import="com.xiaojd.entity.hospital.EngPtDrug"%>
<%@page  import="java.text.DecimalFormat"%>
<%@page  import="java.util.ArrayList"%>
<%@page  import="java.util.List"%>
<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%  String path = request.getContextPath();
		EngPtCf cf = (EngPtCf)request.getAttribute("cf");
		List<EngPtDrug> drugs = (List<EngPtDrug>)request.getAttribute("drugs");
		if(drugs == null) {
			drugs = new ArrayList<EngPtDrug>();
		}
		Double  moneyNumber =0.0;
	    for(int i=0;i<drugs.size();i++) {
	    	moneyNumber +=drugs.get(i).getMoney();
		 }
	    String money =String .format("%.2f",moneyNumber);
%>
<!doctype html>
<html>
<head>
    <title>处方${cf.id}下载</title>
	<script type="text/javascript" src="<c:url value="/scripts/web/jquery-1.8.3.min.js"/>"></script>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
	<style type="text/css">	
		.prescription{
			font: 13px Arial;
			display: block;
			width: 464px;
			margin: 0 auto;
			background-color: #fff;
			/* border: 1px solid #000; */
			border-radius:5px;
			padding: 10px 25px 10px 25px;
		}

		.prescription_box {
			border: 2px solid #000;
			width:100%;
			height:100%;
			overflow: hidden;
			box-sizing: border-box;
		}
		.prescription_tag{
			border: 1px solid red;
			position: absolute;
			top:40px;
			padding:3px;
			font-weight:bold;
			font-size:14px;
			color: #F00;
			-ms-transform:rotate(-9deg); /* IE 9 */
			-moz-transform:rotate(-9deg); /* Firefox */
			-webkit-transform:rotate(-9deg); /* Safari and Chrome */
			-o-transform:rotate(-9deg); /* Opera */
			transform:rotate(-9deg);
		}
		.prescription_inline{
			border: 0px solid red;
			width: 444px;
			overflow:hidden;
		}
		.prescription_inline .title{
			font-size: 23px;
			font-weight:bold;
			text-align:center;
			margin:5px 0 5px 0;
		}
		.prescription_inline div{
			white-space:nowrap; 
		}
		.prescription_inline .rp{
			/* border-bottom:1px solid #000; */
			height: 340px;
			padding:0px;
		}
		.prescription_inline .rp span{
			font-size: 30px;
		}
		.prescription_inline .rp .item{
			margin-bottom:4px;
		}
		.prescription_inline .rp .item div{
			margin:2px;
		}
         
         .prescription_inline .rp .item div{
			margin:2px;
		}


		.lable_drug_left{
			font-weight:bold;
			 margin:0 5px;
		}

		.lable_drug_right{
			 float:right;
			 font-weight:bold;
			 margin:0 5px;
		}
		.lable_drug_right_last{
			 float:right;
			 font-weight:bold;
			 margin:0 30px 0 0;
		}

		.lable_drug_right1{
			 float:right;
			 margin:0 15px;
		}
		.lable_drug_right_last1{
			 float:right;
			 margin:0 15px 0 15px;
		}

		.drug_right_div{
			 float:right;
			 margin:0 20px 0 5px;
		}

			.drug_right_div{
			 float:right;
			 margin:0 20px 0 5px;
		}

		.prescription_inline input{
			border: 0;
			/* border-bottom:1px solid #000; */
			margin:2px 5px;
		}

		.div_right {
			  width: 100%;
             height: 100%;
             overflow: hidden;
		}

"
		
	</style>
</head>	
<body>

<div class="prescription">
	<div class="prescription_inline">
		<div class="title">苏&nbsp州&nbsp市&nbsp立&nbsp医&nbsp院&nbsp东&nbsp区&nbsp门&nbsp诊&nbsp处&nbsp方</div>

		<div style="margin:0 4px 4px 4px" >
			<label>姓名</label>
			<lable style="font-weight:bold;display:inline-block;width:82px;">${cf.name}</lable>
			<label>性别</label>
			<lable style="font: 12px Arial;display:inline-block;width:60px;">${cf.sex}</lable>
			<label>年龄</label>
			<lable style="font: 12px Arial;display:inline-block;width:60px;">${cf.age}</lable>
			<label>日期</label>
			<lable style="font: 12px Arial;display:inline-block;width:120px;">${cf.presDate}</lable>
		</div>
		
       <div style="margin:0 4px 4px 4px" >
            <label>参保编号</label>
			<lable style="font: 11px Arial;display:inline-block;width:100px;">${cf.insurePerson}</lable>
			<label>处方号</label>
			<lable style="font: 12px Arial;display:inline-block;width:80px;">${cf.id}</lable>
			<label>人员类别</label>
			<lable style="font: 11px Arial;display:inline-block;width:80px;">${cf.payType}</lable>
		</div>
		 
		<div style="margin:0 4px 4px 4px" >
			<label>科别</label>
			<lable style="font: 11px Arial;display:inline-block;width:100px;">${cf.department}</lable>
			<label>门诊号</label>
			<lable style="font: 12px Arial;display:inline-block;width:130px;">${cf.patientNo}</lable>
		</div>
			<div style="margin:0 4px 15px 4px" >
			<label>临床诊断</label>
			<lable style="font-size: 10px; Arial;display:inline-block;width:240px;">${cf.diagnose}</lable>
		</div>
       <div class="prescription_box">
		<div class="rp">
			<span style="margin:0 5px;">℞</span>
			     <c:forEach var="drug"  items="${drugs}" >
			      <%-- <c:if test="${drug.realityQuantity > 0}"> --%>
					<div class="item">
						<div><label class="lable_drug_left">${drug.drugName}</label>
						<label class="lable_drug_right_last">${drug.quantity}/${drug.dispenseUnit}</label>
						<label class="lable_drug_right">X</label>
						<label class="lable_drug_right">${drug.spec}</label>
						</div>
						<div class="div_right">
						 <label  class="lable_drug_right_last1">${drug.adminFrequency}</label >
						 <label class="lable_drug_right1">${drug.adminRoute} </label>
						 <label class="lable_drug_right1">${drug.adminDose} </label> 
						</div>
					</div>
				<%-- 	</c:if> --%>
			   </c:forEach>

			<div class="item">
				<div><label>(以下内容为空白)</label></div>
				<div>

				</div>
			</div>
		</div>
		<div>
			<div style='width:25px;float:left;height:30px;'><span style='float:left;width:23px;height:100%;white-space: normal;padding-left:10px;box-sizing: border-box;padding-top:10px;'>调配</span>
			</div>
			<img style='width:118px;float:left;height:50px;'
		    	onerror="this.src='./images/signature_bmp/black.png'"
	       src="./images/signature_bmp/null.bmp"  />
			<div style='margin-left:5px;width:25px;float:left;height:30px;'><span style='float:left;width:23px;height:100%;white-space: normal;padding-left:10px;box-sizing: border-box;padding-top:10px;'>发药</span>
			</div>
			<img style='width:118px;float:left;height:50px;'
			onerror="this.src='./images/signature_bmp/black.png'"
	       src="./images/signature_bmp/null.bmp"  />
			<div style='margin-left:5px;width:25px;float:left;height:30px;'><span style='float:left;width:23px;height:100%;white-space: normal;padding-left:10px;box-sizing: border-box;padding-top:10px;'>医师</span>
			</div>
			<img style='width:118px;height:50px;'
			onerror="this.src='./images/signature_bmp/black.png'"
	       src="./images/signature_bmp/${cf.doctorBmp}.bmp"  />
		</div>
		<div style='margin: 3px 0'>
		  <%-- <label style="white-space: normal;">合计金额：<%=money%>元</label> --%>
		  <label style="white-space: normal;">&nbsp;</label>
		</div>
		</div>
		<div id="printPage" style='margin:10px 0 20px 0; text-align:center' onclick="printPage();">打印当前处方</div>
	</div>
	 
</div>	

</body>
</html>

<script>

function printPage(){
     $("#printPage").empty();
	 window.print();
}
</script>