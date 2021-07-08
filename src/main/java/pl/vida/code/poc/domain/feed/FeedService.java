package pl.vida.code.poc.domain.feed;

import org.springframework.core.convert.ConversionService;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.vida.code.poc.api.request.CompleteFeedRequest;
import pl.vida.code.poc.api.request.FeedColumnRequiredColumnPair;
import pl.vida.code.poc.api.response.FeedContentView;
import pl.vida.code.poc.api.response.FeedEventView;
import pl.vida.code.poc.domain.exception.DomainException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static pl.vida.code.poc.domain.feed.Extension.getExtension;

@Service
public class FeedService {
    private final FeedRepository feedRepository;
    private final ConversionService conversionService;
    private final JmsTemplate jmsTemplate;
    private final FeedContentParsedRepository feedContentParsedRepository;
    private final FeedContentColumnRepository feedContentColumnRepository;
    private final CompletedFeedRepository completedFeedRepository;
    private final CompletedFeedPairRepository completedFeedPairRepository;

    public FeedService(FeedRepository feedRepository, ConversionService conversionService, JmsTemplate jmsTemplate,
                       FeedContentParsedRepository feedContentParsedRepository,
                       FeedContentColumnRepository feedContentColumnRepository,
                       CompletedFeedRepository completedFeedRepository,
                       CompletedFeedPairRepository completedFeedPairRepository) {
        this.feedRepository = feedRepository;
        this.conversionService = conversionService;
        this.jmsTemplate = jmsTemplate;
        this.feedContentParsedRepository = feedContentParsedRepository;
        this.feedContentColumnRepository = feedContentColumnRepository;
        this.completedFeedRepository = completedFeedRepository;
        this.completedFeedPairRepository = completedFeedPairRepository;
    }

    public FeedEventView processFile(MultipartFile multipartFile) throws IOException {
        validateFile(multipartFile);
        final Extension extension = getExtension(multipartFile.getOriginalFilename());
        final String content = new String(multipartFile.getBytes());
        EventStatus eventStatus = extension == Extension.UNKNOWN ? EventStatus.UNRECOGNIZED : EventStatus.IN_PROGRESS;
        Feed feed = new Feed(content, multipartFile.getOriginalFilename(), extension, eventStatus);
        feedRepository.save(feed);
        jmsTemplate.convertAndSend("feed", feed);
        return conversionService.convert(feed, FeedEventView.class);
    }

    public FeedEventView getProcessInformation(String feedId) {
        return conversionService.convert(getFeedById(feedId), FeedEventView.class);
    }

    public FeedContentView getFeedContentParsed(String feedId) {
        return conversionService.convert(getFeedContentParsedByFeedId(feedId), FeedContentView.class);
    }

    public void completeFeed(String feedId, CompleteFeedRequest completeFeedRequest) {
        if(!hasAllUniqueColumns(completeFeedRequest.getColumnPairs())) {
            throw new DomainException("Required columns should be unique");
        }

        List<CompletedFeedPair> completedFeedPairs =
                completedFeedPairRepository.saveAll(map(completeFeedRequest.getColumnPairs()));
        completedFeedRepository.save(new CompletedFeed(feedId, completedFeedPairs));
    }

    private List<CompletedFeedPair> map(List<FeedColumnRequiredColumnPair> columnPairs) {
        return columnPairs.stream()
                .map(feedColumnRequiredColumnPair -> {
                    FeedContentColumn feedContentColumn =
                            feedContentColumnRepository.getByColumnId(feedColumnRequiredColumnPair.getColumnId());
                    return new CompletedFeedPair(
                            feedColumnRequiredColumnPair.getRequiredColumn(),
                            feedContentColumn);
                }).collect(Collectors.toList());
    }

    private boolean hasAllUniqueColumns(List<FeedColumnRequiredColumnPair> columnPairs) {
        List<FeedColumn> feedColumns = new ArrayList<>();
        for(FeedColumnRequiredColumnPair columnPair: columnPairs) {
            if(feedColumns.contains(columnPair.getRequiredColumn())) {
                return false;
            } else {
                feedColumns.add(columnPair.getRequiredColumn());
            }
        }

        return Arrays.asList(FeedColumn.values()).containsAll(feedColumns);
    }

    private void validateFile(MultipartFile multipartFile) {
        try {
            if (Objects.isNull(multipartFile) || multipartFile.isEmpty() || multipartFile.getBytes().length == 0) {
                throw new DomainException("File isn't read properly");
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new DomainException("File isn't read properly");
        }
    }

    private Feed getFeedById(String feedId) {
        return feedRepository.findById(feedId)
                .orElseThrow(() -> new DomainException(String.format("Provided feed id '%s' not found", feedId)));
    }

    private FeedContentParsed getFeedContentParsedByFeedId(String feedId) {
        return feedContentParsedRepository.findByFeedId(feedId)
                .orElseThrow(() -> new DomainException(
                        String.format("Feed content parsed not found by provided id '%s'", feedId)));
    }
}
