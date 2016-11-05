import com.epam.as.xmlparser.entity.Tariff;
import com.epam.as.xmlparser.parser.DomXmlEntityParser;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

/**
 * Test for DOM parser.
 */
public class ParsersTest {
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


    @Test
    public void testDomParse() {

        DomXmlEntityParser parser = new DomXmlEntityParser();
        List<?> entityList;
        InputStream in = new ByteArrayInputStream(TEST_STRING.getBytes());
        Class<?> testClass = Tariff.class;
        entityList = parser.parse(in, testClass);

        assertEquals(entityList.get(0), expectedEntity);
    }

}
