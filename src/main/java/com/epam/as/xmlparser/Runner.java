package com.epam.as.xmlparser;

import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
        DomXmlEntityParser domParser = new DomXmlEntityParser();
        List<Object> entityList = new ArrayList<>();

        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            entityList = (List<Object>) domParser.parse(in, Tariff.class);
        } catch (IOException e) {
            errLogger.error("File: \"{}\" not found!", XmlFileName, e);
        }


    }
}
