package com.epam.as.xmlparser.parser;

import java.io.InputStream;
import java.util.List;

/**
 * An interface for parsing XML
 */
public interface XmlEntityParser {

    List<? extends Object> parse(InputStream in, Class<? extends Object> entityClass);

}
