package com.streaming.user.mapper;

import com.streaming.user.dto.WatchlistResponse;
import com.streaming.user.entity.Watchlist;
import org.springframework.stereotype.Component;

@Component
public class WatchlistMapper {

    public WatchlistResponse toResponse(Watchlist entity) {
        return WatchlistResponse.builder()
                .id(entity.getId())
                .userId(entity.getUser().getId())
                .videoId(entity.getVideoId())
                .addedAt(entity.getAddedAt())
                .videoDetails(null)
                .build();
    }
}