package com.wddmg;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author duym
 * @version $ Id: XMLTest, v 0.1 2023/04/11 9:26 duym Exp $
 */
public class XMLTest {
    public static void main(String[] args) throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document document = reader.read("C:\\Users\\duym\\Desktop\\StandaloneStudy_PsState.xml");
        Element rootElem = document.getRootElement();
        Element ObjectsElem = rootElem.element("Objects");
        List<Element> objectList = ObjectsElem.elements();
        for (int i = 0; i < objectList.size(); i++) {
            Element robcadStudyInfoElem = objectList.get(i);
            Element nodeInfoElem = robcadStudyInfoElem.element("NodeInfo");
            if(nodeInfoElem != null){
                Element idElem = nodeInfoElem.element("Id");
                if(idElem != null){
                    idElem.setText("");
                }
            }
        }
        XMLWriter writer = new XMLWriter(new FileWriter("C:\\Users\\duym\\Desktop\\StandaloneStudy_EmsState.xml"));
        writer.write(document);
        writer.close();
    }
}
