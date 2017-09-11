package com.xiaojd.controller.server;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

import java.net.MalformedURLException;
import java.net.URL; 
import javax.xml.namespace.QName; 
import javax.xml.soap.MessageFactory; 
import javax.xml.soap.SOAPBody; 
import javax.xml.soap.SOAPBodyElement; 
import javax.xml.soap.SOAPConstants; 
import javax.xml.soap.SOAPEnvelope; 
import javax.xml.soap.SOAPMessage; 
import javax.xml.ws.Dispatch; 
import javax.xml.ws.Service; 
import org.w3c.dom.Document;
/**
 * Created by DuLida on 2016/11/15.
 *
 * 该类为java发布的webservice（服务端客户端在一起，该客户端并非由wsdl2java生成）通过main方法访问
 *
 */
public class ClientForCXF {
  public static PtWebService getInterFace(){
    JaxWsProxyFactoryBean factoryBean = new JaxWsProxyFactoryBean();
    factoryBean.setServiceClass(PtWebService.class);
    factoryBean.setAddress("http://localhost:8080/xiaojd/server/web-publish");
    return (PtWebService) factoryBean.create();
  }
  @SuppressWarnings("unused")
  public static void main(String[] args) throws Exception {
	 String ns = "http://server.controller.xiaojd.com/";
	 String wsdlUrl ="http://localhost:8080/xiaojd/server/web-publish?wsdl";
  	 URL url= new URL(wsdlUrl);
  	 QName sname = new QName(ns,"PtWebServiceImplService");
  	 Service service = Service.create(url,sname); 
  	   
  	 //2、创建Dispatch 
  	Dispatch<SOAPMessage> dispatch = service.createDispatch(new QName(ns,"PtWebServiceImplPort"),SOAPMessage.class,Service.Mode.MESSAGE); 

    
    
    SOAPMessage msg=MessageFactory.newInstance(SOAPConstants.SOAP_1_1_PROTOCOL).createMessage();
    SOAPEnvelope envelope=msg.getSOAPPart().getEnvelope();
    SOAPBody body = envelope.getBody();
    
    
	//4 创建QName来指定消息中传递数据
	QName ename = new QName(ns,"runBackEmr","cfx");//
	SOAPBodyElement ele= body.addBodyElement(ename);
	// 传递参数  
	ele.addChildElement("patientNo").setValue("0000466865");
	msg.writeTo(System.out);
	System.out.println("\n invoking.....");
	
	SOAPMessage response = dispatch.invoke(msg);
	response.writeTo(System.out);
	System.out.println();
  }
}
