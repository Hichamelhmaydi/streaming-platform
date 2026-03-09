package com.streaming.user.service;

import com.streaming.user.dto.WatchlistResponse;
import java.util.List;

public interface WatchlistService {
    WatchlistResponse addToWatchlist(Long userId, Long videoId);
    void removeFromWatchlist(Long userId, Long videoId);
    List<WatchlistResponse> getWatchlist(Long userId);
    boolean isInWatchlist(Long userId, Long videoId);
}
