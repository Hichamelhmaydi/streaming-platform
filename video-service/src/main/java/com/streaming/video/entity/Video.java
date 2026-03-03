package com.streaming.video.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "videos")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String thumbnailUrl;
    private String trailerUrl;
    private Integer duration;
    private Integer releaseYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VideoCategory category;

    private Double rating;
    private String director;

    @Column(columnDefinition = "TEXT")
    private String cast;

    public enum VideoType { FILM, SERIE }
    public enum VideoCategory { ACTION, COMEDIE, DRAME, SCIENCE_FICTION, THRILLER, HORREUR }
}
