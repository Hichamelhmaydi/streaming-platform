package com.streaming.video.mapper;

import com.streaming.video.dto.VideoRequest;
import com.streaming.video.dto.VideoResponse;
import com.streaming.video.entity.Video;
import org.springframework.stereotype.Component;

@Component
public class VideoMapper {

    public Video toEntity(VideoRequest request) {
        return Video.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .thumbnailUrl(request.getThumbnailUrl())
                .trailerUrl(request.getTrailerUrl())
                .duration(request.getDuration())
                .releaseYear(request.getReleaseYear())
                .type(request.getType())
                .category(request.getCategory())
                .rating(request.getRating())
                .director(request.getDirector())
                .cast(request.getCast())
                .build();
    }

    public VideoResponse toResponse(Video video) {
        return VideoResponse.builder()
                .id(video.getId())
                .title(video.getTitle())
                .description(video.getDescription())
                .thumbnailUrl(video.getThumbnailUrl())
                .trailerUrl(video.getTrailerUrl())
                .duration(video.getDuration())
                .releaseYear(video.getReleaseYear())
                .type(video.getType())
                .category(video.getCategory())
                .rating(video.getRating())
                .director(video.getDirector())
                .cast(video.getCast())
                .build();
    }

    public void updateEntity(Video video, VideoRequest request) {
        video.setTitle(request.getTitle());
        video.setDescription(request.getDescription());
        video.setThumbnailUrl(request.getThumbnailUrl());
        video.setTrailerUrl(request.getTrailerUrl());
        video.setDuration(request.getDuration());
        video.setReleaseYear(request.getReleaseYear());
        video.setType(request.getType());
        video.setCategory(request.getCategory());
        video.setRating(request.getRating());
        video.setDirector(request.getDirector());
        video.setCast(request.getCast());
    }
}
