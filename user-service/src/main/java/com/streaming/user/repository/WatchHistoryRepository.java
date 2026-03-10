package com.streaming.user.repository;

import com.streaming.user.entity.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {

    List<WatchHistory> findByUserIdOrderByWatchedAtDesc(Long userId);

    void deleteByUserId(Long userId);

    @Query("SELECT COALESCE(SUM(h.progressTime), 0) FROM WatchHistory h WHERE h.user.id = :userId")
    Long sumProgressTimeByUserId(Long userId);
}