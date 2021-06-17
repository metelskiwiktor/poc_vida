package pl.vida.code.poc.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.vida.code.poc.api.request.CompleteFeedRequest;
import pl.vida.code.poc.api.response.FeedContentView;
import pl.vida.code.poc.api.response.FeedEventView;
import pl.vida.code.poc.domain.exception.DomainException;
import pl.vida.code.poc.domain.feed.FeedService;
import pl.vida.code.poc.domain.feed.FeedContentParsed;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class FeedController {
    private final FeedService feedService;

    public FeedController(FeedService feedService) {
        this.feedService = feedService;
    }

    @PostMapping(value = "/feed", consumes = {"multipart/form-data"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public FeedEventView importFile(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return feedService.processFile(multipartFile);
    }

    @GetMapping("/{feedId}/process-information")
    public FeedEventView getProcessInformation(@PathVariable String feedId) {
        return feedService.getProcessInformation(feedId);
    }

    @GetMapping("/{feedId}/content-parsed")
    public FeedContentView getFeedContentParsed(@PathVariable String feedId) {
        return feedService.getFeedContentParsed(feedId);
    }

    @PostMapping("/{feedId}/complete-feed")
    public void completeFeed(@PathVariable String feedId, @RequestBody CompleteFeedRequest completeFeedRequest) {
        feedService.completeFeed(feedId, completeFeedRequest);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<Map<String, Object>> handleIOException(IOException e) {
        e.printStackTrace();
        return new ResponseEntity<>(createErrorBody("An error occurred when parsing file"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<Map<String, Object>> handleDomainException(DomainException e) {
        e.printStackTrace();
        return new ResponseEntity<>(createErrorBody(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    private Map<String, Object> createErrorBody(String message) {
        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("message", message);
        return errorBody;
    }
}
