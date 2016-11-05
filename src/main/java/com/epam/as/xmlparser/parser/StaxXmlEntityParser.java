package com.epam.as.xmlparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse XML by StAX.
 * Using XSD for validating.
 */
public class StaxXmlEntityParser implements XmlEntityParser {

    private Logger infoLogger = LoggerFactory.getLogger("infoLogger");
    private Logger errorLogger = LoggerFactory.getLogger("errorLogger");

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {
        List<Object> list = new ArrayList<>();
        String XsdFileName = "xsdtypes.xsd";

        XMLInputFactory factory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader pareser = factory.createXMLStreamReader(in);
        } catch (XMLStreamException e) {
            //TODO catch2log
        }


        return list;
    }
}

