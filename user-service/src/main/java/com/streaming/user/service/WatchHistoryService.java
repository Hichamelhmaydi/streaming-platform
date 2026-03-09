package com.streaming.user.service;

import com.streaming.user.dto.WatchHistoryRequest;
import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.dto.WatchStatisticsResponse;
import java.util.List;

public interface WatchHistoryService {
    WatchHistoryResponse recordWatch(Long userId, WatchHistoryRequest request);
    List<WatchHistoryResponse> getHistory(Long userId);
    WatchStatisticsResponse getStatistics(Long userId);
    void clearHistory(Long userId);
}
