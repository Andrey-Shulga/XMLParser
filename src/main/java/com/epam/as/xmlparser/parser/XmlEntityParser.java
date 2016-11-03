package com.epam.as.xmlparser.parser;

import com.epam.as.xmlparser.entity.Tariff;

import java.io.InputStream;
import java.util.Collection;

/**
 * An interface for parsing XML
 */
public interface XmlEntityParser {

    <T extends Collection> Tariff parse(InputStream in, Class<T> collectionClass, Class<? extends Object> entityClass);

}
