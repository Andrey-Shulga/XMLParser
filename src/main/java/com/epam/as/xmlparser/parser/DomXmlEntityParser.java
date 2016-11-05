package com.epam.as.xmlparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
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
 * Using XSD for validating.
 */
public class DomXmlEntityParser implements XmlEntityParser {

    private Logger errLogger = LoggerFactory.getLogger("errorLogger");
    private Logger infoLogger = LoggerFactory.getLogger("infoLogger");

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {

        List<Object> list = new ArrayList<>();
        String XsdFileName = "xsdtypes.xsd";

        try {
            InputStream input = DomXmlEntityParser.class.getClassLoader().getResourceAsStream(XsdFileName);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);

            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(input)}));
            factory.setIgnoringElementContentWhitespace(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new ParserErrorHandler());
            infoLogger.info("Start parsing XML by DOM, use for validation XSD file: {}", XsdFileName);
            infoLogger.info("Try to find entities: {}", entityClass);
            Document doc = builder.parse(in);

            Element mobcompany = doc.getDocumentElement();

            NodeList tariffList = mobcompany.getFirstChild().getChildNodes();

            for (int i = 0; i < tariffList.getLength(); i++) {

                Object obj = entityClass.newInstance();

                NodeList propertyList = tariffList.item(i).getChildNodes();

                for (int j = 0; j < propertyList.getLength(); j++) {

                    Node propertyElement = propertyList.item(j);
                    Node nameElement = propertyElement.getFirstChild();
                    String propertyName = ((Text) nameElement.getFirstChild()).getData().trim();

                    Element valueElement = (Element) propertyElement.getLastChild();
                    Object value = parseValue(valueElement);

                    BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
                    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                    boolean done = false;
                    for (int k = 0; !done && k < descriptors.length; k++) {
                        if (descriptors[k].getName().equals(propertyName)) {
                            descriptors[k].getWriteMethod().invoke(obj, value);
                            done = true;
                        }
                    }
                }
                list.add(obj);
            }
            infoLogger.info("Parsing end. Entities found: {}", list.size());
            infoLogger.info("Print results:");
            for (Object o : list)
                System.out.println(o);

        } catch (IntrospectionException
                | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            errLogger.error("Error with reflection methods occur", e);
        } catch (IOException ioe) {
            errLogger.error("Validation XSD file: \"{}\" not found!", XsdFileName, ioe);
        } catch (ParserConfigurationException pe) {
            errLogger.error("DocumentBuilder cannot be created which satisfies the configuration requested", pe);
        } catch (SAXException saxe) {
            errLogger.error("Error with parser occur", saxe);
        }
        return list;
    }

    private Object parseValue(Element valueElement) {
        Element value = (Element) valueElement.getFirstChild();
        String text = ((Text) value.getFirstChild()).getData();
        if ("integer".equals(value.getTagName())) return new Integer(text);
        else if ("string".equals(value.getTagName())) return text;
        else return null;
    }
}
