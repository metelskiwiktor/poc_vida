package pl.vida.code.poc.api.request;

import java.util.List;

public class CompleteFeedRequest {
    private List<FeedColumnRequiredColumnPair> columnPairs;

    public CompleteFeedRequest() {
    }

    public CompleteFeedRequest(List<FeedColumnRequiredColumnPair> columnPairs) {
        this.columnPairs = columnPairs;
    }

    public List<FeedColumnRequiredColumnPair> getColumnPairs() {
        return columnPairs;
    }
}
