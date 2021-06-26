package pl.vida.code.poc.domain.feed.handler.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class XmlData {
    private final String feedId;
    private final Map<Integer, Map<String, List<String>>> nestedColumns;

    private XmlData(String feedId) {
        this.feedId = feedId;
        this.nestedColumns = new HashMap<>();
    }

    public static XmlData create(String feedId, Document document){
        XmlData xmlData = new XmlData(feedId);
        xmlData.parseDocument(document);
        return xmlData;
    }

    private void parseDocument(Document document) {
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
        String whitespaces = " ".repeat(spaces);
        for (int i = 0; i < nodesLength; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().startsWith("#")) {
                continue;
            }
            System.out.printf("%s(%d) %s%n", whitespaces, spaces, node.getNodeName());
            if (hasNestedNodes(node.getChildNodes())) {
                printNodeColumns(spaces + 1, node.getChildNodes());
            } else {
//                System.out.println("poczatek else");
//                System.out.println(node.getChildNodes().item(0).getNodeValue());
//                System.out.println("koniec else");
                Map<String, List<String>> stringListMap;
                if (!nestedColumns.containsKey(spaces)) {
                    nestedColumns.put(spaces, new HashMap<>());
                }
                stringListMap = nestedColumns.get(spaces);
                if(stringListMap.containsKey(node.getNodeName())) {
                    List<String> strings = stringListMap.get(node.getNodeName());
                    strings.add(node.getChildNodes().item(0).getNodeValue());
                } else {
                    stringListMap.put(node.getNodeName(), new ArrayList<>(Arrays.asList(node.getChildNodes().item(0).getNodeValue())));
                }
            }
        }
    }
    //nodes without # at start
    private boolean hasNestedNodes(NodeList nodeList) {
        int nodesLength = nodeList.getLength();
        int nestedNodes = 0;
        for (int i = 0; i < nodesLength; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().startsWith("#")) {
                continue;
            }
            nestedNodes++;
        }
        return nestedNodes > 0;
    }

    @Override
    public String toString() {
        return "XmlData{" +
                "feedId='" + feedId + '\'' +
                ", nestedColumns=" + nestedColumns +
                '}';
    }
}
