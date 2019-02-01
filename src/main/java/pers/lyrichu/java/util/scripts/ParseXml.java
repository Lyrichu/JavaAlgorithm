package pers.lyrichu.java.util.scripts;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ParseXml {
    public static void main(String[] args) throws Exception{
        String xmlPath = "src/main/resources/test.xml";
        printStudentInfo(xmlPath);
    }

    private static void printStudentInfo(String xmlPath) throws Exception{
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        File file = new File(xmlPath);
        if (!file.exists()) {
            System.err.printf("%s doesn't exist!\n",xmlPath);
            return;
        }
        Document doc = db.parse(file);
        Element element = doc.getDocumentElement();
        // print the root element of the document
        System.out.println("root element of document:"+element.getNodeName());
        NodeList nodeList = element.getElementsByTagName("student");
        // print the total student elements
        System.out.println("total students:"+nodeList.getLength());
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0;i<nodeList.getLength();i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) node;
                    NodeList nodeList1 = e.getElementsByTagName("name");
                    String name = nodeList1.item(0).getChildNodes().item(0).getNodeValue();
                    System.out.println("name:"+name);
                    nodeList1 = e.getElementsByTagName("grade");
                    String grade = nodeList1.item(0).getChildNodes().item(0).getNodeValue();
                    System.out.println("grade:"+grade);
                    String age = e.getElementsByTagName("age").item(0)
                            .getChildNodes().item(0).getNodeValue();
                    System.out.println("age:"+age);
                }
            }
        }
    }
}
