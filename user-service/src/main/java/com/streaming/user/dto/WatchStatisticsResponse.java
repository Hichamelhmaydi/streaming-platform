package com.streaming.user.dto;

import lombok.*;
import java.util.Map;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WatchStatisticsResponse {
    private Long userId;
    private Long totalVideosWatched;
    private Long completedVideos;
    private Long inProgressVideos;
    private Integer totalWatchTimeSeconds;
    private Map<String, Long> watchCountByStatus;
}
