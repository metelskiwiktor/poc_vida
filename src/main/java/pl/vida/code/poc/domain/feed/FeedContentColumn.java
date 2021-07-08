package pl.vida.code.poc.domain.feed;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

@Entity
public class FeedContentColumn {
    @Id
    private String columnId;
    private String column;
//    @ElementCollection
//    @Column(length = 30000)
//    private List<String> contents;
    @ElementCollection
    @Column(length = 30000)
    private Map<Integer, String> recordIndexContents;

    public FeedContentColumn() {
    }

    public FeedContentColumn(String column) {
        this.column = column;
        this.recordIndexContents = new HashMap<>();
        this.columnId = UUID.randomUUID().toString();
    }

    public String getColumn() {
        return column;
    }

    public Map<Integer, String> getRecordIndexContents() {
        return recordIndexContents;
    }

    public void addContent(Integer index, String content) {
        recordIndexContents.put(index, content);
    }

    public String getColumnId() {
        return columnId;
    }


    @Override
    public String toString() {
        return "FeedContentColumn{" +
                "columnId='" + columnId + '\'' +
                ", column='" + column + '\'' +
                ", recordIndexContents=" + recordIndexContents +
                '}';
    }
}
