package com.streaming.user.repository;

import com.streaming.user.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    List<WatchHistory> findByUserIdOrderByWatchedAtDesc(Long userId);
    Optional<WatchHistory> findByUserIdAndVideoId(Long userId, Long videoId);
    long countByUserId(Long userId);
    long countByUserIdAndCompleted(Long userId, Boolean completed);

    @Query("SELECT COALESCE(SUM(wh.progressTime), 0) FROM WatchHistory wh WHERE wh.user.id = :userId")
    Integer sumProgressTimeByUserId(Long userId);
}
