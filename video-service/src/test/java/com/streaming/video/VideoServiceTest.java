package com.streaming.video;

import com.streaming.video.dto.VideoRequest;
import com.streaming.video.dto.VideoResponse;
import com.streaming.video.entity.Video;
import com.streaming.video.exception.VideoNotFoundException;
import com.streaming.video.mapper.VideoMapper;
import com.streaming.video.repository.VideoRepository;
import com.streaming.video.service.VideoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

    @Mock
    private VideoRepository videoRepository;

    @Mock
    private VideoMapper videoMapper;

    @InjectMocks
    private VideoServiceImpl videoService;

    private Video video;
    private VideoRequest request;
    private VideoResponse response;

    @BeforeEach
    void setUp() {
        video = Video.builder()
                .id(1L)
                .title("Inception")
                .description("A mind-bending thriller")
                .type(Video.VideoType.FILM)
                .category(Video.VideoCategory.SCIENCE_FICTION)
                .director("Christopher Nolan")
                .rating(8.8)
                .releaseYear(2010)
                .duration(148)
                .build();

        request = VideoRequest.builder()
                .title("Inception")
                .description("A mind-bending thriller")
                .type(Video.VideoType.FILM)
                .category(Video.VideoCategory.SCIENCE_FICTION)
                .director("Christopher Nolan")
                .rating(8.8)
                .releaseYear(2010)
                .duration(148)
                .build();

        response = VideoResponse.builder()
                .id(1L)
                .title("Inception")
                .type(Video.VideoType.FILM)
                .category(Video.VideoCategory.SCIENCE_FICTION)
                .build();
    }

    @Test
    void createVideo_ShouldReturnCreatedVideo() {
        when(videoMapper.toEntity(request)).thenReturn(video);
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toResponse(video)).thenReturn(response);

        VideoResponse result = videoService.createVideo(request);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Inception");
        verify(videoRepository, times(1)).save(video);
    }

    @Test
    void getVideoById_WhenExists_ShouldReturnVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoMapper.toResponse(video)).thenReturn(response);

        VideoResponse result = videoService.getVideoById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
    }

    @Test
    void getVideoById_WhenNotExists_ShouldThrowException() {
        when(videoRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> videoService.getVideoById(999L))
                .isInstanceOf(VideoNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void getAllVideos_ShouldReturnList() {
        when(videoRepository.findAll()).thenReturn(Arrays.asList(video));
        when(videoMapper.toResponse(video)).thenReturn(response);

        List<VideoResponse> results = videoService.getAllVideos();

        assertThat(results).hasSize(1);
    }

    @Test
    void deleteVideo_WhenExists_ShouldDelete() {
        when(videoRepository.existsById(1L)).thenReturn(true);

        videoService.deleteVideo(1L);

        verify(videoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteVideo_WhenNotExists_ShouldThrowException() {
        when(videoRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> videoService.deleteVideo(999L))
                .isInstanceOf(VideoNotFoundException.class);
    }

    @Test
    void updateVideo_ShouldReturnUpdatedVideo() {
        when(videoRepository.findById(1L)).thenReturn(Optional.of(video));
        when(videoRepository.save(video)).thenReturn(video);
        when(videoMapper.toResponse(video)).thenReturn(response);

        VideoResponse result = videoService.updateVideo(1L, request);

        assertThat(result).isNotNull();
        verify(videoMapper, times(1)).updateEntity(video, request);
    }
}
