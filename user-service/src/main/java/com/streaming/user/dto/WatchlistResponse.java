package com.streaming.user.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WatchlistResponse {
    private Long id;
    private Long userId;
    private Long videoId;
    private LocalDateTime addedAt;
    private Object videoDetails;
}
