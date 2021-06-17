package pl.vida.code.poc.api.request;

import java.util.List;

public class CustomizeFeedOverviewRequest {
    private String feedId;
    private List<CustomizeFeedColumnRequest> customizeFeedColumnRequests;

    public CustomizeFeedOverviewRequest() {
    }

    public CustomizeFeedOverviewRequest(String feedId, List<CustomizeFeedColumnRequest> customizeFeedColumnRequests) {
        this.feedId = feedId;
        this.customizeFeedColumnRequests = customizeFeedColumnRequests;
    }

    public List<CustomizeFeedColumnRequest> getCustomizeFeedColumnRequests() {
        return customizeFeedColumnRequests;
    }

    public String getFeedId() {
        return feedId;
    }
}

