package pl.vida.code.poc.domain.feed;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.domain.feed.handler.csv.CsvHandler;
import pl.vida.code.poc.domain.feed.handler.ExtensionHandler;
import pl.vida.code.poc.domain.feed.handler.xml.XmlHandler;

@Component
public class FeedConsumer {
    private final CsvHandler csvHandler;
    private final XmlHandler xmlHandler;
    private final FeedContentParsedRepository feedContentParsedRepository;
    private final FeedContentColumnRepository feedContentColumnRepository;

    public FeedConsumer(CsvHandler csvHandler, XmlHandler xmlHandler,
                        FeedContentParsedRepository feedContentParsedRepository,
                        FeedContentColumnRepository feedContentColumnRepository) {
        this.csvHandler = csvHandler;
        this.xmlHandler = xmlHandler;
        this.feedContentParsedRepository = feedContentParsedRepository;
        this.feedContentColumnRepository = feedContentColumnRepository;
    }

    @JmsListener(destination = "feed", containerFactory = "myFactory")
    public void processFeed(Feed feed) {
        FeedContentParsed feedContentParsed = findExtensionHandler(feed.getExtension()).toFeed(feed.getContent(), feed.getOriginalFilename(), feed.getId());
        feedContentColumnRepository.saveAll(feedContentParsed.getFeedContentRecords());
        feedContentParsedRepository.save(feedContentParsed);
    }

    private ExtensionHandler findExtensionHandler(Extension extension) {
        switch (extension) {
            case CSV:
                return csvHandler;
            case XML:
                return xmlHandler;
            case UNKNOWN:
            default:
                return null;
        }
    }

}
