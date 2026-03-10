package com.streaming.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class WatchlistResponse {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
    private Object videoDetails; // null pour l'instant, enrichi par le frontend
}
