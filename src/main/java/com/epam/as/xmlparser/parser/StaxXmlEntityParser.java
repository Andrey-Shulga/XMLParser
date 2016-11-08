package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.MobileCompany;
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

    private Logger logger = LoggerFactory.getLogger("StaxXmlEntityParser");
    private MobileCompany mobileCompany;


    public StaxXmlEntityParser() {
        this.mobileCompany = new MobileCompany();
    }

    @Override
    public <T> MobileCompany parse(InputStream in, Class<?> entityClass) {
        List<Object> entityList = new ArrayList<>();
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
            logger.error("Error with XMLStreamReader occur!", e);
        }
        logger.debug("Start parsing XML by StAX.");
        logger.debug("Try to find entities: {}", entityClass);
        try {
            while (parser.hasNext()) {
                int event = parser.next();

                switch (event) {
                    case XMLStreamConstants.END_DOCUMENT:
                        logger.debug("Parsing end. Entities found: {}", entityList.size());
                        logger.debug("Print results:");
                        for (Object o : entityList)
                            logger.debug(o.toString());
                        mobileCompany.setTariffs(entityList);
                        logger.debug("Aggregator object {} with entities {} create.", mobileCompany.getClass(), entityClass);
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
                                    logger.error("Errors with reflexion occur!", e);
                                }
                                break;

                            case "tariff":
                                entityList.add(obj);
                                break;
                        }
                        break;
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error with Class {} method newInstance() occur!", entityClass, e);
        } catch (XMLStreamException e) {
            logger.error("Error with method hasNext() or next() occur!", e);
        }
        return mobileCompany;
    }
}


