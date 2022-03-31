package testMyBatisSqlBuilder;

import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by LiuJun on 2016/8/25.
 */
public class MybatisSqlBuilder {
    public static void main(String[] args) throws NoSuchFieldException, ParserConfigurationException, IOException, SAXException, XPathExpressionException, OgnlException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setValidating(false);
        builderFactory.setNamespaceAware(false);
        builderFactory.setIgnoringComments(true);
        builderFactory.setIgnoringElementContentWhitespace(false);
        builderFactory.setCoalescing(false);
        builderFactory.setExpandEntityReferences(true);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();

        builder.setErrorHandler(new ErrorHandler() {
            @Override
            public void warning(SAXParseException exception) {
                System.out.println("warning:" + exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) {
                System.out.println("error:" + exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) {
                System.out.println("fatalError:" + exception.getMessage());
            }
        });
        String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
        String sql = prefix + "<sql>" + "UPDATE \"icon\" SET\n" +
                "        <if test=\"name != null\">\"name\" = #{name},</if>\n" +
                "        <if test=\"url != null\">\"url\" = #{url},</if>\n" +
                "        \"updatedBy\" = #{updatedBy},\n" +
                "        \"updatedTime\" = #{updatedTime}\n" +
                "        WHERE \"code\" = #{code};" + "</sql>";
        ByteArrayInputStream sqlIs = new ByteArrayInputStream(sql.getBytes(StandardCharsets.UTF_8));
        Document sqlDoc = builder.parse(sqlIs);
        Node sqlNode = sqlDoc.getElementsByTagName("sql").item(0);
        NodeList nodeList = sqlNode.getChildNodes();
        Map<String, String> map = new HashMap<>();
        map.put("name", null);
        map.put("url", "'111'");
        map.put("updatedBy", "'a'");
        map.put("updatedTime", "'2021-01-09'");
        map.put("code", "'m'");
        String sqlStr = "";
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if("if".equals(node.getNodeName())){
                Node testNode = node.getAttributes().getNamedItem("test");
                if(null != testNode && evaluateBoolean(testNode.getNodeValue(),map)){
                    sqlStr += getNodeText(node.getChildNodes().item(0));
                }
            }else{
                sqlStr += getNodeText(node);
            }
        }
        StrSubstitutor substitutor = new StrSubstitutor(map,"#{","}",'#');
        System.out.println(substitutor.replace(sqlStr));
    }

    private static boolean evaluateBoolean(String expression,Object param) throws OgnlException {
        Object value = Ognl.getValue(expression, param);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            return new BigDecimal(String.valueOf(value)).compareTo(BigDecimal.ZERO) != 0;
        }
        return value != null;
    }

    private static String getNodeText(Node node) {
        if(null != node && (node.getNodeType() == Node.CDATA_SECTION_NODE
                || node.getNodeType() == Node.TEXT_NODE)){
            return ((CharacterData) node).getData();
        }else{
            return "";
        }
    }

}
