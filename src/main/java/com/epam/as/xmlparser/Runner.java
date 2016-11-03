package com.epam.as.xmlparser;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.io.InputStream;

/**
 * This program parse XML on entity by DOM, SAX, StAX.
 *
 * @author Andrey Shulga
 * @version 1.0   2016-11-03
 */
public class Runner {
    public static void main(String[] args) throws ClassNotFoundException, IntrospectionException {

        String XmlFileName = "mobilecompany";

        try (InputStream in = Runner.class.getClassLoader().getResourceAsStream(XmlFileName)) {
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
