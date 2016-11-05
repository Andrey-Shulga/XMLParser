package com.epam.as.xmlparser.parser;

import java.io.InputStream;
import java.util.List;

/**
 * An interface for parsing XML
 */
interface XmlEntityParser {

    List<?> parse(InputStream in, Class<?> entityClass);

}
