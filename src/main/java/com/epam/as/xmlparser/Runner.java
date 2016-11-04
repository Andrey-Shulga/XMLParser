package com.epam.as.xmlparser;

import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;

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

        String XmlFileName = "mobilecompany.xml";
        DomXmlEntityParser domParser = new DomXmlEntityParser();
        List<Tariff> tariffList = new ArrayList<>();

        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
            tariffList = domParser.parse(in, Tariff.class);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO catch to log
        }
        System.out.println(tariffList.size());
        for (Tariff t : tariffList)
            System.out.println(t);

    }
}
