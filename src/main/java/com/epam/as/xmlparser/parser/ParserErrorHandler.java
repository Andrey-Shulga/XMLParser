package com.epam.as.xmlparser.parser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Error handler for parser.
 */
class ParserErrorHandler implements ErrorHandler {
    private Logger logger = LoggerFactory.getLogger("ParserErrorHandler");

    ParserErrorHandler() {
    }

    private String getParseExceptionInfo(SAXParseException spe) {
        String systemId = spe.getSystemId();
        if (systemId == null) {
            systemId = "null";

        }

        return "URI=" + systemId + " Line=" + spe.getLineNumber() +
                ": " + spe.getMessage();
    }

    public void warning(SAXParseException spe) throws SAXException {
        logger.error("Warning: " + getParseExceptionInfo(spe));
    }

    public void error(SAXParseException spe) throws SAXException {
        String message = "Error: " + getParseExceptionInfo(spe);
        logger.error(message);
        throw new SAXException(message);

    }

    public void fatalError(SAXParseException spe) throws SAXException {
        String message = "Fatal Error: " + getParseExceptionInfo(spe);
        logger.error(message);
        throw new SAXException(message);
    }
}
