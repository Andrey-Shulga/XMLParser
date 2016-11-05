package com.epam.as.xmlparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
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
 * Parse XML by SAX.
 * Using XSD for validating.
 */
public class SaxXmlEntityParser implements XmlEntityParser {

    private Logger infoLogger = LoggerFactory.getLogger("infoLogger");

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {

        List<Object> list = new ArrayList<>();
        String XsdFileName = "xsdtypes.xsd";

        DefaultHandler handler = new DefaultHandler() {
            private Object obj;
            private String propertyName;
            private Object value;
            private boolean isInteger = false;
            private boolean isName = false;

            @Override
            public void startDocument() throws SAXException {
                infoLogger.info("Start parsing XML by SAX, use for validation XSD file: {}", XsdFileName);
                infoLogger.info("Try to find entities: {}", entityClass);
            }

            @Override
            public void endDocument() throws SAXException {
                infoLogger.info("Parsing end. Entities found: {}", list.size());
                infoLogger.info("Print results:");
                for (Object o : list)
                    System.out.println(o);
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                switch (localName) {
                    case "tariff":
                        try {
                            obj = entityClass.newInstance();
                        } catch (InstantiationException | IllegalAccessException e1) {
                            e1.printStackTrace();
                            //TODO catch2log
                        }
                        break;
                    case "name":
                        isName = true;
                        break;
                    case "value":
                        isName = false;
                        break;
                    case "integer":
                        isInteger = true;
                        break;
                    case "string":
                        isInteger = false;
                        break;
                }
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {

                switch (localName) {
                    case "property": {
                        BeanInfo beanInfo = null;
                        try {
                            beanInfo = Introspector.getBeanInfo(entityClass);
                        } catch (IntrospectionException e) {
                            e.printStackTrace();
                            //TODO catch2log
                        }
                        if (beanInfo != null) {
                            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                            boolean done = false;
                            for (int k = 0; !done && k < descriptors.length; k++) {
                                if (descriptors[k].getName().equals(propertyName)) {
                                    try {
                                        descriptors[k].getWriteMethod().invoke(obj, value);
                                    } catch (IllegalAccessException | InvocationTargetException e) {
                                        e.printStackTrace();
                                        //TODO catch2log
                                    }
                                    done = true;
                                }
                            }
                        }
                    }
                    break;
                    case "tariff":
                        list.add(obj);
                        break;
                }

            }

            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {

                String content = new String(ch, start, length);

                if (isName) propertyName = content;
                else if (isInteger) value = new Integer(content);
                else value = content;
            }
        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try (InputStream input = SaxXmlEntityParser.class.getClassLoader().getResourceAsStream(XsdFileName)) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(input)}));
            SAXParser parser = factory.newSAXParser();
            parser.parse(in, handler);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
            //TODO catch2log
        }

        return list;
    }
}
