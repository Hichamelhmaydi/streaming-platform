package com.streaming.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WatchHistoryResponse {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime watchedAt;
    private Integer progressTime;
    private Boolean completed;
    private Object videoDetails;

}