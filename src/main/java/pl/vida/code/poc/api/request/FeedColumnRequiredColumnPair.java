package pl.vida.code.poc.api.request;

import pl.vida.code.poc.domain.feed.FeedColumn;

public class FeedColumnRequiredColumnPair {
    private String columnId;
    private FeedColumn requiredColumn;

    public FeedColumnRequiredColumnPair() {
    }

    public FeedColumnRequiredColumnPair(String columnId, FeedColumn requiredColumn) {
        this.columnId = columnId;
        this.requiredColumn = requiredColumn;
    }

    public String getColumnId() {
        return columnId;
    }

    public FeedColumn getRequiredColumn() {
        return requiredColumn;
    }
}
