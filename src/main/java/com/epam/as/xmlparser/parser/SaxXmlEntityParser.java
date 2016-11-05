package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.Tariff;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Parse XML by SAX.
 * Using XSD for validating.
 */
public class SaxXmlEntityParser implements XmlEntityParser {

    @Override
    public List<?> parse(InputStream in, Class<?> entityClass) {

        List<Tariff> list = new ArrayList<>();
        String XsdFileName = "xsdtypes.xsd";

        DefaultHandler handler = new DefaultHandler() {

        };

        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setNamespaceAware(true);
        try (InputStream input = DomXmlEntityParser.class.getClassLoader().getResourceAsStream(XsdFileName)) {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            factory.setSchema(schemaFactory.newSchema(new Source[]{new StreamSource(input)}));
        } catch (SAXException | IOException e) {
            e.printStackTrace();
            //TODO catch2log
        }
        return null;
    }
}
