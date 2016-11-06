package com.epam.as.xmlparser;

import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import com.epam.as.xmlparser.parser.SaxXmlEntityParser;
import com.epam.as.xmlparser.parser.StaxXmlEntityParser;
import com.epam.as.xmlparser.util.JDomXmlCreator;
import org.jdom2.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * This program parse XML on entity by DOM, SAX, StAX.
 *
 * @author Andrey Shulga
 * @version 1.0   2016-11-03
 */
public class Runner {
    public static void main(String[] args) {

        Logger errLogger = LoggerFactory.getLogger("errorLogger");

        String XmlFileName = "mobilecompany.xml";
        Class<?> entityClass = Tariff.class;
        List<Object> entityList = null;
        DomXmlEntityParser domParser = new DomXmlEntityParser();
        SaxXmlEntityParser saxParser = new SaxXmlEntityParser();
        StaxXmlEntityParser staxParser = new StaxXmlEntityParser();

        //Test parsing by DOM
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) domParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            errLogger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Test parsing by Sax
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) saxParser.parse(in, entityClass);
            entityList.clear();
        } catch (IOException e) {
            errLogger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Test parsing by Stax
        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) staxParser.parse(in, entityClass);

        } catch (IOException e) {
            errLogger.error("File: \"{}\" not found!", XmlFileName, e);
        }

        //Create XML document from entity and write XML to file.
        Document doc = null;
        if (entityList != null) doc = JDomXmlCreator.createDocument(entityList);
        if (doc != null) JDomXmlCreator.saveDocToXml("newXMLdoc.xml", doc);
    }
}
