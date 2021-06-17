package pl.vida.code.poc.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.api.response.FeedContentRecordView;
import pl.vida.code.poc.domain.feed.FeedContentRecord;

@Component
public class FeedContentRecordToFeedContentRecordViewConverter implements Converter<FeedContentRecord, FeedContentRecordView> {
    @Override
    public FeedContentRecordView convert(FeedContentRecord feedContentRecord) {
        return new FeedContentRecordView(
                feedContentRecord.getColumnId(),
                feedContentRecord.getColumn(),
                feedContentRecord.getContents()
        );
    }
}
