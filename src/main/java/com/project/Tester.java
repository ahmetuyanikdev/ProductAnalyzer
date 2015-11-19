package com.project;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class Tester {

    public static void xmlRowHandler() throws Exception{

        File fXmlFile = new File("src/main/resources/site1.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("row");

        System.out.println("---------------------------- " +nList.getLength());

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());

            if(nNode.getNodeType() == Node.ELEMENT_NODE){
                Element eElement = (Element) nNode;
                System.out.println("Staff id : " + eElement.getElementsByTagName("title").item(0).getTextContent());
            }
        }
    }

    public static void main(String [] args){
        System.out.println("dsdsd");
        try {
            xmlRowHandler();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

