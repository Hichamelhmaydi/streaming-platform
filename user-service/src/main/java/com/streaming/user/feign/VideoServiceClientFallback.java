package com.streaming.user.feign;

import org.springframework.stereotype.Component;

@Component
public class VideoServiceClientFallback implements VideoServiceClient {

    @Override
    public Object getVideoById(Long id) {
        return null;
    }
}
