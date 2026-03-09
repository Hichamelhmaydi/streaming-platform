package com.streaming.user.mapper;

import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.entity.WatchHistory;
import org.springframework.stereotype.Component;

@Component
public class WatchHistoryMapper {

    public WatchHistoryResponse toResponse(WatchHistory history) {
        return WatchHistoryResponse.builder()
                .id(history.getId())
                .userId(history.getUser().getId())
                .videoId(history.getVideoId())
                .watchedAt(history.getWatchedAt())
                .progressTime(history.getProgressTime())
                .completed(history.getCompleted())
                .build();
    }

    public WatchHistoryResponse toResponseWithVideo(WatchHistory history, Object videoDetails) {
        WatchHistoryResponse response = toResponse(history);
        response.setVideoDetails(videoDetails);
        return response;
    }
}
