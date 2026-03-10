package com.streaming.user.dto;

import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VideoResponse {
    private Long id;
    private String title;
    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;
    private String type;
    private String category;
    private Double rating;
    private String director;
    private String cast;
    private String description;
}