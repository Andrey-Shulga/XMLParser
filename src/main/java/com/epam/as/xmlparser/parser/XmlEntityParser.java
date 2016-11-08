package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.MobileCompany;

import java.io.InputStream;

/**
 * An interface for parsing XML
 */
interface XmlEntityParser {

    <T> MobileCompany parse(InputStream in, Class<?> entityClass);

}
