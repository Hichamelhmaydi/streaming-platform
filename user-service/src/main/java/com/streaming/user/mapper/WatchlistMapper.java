package com.streaming.user.mapper;

import com.streaming.user.dto.WatchlistResponse;
import com.streaming.user.entity.Watchlist;
import org.springframework.stereotype.Component;

@Component
public class WatchlistMapper {

    public WatchlistResponse toResponse(Watchlist watchlist) {
        return WatchlistResponse.builder()
                .id(watchlist.getId())
                .userId(watchlist.getUser().getId())
                .videoId(watchlist.getVideoId())
                .addedAt(watchlist.getAddedAt())
                .build();
    }

    public WatchlistResponse toResponseWithVideo(Watchlist watchlist, Object videoDetails) {
        WatchlistResponse response = toResponse(watchlist);
        response.setVideoDetails(videoDetails);
        return response;
    }
}
