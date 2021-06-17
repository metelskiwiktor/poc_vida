package pl.vida.code.poc.domain.feed;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedContentParsedRepository extends JpaRepository<FeedContentParsed, String> {
    Optional<FeedContentParsed> findByFeedId(String feedId);
}
