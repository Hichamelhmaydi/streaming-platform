package com.streaming.user.feign;

import com.streaming.user.dto.VideoResponse;
import org.springframework.stereotype.Component;

@Component
public class VideoServiceClientFallback implements VideoServiceClient {

    @Override
    public VideoResponse getVideoById(Long id) {
        return null; // retourne null silencieusement en cas d'erreur
    }
}