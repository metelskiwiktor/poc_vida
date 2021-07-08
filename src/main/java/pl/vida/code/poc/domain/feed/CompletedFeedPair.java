package pl.vida.code.poc.domain.feed;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class CompletedFeedPair {
    @Id
    @GeneratedValue(generator = "my-uuid")
    @GenericGenerator(name = "my-uuid", strategy = "uuid")
    private String id;
    @Enumerated(EnumType.STRING)
    private FeedColumn feedColumn;
    @ManyToOne
    private FeedContentColumn feedContentRecords;

    public CompletedFeedPair() {
    }

    public CompletedFeedPair(FeedColumn feedColumn, FeedContentColumn feedContentRecords) {
        this.feedColumn = feedColumn;
        this.feedContentRecords = feedContentRecords;
    }

    public String getId() {
        return id;
    }

    public FeedColumn getFeedColumn() {
        return feedColumn;
    }

    public FeedContentColumn getFeedContentRecords() {
        return feedContentRecords;
    }
}
