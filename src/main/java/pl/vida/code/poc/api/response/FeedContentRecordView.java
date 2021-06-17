package pl.vida.code.poc.api.response;

import java.util.List;

public class FeedContentRecordView {
    private final String columnId;
    private final String column;
    private final List<String> contents;

    public FeedContentRecordView(String columnId, String column, List<String> contents) {
        this.columnId = columnId;
        this.column = column;
        this.contents = contents;
    }

    public String getColumnId() {
        return columnId;
    }

    public String getColumn() {
        return column;
    }

    public List<String> getContents() {
        return contents;
    }
}
