package pl.vida.code.poc.domain.feed;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class FeedContentParsed {
    @Id
    @GeneratedValue(generator = "my-uuid")
    @GenericGenerator(name = "my-uuid", strategy = "uuid")
    private String id;
    @Column(unique = true)
    private String feedId;
    @OneToMany
    private List<FeedContentRecord> feedContentRecords;

    public FeedContentParsed() {
    }

    public FeedContentParsed(String feedId, List<FeedContentRecord> feedContentRecords) {
        this.feedId = feedId;
        this.feedContentRecords = feedContentRecords;
    }

    public String getId() {
        return id;
    }

    public String getFeedId() {
        return feedId;
    }

    public List<FeedContentRecord> getFeedContentRecords() {
        return feedContentRecords;
    }

    @Override
    public String toString() {
        return "FeedContentParsed{" +
                "id='" + id + '\'' +
                ", feedId='" + feedId + '\'' +
                ", feedContentRecords=" + feedContentRecords +
                '}';
    }
}
