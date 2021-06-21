package pl.vida.code.poc.domain.feed;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Feed {
    @Id
    @GeneratedValue(generator = "my-uuid")
    @GenericGenerator(name = "my-uuid", strategy = "uuid")
    private String id;
    @Column(length = 20000000)
    private String content;
    private LocalDate createdAt;
    private String originalFilename;
    private Extension extension;
    private EventStatus eventStatus;

    public Feed() {
    }

    public Feed(String content, String originalFilename, Extension extension, EventStatus eventStatus) {
        this.content = content;
        this.eventStatus = eventStatus;
        this.createdAt = LocalDate.now();
        this.originalFilename = originalFilename;
        this.extension = extension;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    public Extension getExtension() {
        return extension;
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id='" + id + '\'' +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", originalFilename='" + originalFilename + '\'' +
                ", extension=" + extension +
                ", eventStatus=" + eventStatus +
                '}';
    }
}
