package pl.vida.code.poc.domain.feed;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class CompletedFeed {
    @Id
    @GeneratedValue(generator = "my-uuid")
    @GenericGenerator(name = "my-uuid", strategy = "uuid")
    private String id;
    private String feedId;
    @OneToMany
    private List<CompletedFeedPair> completedFeedPairList;

    public CompletedFeed() {
    }

    public CompletedFeed(String feedId, List<CompletedFeedPair> completedFeedPairList) {
        this.feedId = feedId;
        this.completedFeedPairList = completedFeedPairList;
    }

    public String getId() {
        return id;
    }

    public String getFeedId() {
        return feedId;
    }

    public List<CompletedFeedPair> getCompleteFeedPairList() {
        return completedFeedPairList;
    }
}
