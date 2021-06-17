package pl.vida.code.poc.domain.feed;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedContentRecordRepository extends JpaRepository<FeedContentRecord, String> {
    FeedContentRecord getByColumnId(String columnId);
}
