package com.streaming.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "video-service", fallback = VideoServiceClientFallback.class)
public interface VideoServiceClient {

    @GetMapping("/api/videos/{id}")
    Object getVideoById(@PathVariable("id") Long id);
}
