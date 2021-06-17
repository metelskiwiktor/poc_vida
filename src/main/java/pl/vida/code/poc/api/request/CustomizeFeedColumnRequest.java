package pl.vida.code.poc.api.request;

import pl.vida.code.poc.domain.feed.FeedColumn;

public class CustomizeFeedColumnRequest {
    private String columnId;
    private FeedColumn feedColumn;

    public CustomizeFeedColumnRequest() {
    }

    public CustomizeFeedColumnRequest(String columnId, FeedColumn feedColumn) {
        this.columnId = columnId;
        this.feedColumn = feedColumn;
    }

    public String getColumnId() {
        return columnId;
    }

    public FeedColumn getFeedColumn() {
        return feedColumn;
    }
}
