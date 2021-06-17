package pl.vida.code.poc.domain.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.vida.code.poc.api.response.FeedEventView;
import pl.vida.code.poc.domain.feed.Feed;

@Component
public class FeedToFeedEventViewConverter implements Converter<Feed, FeedEventView> {
    @Override
    public FeedEventView convert(Feed feed) {
        return new FeedEventView(
                feed.getId(),
                feed.getEventStatus()
        );
    }
}
