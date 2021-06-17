package pl.vida.code.poc.domain.feed.handler;

import org.springframework.stereotype.Component;
import pl.vida.code.poc.domain.feed.FeedContentParsed;

@Component
public class XmlHandler implements ExtensionHandler {
    @Override
    public FeedContentParsed toFeed(String fileContent, String originalFilename, String feedId) {
        System.out.println("Starting xml, parsing " + originalFilename + ", length: " + fileContent.length() +  ", content: ");
//        System.out.println(fileContent);
        return null;
    }
}
