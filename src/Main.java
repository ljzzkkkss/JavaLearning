import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * Created by LiuJun on 2016/8/25.
 */
public class Main {
    public static void main(String[] args) throws NoSuchFieldException, ParserConfigurationException, IOException, SAXException, XPathExpressionException {
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
                System.out.println("warning:"+exception.getMessage());
            }

            @Override
            public void error(SAXParseException exception) {
                System.out.println("error:"+exception.getMessage());
            }

            @Override
            public void fatalError(SAXParseException exception) {
                System.out.println("fatalError:"+exception.getMessage());
            }
        });
        String prefix = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">";
        String sql = prefix + "<sql>" + "UPDATE \"icon\" SET\n" +
                "        <if test=\"name != null\">\"name\" = #{name},</if>\n" +
                "        <if test=\"url != null\">\"url\" = #{url},</if>\n" +
                "        \"updatedBy\" = #{updatedBy},\n" +
                "        \"updatedTime\" = #{updatedTime}\n" +
                "        WHERE \"code\" = #{code};"+ "</sql>" ;
        ByteArrayInputStream sqlIs = new ByteArrayInputStream(sql.getBytes("GBK"));
        Document sqlDoc = builder.parse(sqlIs);
        // 以下通过 XPath 来获取对应的信息
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        // 解析 所有sql节点下的if节点
        XPathExpression expression = xPath.compile("//sql//if[test]");
        Object result = expression.evaluate(sqlDoc, XPathConstants.NODESET);
        NodeList nodeList = (NodeList)result;
        for (int i = 0; i < nodeList.getLength(); i++) {
            System.out.println(nodeList.item(i).getNodeValue());
        }
    }

}
