package com.esy.stack.process;

import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.esy.stack.dao.impl.WebSiteColumnMapper;
import com.esy.stack.download.Analystor;
import com.esy.stack.entity.WebSiteColumn;
import com.esy.stack.util.StatusEnum;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AnalystorTest {
	
	@Test
	public void test() {
		analystor.analys();
	}
	
	@Autowired
	private Analystor analystor;

	@Test
	public void test001() {
		analystor.analys();
	}
	
	public static void main(String[] args) throws DocumentException {
		SAXReader reader = new SAXReader();
        Document document = reader.read("src/test/resources/column.xml");
        Element root = document.getRootElement();
        for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            System.out.println(element.element("webSiteId").getData());;
        }
	}
	@Autowired
	private WebSiteColumnMapper webSiteColumnMapper; 
	
	@Test
	public void testParseXml() throws DocumentException {
		SAXReader reader = new SAXReader();
        Document document = reader.read("src/test/resources/column.xml");
        Element root = document.getRootElement();
        Iterator<Element> it = root.elementIterator();
        while(it.hasNext()) {
        	Element sub = it.next();
        	WebSiteColumn column = new WebSiteColumn();
        	column.setWebSiteId(Integer.parseInt(sub.element("webSiteId").getData().toString()));
        	column.setColumnAlias(sub.element("columnAlias").getData().toString());
        	column.setColumnName(sub.element("columnName").getData().toString());
        	column.setColumnUrl(sub.element("columnUrl").getData().toString());
        	Element domainurl = sub.element("domainUrl");
        	if(domainurl != null)
        		column.setDomainUrl(domainurl.getData().toString());
        	column.setStatus(StatusEnum.WAIT.getValue());
        	webSiteColumnMapper.insert(column);
        }
        
	}
	
	
}
