package pl.vida.code.poc.domain.feed.handler;

import pl.vida.code.poc.domain.feed.FeedContentParsed;

public interface ExtensionHandler {
    FeedContentParsed toFeed(String fileContent, String originalFilename, String feedId);
}
