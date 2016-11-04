package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.Tariff;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse XML by DOM.
 */
public class DomXmlEntityParser implements XmlEntityParser {

    @Override
    public List<Tariff> parse(InputStream in, Class<Tariff> entityClass) {
        List<Tariff> list = new ArrayList<>();


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(true);
            factory.setNamespaceAware(true);
            final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";
            final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";
            factory.setAttribute(JAXP_SCHEMA_LANGUAGE, W3C_XML_SCHEMA);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(in);

            Element mobcompany = doc.getDocumentElement();
            NodeList mocompanyTariffs = mobcompany.getElementsByTagName("tariffs");
            Element tariffs = (Element) mocompanyTariffs.item(0);
            NodeList tariffList = tariffs.getElementsByTagName("tariff");

            for (int i = 0; i < tariffList.getLength(); i++) {
                Class<?> cl = entityClass;
                Object obj = cl.newInstance();

                Element tariff = (Element) tariffList.item(i);
                NodeList propertyList = tariff.getElementsByTagName("property");

                for (int j = 0; j < propertyList.getLength(); j++) {

                    Node propertyElement = propertyList.item(j);
                    Node nameElement = propertyElement.getFirstChild();
                    String propertyName = ((Text) nameElement.getFirstChild()).getData().trim();

                    Element valueElement = (Element) propertyElement.getLastChild();
                    Object value = parseValue(valueElement);
                    BeanInfo beanInfo = Introspector.getBeanInfo(cl);
                    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                    boolean done = false;
                    for (int k = 0; !done && k < descriptors.length; k++) {
                        if (descriptors[k].getName().equals(propertyName)) {
                            descriptors[k].getWriteMethod().invoke(obj, value);
                            done = true;
                        }
                    }

                }
                list.add((Tariff) obj);


            }

        } catch (ParserConfigurationException | SAXException | IOException | IntrospectionException
                | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            //TODO catch to log
        }


        return list;
    }

    private Object parseValue(Element valueElement) {
        Element value = (Element) valueElement.getFirstChild();
        String text = ((Text) value.getFirstChild()).getData();
        if (value.getTagName().equals("integer")) return new Integer(text);
        else if (value.getTagName().equals("string")) return text;
        else return null;
    }
}
