package com.epam.as.xmlparser.util;


import com.epam.as.xmlparser.entity.Tariff;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Create and write entities to XML file.
 */
public class JDomXmlCreator {

    private static Tariff tariff;

    public static void saveDocToXml(String fileName, Document document) {

        XMLOutputter out = new XMLOutputter();
        try {
            out.output(document, new FileOutputStream(fileName));
        } catch (IOException e) {
            //TODO catch2log
        }


    }

    public static Document createDocument(List<?> list) {

        Element root = new Element("mobilecompany");
        Element tariffs = new Element("tariffs");
        root.addContent(tariffs);

        Iterator<?> entityIterator = list.iterator();
        while (entityIterator.hasNext()) {
            tariff = (Tariff) entityIterator.next();

            Element tariffElement = new Element("tariff");
            tariffElement.addContent(getPropertyElement("id", "integer", tariff.getId()));
            tariffElement.addContent(getPropertyElement("name", "string", tariff.getName()));
            tariffElement.addContent(getPropertyElement("fee", "integer", tariff.getFee()));
            tariffElement.addContent(getPropertyElement("includedMinutes", "integer", tariff.getIncludedMinutes()));
            tariffElement.addContent(getPropertyElement("includedTraffic", "integer", tariff.getIncludedTraffic()));
            tariffs.addContent(tariffElement);
        }
        return new Document(root);
    }

    private static Element getPropertyElement(String filedName, String filedType, Object filedValue) {
        Element propertyElemet = new Element("property");
        Element nameElement = new Element("name");
        nameElement.setText(filedName);
        propertyElemet.addContent(nameElement);
        Element valueElement = new Element("value");
        Element valueTypeElement = new Element(filedType);
        valueTypeElement.setText(filedValue.toString());
        valueElement.addContent(valueTypeElement);
        propertyElemet.addContent(valueElement);
        return propertyElemet;
    }


}
