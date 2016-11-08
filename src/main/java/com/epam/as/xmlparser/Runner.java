package com.epam.as.xmlparser;

import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import com.epam.as.xmlparser.parser.SaxXmlEntityParser;
import com.epam.as.xmlparser.parser.StaxXmlEntityParser;
import com.epam.as.xmlparser.util.JDomXmlCreator;
import com.epam.as.xmlparser.util.JaxbXmlCreator;
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
    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger("Runner");

        String XmlFileName = "mobilecompany.xml";
        Class<?> entityClass = Tariff.class;
        List<Object> entityList = null;

        //Test parsing by DOM
        DomXmlEntityParser domParser = new DomXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) domParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Test parsing by Sax
        SaxXmlEntityParser saxParser = new SaxXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) saxParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Test parsing by Stax
        StaxXmlEntityParser staxParser = new StaxXmlEntityParser();
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) staxParser.parse(in, entityClass);

        } catch (IOException e) {
            logger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Create XML document from entity by JDOM and write to file.
        Document doc = null;
        if (entityList != null) doc = JDomXmlCreator.createDocument(entityList);
        if (doc != null) JDomXmlCreator.saveDocToXml("newJdomXml.xml", doc);

        //Create XML document from entity by JAXB and write to file.
        JaxbXmlCreator.createDocument(entityList, "newJaxbXml.xml");
    }
}
