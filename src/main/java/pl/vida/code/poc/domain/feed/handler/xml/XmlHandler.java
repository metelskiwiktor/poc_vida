package pl.vida.code.poc.domain.feed.handler.xml;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import pl.vida.code.poc.domain.feed.FeedContentColumn;
import pl.vida.code.poc.domain.feed.FeedContentParsed;
import pl.vida.code.poc.domain.feed.handler.ExtensionHandler;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class XmlHandler implements ExtensionHandler {
    private int nextKey = 1;

    @Override
    public FeedContentParsed toFeed(String fileContent, String originalFilename, String feedId) {
        try {
            System.out.println("Starting xml, parsing " + originalFilename + ", length: " + fileContent.length());
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new InputSource(new StringReader(fileContent)));
            doc.getDocumentElement().normalize();
            XmlData xmlData = XmlData.create(feedId, doc);
            return toFeedContentParsed(xmlData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private FeedContentParsed toFeedContentParsed(XmlData xmlData) {
        List<FeedContentColumn> columns = new ArrayList<>();
        xmlData.getNestedColumns().forEach((integer, stringListMap) -> {
            stringListMap.forEach((columnName, rowIndexAndValue) -> {
                FeedContentColumn record = getFeedContentRecordByColumnName(columns,
                        columnName.concat(String.valueOf(nextKey)));
                rowIndexAndValue.forEach(integerStringMap -> {
                    for(Integer key: integerStringMap.keySet()) {
                        record.addContent(key, integerStringMap.get(key));
                    }
                });
                columns.add(record);
            });
            nextKey++;
        });
        return new FeedContentParsed(xmlData.getFeedId(), columns);
    }

    private FeedContentColumn getFeedContentRecordByColumnName(List<FeedContentColumn> records, String columnName) {
        return records.stream()
                .filter(feedContentRecord -> feedContentRecord.getColumn().equals(columnName))
                .findFirst()
                .orElse(new FeedContentColumn(columnName));
    }
}
