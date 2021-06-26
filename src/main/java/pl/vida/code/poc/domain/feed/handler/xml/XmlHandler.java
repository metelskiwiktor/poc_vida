package pl.vida.code.poc.domain.feed.handler.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import pl.vida.code.poc.domain.feed.FeedContentParsed;
import pl.vida.code.poc.domain.feed.handler.ExtensionHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

@Component
public class XmlHandler implements ExtensionHandler {
    @Override
    public FeedContentParsed toFeed(String fileContent, String originalFilename, String feedId) {
        try {
            System.out.println("Starting xml, parsing " + originalFilename + ", length: " + fileContent.length() + ", content: ");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(fileContent)));
            doc.getDocumentElement().normalize();
            XmlData xmlData = XmlData.create(feedId, doc);
            System.out.println(xmlData);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
