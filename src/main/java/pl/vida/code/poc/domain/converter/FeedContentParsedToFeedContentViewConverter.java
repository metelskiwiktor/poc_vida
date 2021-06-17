package pl.vida.code.poc.domain.converter;

import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.api.response.FeedContentDataView;
import pl.vida.code.poc.api.response.FeedContentRecordView;
import pl.vida.code.poc.api.response.FeedContentView;
import pl.vida.code.poc.domain.feed.FeedColumn;
import pl.vida.code.poc.domain.feed.FeedContentParsed;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FeedContentParsedToFeedContentViewConverter implements Converter<FeedContentParsed, FeedContentView> {
    private final ConversionService conversionService;

    @Lazy
    public FeedContentParsedToFeedContentViewConverter(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public FeedContentView convert(FeedContentParsed feedContentParsed) {
        List<FeedContentRecordView> feedContentRecordViews = feedContentParsed.getFeedContentRecords().stream()
                .map(feedContentRecord -> conversionService.convert(feedContentRecord, FeedContentRecordView.class))
                .collect(Collectors.toList());

        return new FeedContentView(
                feedContentParsed.getFeedId(),
                new FeedContentDataView(feedContentRecordViews),
                Arrays.asList(FeedColumn.values())
        );
    }
}
