import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import com.epam.as.xmlparser.parser.SaxXmlEntityParser;
import com.epam.as.xmlparser.parser.StaxXmlEntityParser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Test for DOM parser.
 */
public class XmlEntityParsersTest {
    private static final String TEST_STRING = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<mobilecompany xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "               xsi:noNamespaceSchemaLocation=\"mobilecompany.xsd\">\n" +
            "\n" +
            "    <tariffs>\n" +
            "        <tariff>\n" +
            "            <property>\n" +
            "                <name>id</name>\n" +
            "                <value>\n" +
            "                    <integer>1</integer>\n" +
            "                </value>\n" +
            "            </property>\n" +
            "            <property>\n" +
            "                <name>name</name>\n" +
            "                <value>\n" +
            "                    <string>All for 500!</string>\n" +
            "                </value>\n" +
            "            </property>\n" +
            "            <property>\n" +
            "                <name>fee</name>\n" +
            "                <value>\n" +
            "                    <integer>500</integer>\n" +
            "                </value>\n" +
            "            </property>\n" +
            "            <property>\n" +
            "                <name>includedMinutes</name>\n" +
            "                <value>\n" +
            "                    <integer>50</integer>\n" +
            "                </value>\n" +
            "            </property>\n" +
            "            <property>\n" +
            "                <name>includedTraffic</name>\n" +
            "                <value>\n" +
            "                    <integer>500</integer>\n" +
            "                </value>\n" +
            "            </property>\n" +
            "        </tariff>\n" +
            "    </tariffs>\n" +
            "</mobilecompany>";

    private static final Tariff expectedEntity = new Tariff(1, "All for 500!", 500, 50, 500);
    private List<?> entityDomList;
    private List<?> entitySaxList;
    private List<?> entityStaxList;

    @Before
    public void setUp() throws Exception {

        DomXmlEntityParser domParser = new DomXmlEntityParser();
        SaxXmlEntityParser saxParser = new SaxXmlEntityParser();
        StaxXmlEntityParser staxParser = new StaxXmlEntityParser();
        Class<?> testClass = Tariff.class;

        try (InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes())) {
            entityDomList = domParser.parse(in, testClass);
        }
        try (InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes())) {
            entitySaxList = saxParser.parse(in, testClass);
        }
        try (InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes())) {
            entityStaxList = staxParser.parse(in, testClass);
        }

    }

    @After
    public void tearDown() throws Exception {

        entityDomList.clear();
        entitySaxList.clear();
        entityStaxList.clear();

    }

    @Test
    public void testDomParser() {

        assertEquals(entityDomList.get(0), expectedEntity);

    }


    @Test
    public void testSaxParser() {

        assertEquals(entitySaxList.get(0), expectedEntity);
    }

    @Test
    public void testStaxParser() {

        assertEquals(entityStaxList.get(0), expectedEntity);
    }

}
