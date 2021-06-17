package pl.vida.code.poc.api.response;

import java.util.List;

public class FeedContentDataView {
    private final List<FeedContentRecordView> feedContentRecordViews;

    public FeedContentDataView(List<FeedContentRecordView> feedContentRecordViews) {
        this.feedContentRecordViews = feedContentRecordViews;
    }

    public List<FeedContentRecordView> getFeedContentRecordViews() {
        return feedContentRecordViews;
    }
}
