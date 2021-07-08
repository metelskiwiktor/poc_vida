package pl.vida.code.poc.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.api.response.FeedContentRecordView;
import pl.vida.code.poc.domain.feed.FeedContentColumn;

@Component
public class FeedContentRecordToFeedContentRecordViewConverter implements Converter<FeedContentColumn, FeedContentRecordView> {
    @Override
    public FeedContentRecordView convert(FeedContentColumn feedContentColumn) {
        return new FeedContentRecordView(
                feedContentColumn.getColumnId(),
                feedContentColumn.getColumn(),
                feedContentColumn.getRecordIndexContents()
        );
    }
}
