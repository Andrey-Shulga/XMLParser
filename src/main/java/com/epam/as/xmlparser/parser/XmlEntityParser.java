package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.Tariff;

import java.io.InputStream;
import java.util.List;

/**
 * An interface for parsing XML
 */
public interface XmlEntityParser {

    List<Tariff> parse(InputStream in, Class<Tariff> entityClass);

}
