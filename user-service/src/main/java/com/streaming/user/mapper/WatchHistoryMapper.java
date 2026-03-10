package com.streaming.user.mapper;

import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.entity.WatchHistory;
import org.springframework.stereotype.Component;

@Component
public class WatchHistoryMapper {

    public WatchHistoryResponse toResponse(WatchHistory entity) {
        return WatchHistoryResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .videoId(entity.getVideoId())
                .watchedAt(entity.getWatchedAt())
                .progressTime(entity.getProgressTime())
                .completed(entity.getCompleted())
                .videoDetails(null)
                .build();
    }
}