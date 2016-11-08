package com.epam.as.xmlparser.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Create and write entities to XML file by JAXB.
 */
public class JaxbXmlWriter {


    private static Logger logger = LoggerFactory.getLogger("JaxbXmlWriter");

    public static void createDocument(Object transformClass, String fileName) {

        JAXBContext context;
        try {
            logger.debug("Trying to create XML document model from entity by JAXB...");
            context = JAXBContext.newInstance(transformClass.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(transformClass, new FileOutputStream(fileName));
            logger.debug("Creating XML model succeed!");
            logger.debug("Written to file {}", fileName);
        } catch (FileNotFoundException e) {
            logger.error("File {} no found", fileName, e);
        } catch (JAXBException e) {
            logger.error("JAXB error occur!", e);
        }
    }
}
