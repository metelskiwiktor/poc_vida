package pl.vida.code.poc.domain.feed;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class FeedContentRecord {
    @Id
    private String columnId;
    private String column;
    @ElementCollection
    @Column(length = 30000)
    private List<String> contents;

    public FeedContentRecord() {
    }

    public FeedContentRecord(String column) {
        this.column = column;
        this.contents = new ArrayList<>();
        this.columnId = UUID.randomUUID().toString();
    }

    public String getColumn() {
        return column;
    }

    public List<String> getContents() {
        return contents;
    }

    public void addContent(String content) {
        contents.add(content);
    }

    public String getColumnId() {
        return columnId;
    }


    @Override
    public String toString() {
        return "FeedContentRecord{" +
                "columnId='" + columnId + '\'' +
                ", column='" + column + '\'' +
                ", contents=" + contents +
                '}';
    }
}
