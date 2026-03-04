package com.streaming.video.dto;

import com.streaming.video.entity.Video.VideoCategory;
import com.streaming.video.entity.Video.VideoType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private String thumbnailUrl;
    private String trailerUrl;

    @Min(value = 1, message = "Duration must be positive")
    private Integer duration;

    @Min(1888) @Max(2100)
    private Integer releaseYear;

    @NotNull(message = "Type is required")
    private VideoType type;

    @NotNull(message = "Category is required")
    private VideoCategory category;

    @DecimalMin("0.0") @DecimalMax("10.0")
    private Double rating;

    private String director;
    private String cast;
}
