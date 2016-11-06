package com.epam.as.xmlparser.util;

import com.epam.as.xmlparser.entity.Tariff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Create and write entities to XML file by JAXB.
 */
public class JaxbXmlCreator {

    private static Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    private static Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    public static void createDocument(List<?> list, String fileName) {

        Object obj;
        Class<?> entityClass = Tariff.class;
        JAXBContext context;
        try {
            if ((new File(fileName).exists())) new FileOutputStream(fileName);
            infoLogger.info("Trying to create XML document model from entity by JAXB...");
            for (Object o : list) {

                context = JAXBContext.newInstance(entityClass);
                Marshaller m = context.createMarshaller();
                m.marshal(o, new FileOutputStream(fileName, true));

            }
            infoLogger.info("Creating succeed! Written to file {}", fileName);
        } catch (FileNotFoundException e) {
            errorLogger.error("File {} no found", fileName, e);
        } catch (JAXBException e) {
            errorLogger.error("JAXB error occur!", e);
        }
    }
}
