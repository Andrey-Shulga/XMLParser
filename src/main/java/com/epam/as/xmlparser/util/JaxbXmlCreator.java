package com.epam.as.xmlparser.util;

import com.epam.as.xmlparser.entity.Tariff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Create and write entities to XML file by JAXB.
 */
public class JaxbXmlCreator {


    private static Logger logger = LoggerFactory.getLogger("JaxbXmlCreator");

    public static void createDocument(List<?> list, String fileName) {

        Object obj;
        Class<?> entityClass = Tariff.class;
        JAXBContext context;
        try {
            logger.debug("Trying to create XML document model from entity by JAXB...");
            context = JAXBContext.newInstance(entityClass);
            Marshaller m = context.createMarshaller();
            m.marshal(list.get(0), new FileOutputStream(fileName));
            logger.debug("Creating succeed! Written to file {}", fileName);
        } catch (FileNotFoundException e) {
            logger.error("File {} no found", fileName, e);
        } catch (JAXBException e) {
            logger.error("JAXB error occur!", e);
        }
    }
}
