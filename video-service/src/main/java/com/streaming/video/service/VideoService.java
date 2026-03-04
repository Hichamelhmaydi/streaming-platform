package com.streaming.video.service;

import com.streaming.video.dto.VideoRequest;
import com.streaming.video.dto.VideoResponse;
import com.streaming.video.entity.Video.VideoCategory;
import com.streaming.video.entity.Video.VideoType;

import java.util.List;

public interface VideoService {
    VideoResponse createVideo(VideoRequest request);
    VideoResponse getVideoById(Long id);
    List<VideoResponse> getAllVideos();
    VideoResponse updateVideo(Long id, VideoRequest request);
    void deleteVideo(Long id);
    List<VideoResponse> getVideosByType(VideoType type);
    List<VideoResponse> getVideosByCategory(VideoCategory category);
    List<VideoResponse> searchByTitle(String title);
    List<VideoResponse> getVideosByTypeAndCategory(VideoType type, VideoCategory category);
}
