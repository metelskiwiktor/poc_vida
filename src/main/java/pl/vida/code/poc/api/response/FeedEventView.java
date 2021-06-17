package pl.vida.code.poc.api.response;

import pl.vida.code.poc.domain.feed.EventStatus;

public class FeedEventView {
    private final String id;
    private final EventStatus feedEvent;

    public FeedEventView(String id, EventStatus feedEvent) {
        this.id = id;
        this.feedEvent = feedEvent;
    }

    public String getId() {
        return id;
    }

    public EventStatus getFeedEvent() {
        return feedEvent;
    }
}
