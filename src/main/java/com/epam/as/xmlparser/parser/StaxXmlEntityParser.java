package com.epam.as.xmlparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse XML by StAX.
 */
public class StaxXmlEntityParser implements XmlEntityParser {

    private Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    private Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {
        List<Object> list = new ArrayList<>();
        Object obj = null;
        String propertyName = null;
        Object value = null;
        boolean isInteger = false;
        boolean isName = false;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader parser = null;
        try {
            parser = factory.createXMLStreamReader(in);

        } catch (XMLStreamException e) {
            errorLogger.error("Error with XMLStreamReader occur!", e);
        }
        infoLogger.info("Start parsing XML by StAX.");
        infoLogger.info("Try to find entities: {}", entityClass);
        try {
            while (parser.hasNext()) {
                int event = parser.next();

                switch (event) {
                    case XMLStreamConstants.END_DOCUMENT:
                        infoLogger.info("Parsing end. Entities found: {}", list.size());
                        infoLogger.info("Print results:");
                        for (Object o : list)
                            System.out.println(o);
                        break;

                    case XMLStreamConstants.START_ELEMENT:
                        switch (parser.getLocalName()) {
                            case "tariff":
                                obj = entityClass.newInstance();
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
                        break;

                    case XMLStreamConstants.CHARACTERS:
                        String content = parser.getText().trim();
                        if (!content.equals("")) {
                            if (isName) propertyName = content;
                            else if (isInteger) value = new Integer(content);
                            else value = content;
                        }
                        break;

                    case XMLStreamConstants.END_ELEMENT:
                        switch (parser.getLocalName()) {
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
                                    errorLogger.error("Errors with reflexion occur!", e);
                                }
                                break;

                            case "tariff":
                                list.add(obj);
                                break;
                        }
                        break;
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            errorLogger.error("Error with Class {} method newInstance() occur!", entityClass, e);
        } catch (XMLStreamException e) {
            errorLogger.error("Error with method hasNext() or next() occur!", e);
        }
        return list;
    }
}


