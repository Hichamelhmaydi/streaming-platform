package com.streaming.video.controller;

import com.streaming.video.dto.VideoRequest;
import com.streaming.video.dto.VideoResponse;
import com.streaming.video.entity.Video.VideoCategory;
import com.streaming.video.entity.Video.VideoType;
import com.streaming.video.service.VideoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class VideoController {

    private final VideoService videoService;

    @PostMapping
    public ResponseEntity<VideoResponse> createVideo(@Valid @RequestBody VideoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(videoService.createVideo(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VideoResponse> getVideoById(@PathVariable Long id) {
        return ResponseEntity.ok(videoService.getVideoById(id));
    }

    @GetMapping
    public ResponseEntity<List<VideoResponse>> getAllVideos() {
        return ResponseEntity.ok(videoService.getAllVideos());
    }

    @PutMapping("/{id}")
    public ResponseEntity<VideoResponse> updateVideo(@PathVariable Long id,
                                                     @Valid @RequestBody VideoRequest request) {
        return ResponseEntity.ok(videoService.updateVideo(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideo(@PathVariable Long id) {
        videoService.deleteVideo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<VideoResponse>> getByType(@PathVariable VideoType type) {
        return ResponseEntity.ok(videoService.getVideosByType(type));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<VideoResponse>> getByCategory(@PathVariable VideoCategory category) {
        return ResponseEntity.ok(videoService.getVideosByCategory(category));
    }

    @GetMapping("/search")
    public ResponseEntity<List<VideoResponse>> searchByTitle(@RequestParam String title) {
        return ResponseEntity.ok(videoService.searchByTitle(title));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<VideoResponse>> filterByTypeAndCategory(
            @RequestParam VideoType type,
            @RequestParam VideoCategory category) {
        return ResponseEntity.ok(videoService.getVideosByTypeAndCategory(type, category));
    }
}
