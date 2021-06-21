package pl.vida.code.poc.domain.feed.handler;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import pl.vida.code.poc.domain.feed.FeedContentParsed;

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
            printColumns(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void printColumns(Document document) {
        boolean firstChild = true;
        while (true) {
            Node node;
            if (firstChild) {
                node = document.getFirstChild();
            } else {
                try {
                    node = document.getNextSibling();
                } catch (Exception e) {
                    break;
                }
            }
            if (node == null) {
                break;
            }
            printNodeColumns(0, node.getChildNodes());
            firstChild = false;
        }
    }

    private void printNodeColumns(int spaces, NodeList nodeList) {
        int nodesLength = nodeList.getLength();
        String whitespaces = " ".repeat(Math.max(0, spaces));
        for (int i = 0; i < nodesLength; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().startsWith("#")) {
                continue;
            }
            System.out.printf("%s(%d) %s%n", whitespaces, spaces, node.getNodeName());
            if (node.getChildNodes().getLength() > 0) {
                printNodeColumns(spaces + 1, node.getChildNodes());
            }
        }
    }
}
