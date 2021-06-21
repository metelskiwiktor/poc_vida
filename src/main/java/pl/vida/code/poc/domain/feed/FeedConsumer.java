package pl.vida.code.poc.domain.feed;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.domain.feed.handler.CsvHandler;
import pl.vida.code.poc.domain.feed.handler.ExtensionHandler;
import pl.vida.code.poc.domain.feed.handler.XmlHandler;

@Component
public class FeedConsumer {
    private final CsvHandler csvHandler;
    private final XmlHandler xmlHandler;
    private final FeedContentParsedRepository feedContentParsedRepository;
    private final FeedContentRecordRepository feedContentRecordRepository;

    public FeedConsumer(CsvHandler csvHandler, XmlHandler xmlHandler,
                        FeedContentParsedRepository feedContentParsedRepository,
                        FeedContentRecordRepository feedContentRecordRepository) {
        this.csvHandler = csvHandler;
        this.xmlHandler = xmlHandler;
        this.feedContentParsedRepository = feedContentParsedRepository;
        this.feedContentRecordRepository = feedContentRecordRepository;
    }

    @JmsListener(destination = "feed", containerFactory = "myFactory")
    public void processFeed(Feed feed) {
        FeedContentParsed feedContentParsed = findExtensionHandler(feed.getExtension()).toFeed(feed.getContent(), feed.getOriginalFilename(), feed.getId());
//        feedContentRecordRepository.saveAll(feedContentParsed.getFeedContentRecords());
//        feedContentParsedRepository.save(feedContentParsed);
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
