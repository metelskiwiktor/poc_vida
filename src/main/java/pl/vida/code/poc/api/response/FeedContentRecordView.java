package pl.vida.code.poc.api.response;

import java.util.List;
import java.util.Map;

public class FeedContentRecordView {
    private final String columnId;
    private final String column;
    private final Map<Integer, String> contents;

    public FeedContentRecordView(String columnId, String column, Map<Integer, String> contents) {
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

    public Map<Integer, String> getContents() {
        return contents;
    }
}
