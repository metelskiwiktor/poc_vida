package pl.vida.code.poc.domain.feed.handler.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.*;

public class XmlData {
    private final String feedId;
    private final Map<Integer, Map<String, List<Map<Integer, String>>>> nestedColumns;
    private int actualRows;

    private XmlData(String feedId) {
        this.feedId = feedId;
        this.nestedColumns = new HashMap<>();
    }

    public String getFeedId() {
        return feedId;
    }

    public Map<Integer, Map<String, List<Map<Integer, String>>>> getNestedColumns() {
        return nestedColumns;
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
        for (int i = 0; i < nodesLength; i++) {
            Node node = nodeList.item(i);
            if (node.getNodeName().startsWith("#")) {
                continue;
            }
            if (hasNestedNodes(node.getChildNodes())) {
                printNodeColumns(spaces + 1, node.getChildNodes());
            } else {
                if (!nestedColumns.containsKey(spaces)) {
                    nestedColumns.put(spaces, new HashMap<>());
                }
                Map<String, List<Map<Integer, String>>> stringListMap;
                stringListMap = nestedColumns.get(spaces);
                Map<Integer, String> record = new HashMap<>();
                if(stringListMap.containsKey(node.getNodeName())) {
                    List<Map<Integer, String>> strings = stringListMap.get(node.getNodeName());
                    actualRows = Math.max(strings.size(), actualRows);
                    record.put(actualRows, node.getChildNodes().item(0).getNodeValue());
                    strings.add(record);
                } else {
                    record.put(0, node.getChildNodes().item(0).getNodeValue());
                    stringListMap.put(node.getNodeName(),new ArrayList<>(Collections.singletonList(record)));
                }
            }
        }
        actualRows = 0;
    }

    public static XmlData create(String feedId, Document document){
        XmlData xmlData = new XmlData(feedId);
        xmlData.parseDocument(document);
        return xmlData;
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
}
