package com.streaming.user.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WatchHistoryRequest {

    @NotNull(message = "Video ID is required")
    private Long videoId;

    private Integer progressTime;
    private Boolean completed;
}
