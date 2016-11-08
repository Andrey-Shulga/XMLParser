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

    private Logger logger = LoggerFactory.getLogger("SaxXmlEntityParser");
    private final String XSD_FILE_NAME = "mobilecompanytypes.xsd";

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {

        List<Object> list = new ArrayList<>();


        DefaultHandler handler = new DefaultHandler() {
            private Object obj;
            private String propertyName;
            private Object value;
            private boolean isInteger = false;
            private boolean isName = false;

            @Override
            public void startDocument() throws SAXException {
                logger.debug("Start parsing XML by SAX, use for validation XSD file: {}", XSD_FILE_NAME);
                logger.debug("Try to find entities: {}", entityClass);
            }

            @Override
            public void endDocument() throws SAXException {
                logger.debug("Parsing end. Entities found: {}", list.size());
                logger.debug("Print results:");
                for (Object o : list)
                    logger.debug(o.toString());
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                switch (localName) {
                    case "tariff":
                        try {
                            obj = entityClass.newInstance();
                        } catch (InstantiationException | IllegalAccessException e1) {
                            logger.error("Error occurs with newInstance() method for class {}", entityClass, e1);
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
                    case "property":
                        try {
                            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass);
                            if (beanInfo != null) {
                                PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                                boolean done = false;
                                for (int k = 0; !done && k < descriptors.length; k++) {
                                    if (descriptors[k].getName().equals(propertyName)) {
                                        descriptors[k].getWriteMethod().invoke(obj, value);
                                        done = true;
                                    }
                                }
                            }
                        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                            logger.error("Errors with reflexion occur!", e);
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
        try (InputStream input = SaxXmlEntityParser.class.getClassLoader().getResourceAsStream(XSD_FILE_NAME)) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(input)}));
            SAXParser parser = factory.newSAXParser();
            parser.parse(in, handler);
        } catch (IOException ioe) {
            logger.error("Validation XSD file: \"{}\" not found!", XSD_FILE_NAME, ioe);
        } catch (ParserConfigurationException pe) {
            logger.error("DocumentBuilder cannot be created which satisfies the configuration requested", pe);
        } catch (SAXException saxe) {
            logger.error("Error with parser occur", saxe);
        }

        return list;
    }
}
