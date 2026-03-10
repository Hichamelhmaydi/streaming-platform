package com.streaming.user.feign;

import com.streaming.user.dto.VideoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "video-service", fallback = VideoServiceClientFallback.class)
public interface VideoServiceClient {

    @GetMapping("/api/videos/{id}")
    VideoResponse getVideoById(@PathVariable("id") Long id);
}