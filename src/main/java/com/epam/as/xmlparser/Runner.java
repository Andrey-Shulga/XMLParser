package com.epam.as.xmlparser;

import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import com.epam.as.xmlparser.parser.SaxXmlEntityParser;
import com.epam.as.xmlparser.parser.StaxXmlEntityParser;
import com.epam.as.xmlparser.util.JDomXmlWriter;
import com.epam.as.xmlparser.util.JaxbXmlWriter;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This program parses XML on entities by DOM, SAX, StAX.
 * Create XML document from entities and write it to xml file, using JDOM.
 *
 * @author Andrey Shulga
 * @version 1.0   2016-11-03
 */
public class Runner {

    private static final Logger logger = LoggerFactory.getLogger("Runner");
    private static final String XML_FILE_NAME = "mobilecompany.xml";

    public static void main(String[] args) {

        Class<?> entityClass = Tariff.class;
        List<Object> entityList = null;

        //Test parsing by DOM
        DomXmlEntityParser domParser = new DomXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XML_FILE_NAME)) {
            entityList = (List<Object>) domParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XML_FILE_NAME, e);
        }

        //Test parsing by Sax
        SaxXmlEntityParser saxParser = new SaxXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XML_FILE_NAME)) {
            entityList = (List<Object>) saxParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XML_FILE_NAME, e);
        }

        //Test parsing by Stax
        StaxXmlEntityParser staxParser = new StaxXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XML_FILE_NAME)) {
            entityList = (List<Object>) staxParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XML_FILE_NAME, e);
        }

        //Create XML document from entity by JDOM and write to file.
        Document doc = null;
        if (entityList != null) doc = JDomXmlWriter.createDocument(entityList);
        if (doc != null) JDomXmlWriter.saveDocToXml("newJdomXml.xml", doc);

        //Create XML document from entity by JAXB and write to file.
        Tariff tariff = new Tariff(1, "New Tariff", 5000, 500, 5000);
        JaxbXmlWriter.createDocument(tariff, "newJaxbXml.xml");
    }
}
