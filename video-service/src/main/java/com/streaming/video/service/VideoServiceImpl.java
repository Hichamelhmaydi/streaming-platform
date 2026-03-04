package com.streaming.video.service;

import com.streaming.video.dto.VideoRequest;
import com.streaming.video.dto.VideoResponse;
import com.streaming.video.entity.Video;
import com.streaming.video.entity.Video.VideoCategory;
import com.streaming.video.entity.Video.VideoType;
import com.streaming.video.exception.VideoNotFoundException;
import com.streaming.video.mapper.VideoMapper;
import com.streaming.video.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;

    @Override
    public VideoResponse createVideo(VideoRequest request) {
        log.info("Creating video: {}", request.getTitle());
        Video video = videoMapper.toEntity(request);
        Video saved = videoRepository.save(video);
        return videoMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public VideoResponse getVideoById(Long id) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
        return videoMapper.toResponse(video);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> getAllVideos() {
        return videoRepository.findAll().stream()
                .map(videoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public VideoResponse updateVideo(Long id, VideoRequest request) {
        Video video = videoRepository.findById(id)
                .orElseThrow(() -> new VideoNotFoundException(id));
        videoMapper.updateEntity(video, request);
        return videoMapper.toResponse(videoRepository.save(video));
    }

    @Override
    public void deleteVideo(Long id) {
        if (!videoRepository.existsById(id)) {
            throw new VideoNotFoundException(id);
        }
        videoRepository.deleteById(id);
        log.info("Deleted video with id: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> getVideosByType(VideoType type) {
        return videoRepository.findByType(type).stream()
                .map(videoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> getVideosByCategory(VideoCategory category) {
        return videoRepository.findByCategory(category).stream()
                .map(videoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> searchByTitle(String title) {
        return videoRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(videoMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VideoResponse> getVideosByTypeAndCategory(VideoType type, VideoCategory category) {
        return videoRepository.findByTypeAndCategory(type, category).stream()
                .map(videoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
