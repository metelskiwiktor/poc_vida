package pl.vida.code.poc.domain.feed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedContentColumnRepository extends JpaRepository<FeedContentColumn, String> {
    FeedContentColumn getByColumnId(String columnId);
}
