package com.project.actor;

import akka.actor.UntypedActor;
import com.project.message.Job;
import com.project.model.Product;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

public class Worker extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {

        if(message instanceof Job){
            Job job = (Job) message;
            job.setProducts(parseXmlToProductList(job.getFilePath()));
            getSender().tell(job,getSelf());
        }
        else{
            unhandled(message);
        }
    }

    private List<Product> parseXmlToProductList(String filePath) throws Exception{

        File fXmlFile = new File(filePath);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("row");

        List<Product> products = new LinkedList<Product>();

        for (int temp = 0; temp < nList.getLength(); temp++) {

            Node nNode = nList.item(temp);

            if(nNode.getNodeType() == Node.ELEMENT_NODE){

                Element eElement = (Element) nNode;
                Product product = new Product();
                product.setPrices(eElement.getElementsByTagName("prices").item(0).getTextContent());
                product.setDates(eElement.getElementsByTagName("dates").item(0).getTextContent());
                product.setTitle(eElement.getElementsByTagName("title").item(0).getTextContent());
                product.setBrand(eElement.getElementsByTagName("brand").item(0).getTextContent());
                product.setCategory(eElement.getElementsByTagName("category").item(0).getTextContent());
                product.setUrl(eElement.getElementsByTagName("url").item(0).getTextContent());
                products.add(product);
            }
        }
        return  products;
    }
}
