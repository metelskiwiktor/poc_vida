package pl.vida.code.poc.api.response;

import pl.vida.code.poc.domain.feed.FeedColumn;

import java.util.List;

public class FeedContentView {
    private final String feedId;
    private final FeedContentDataView data;
    private final List<FeedColumn> requiredColumns;

    public FeedContentView(String feedId, FeedContentDataView data, List<FeedColumn> requiredColumns) {
        this.feedId = feedId;
        this.data = data;
        this.requiredColumns = requiredColumns;
    }

    public String getFeedId() {
        return feedId;
    }

    public FeedContentDataView getData() {
        return data;
    }

    public List<FeedColumn> getRequiredColumns() {
        return requiredColumns;
    }
}
