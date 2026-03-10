package com.streaming.user.controller;

import com.streaming.user.dto.WatchHistoryRequest;
import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.dto.WatchStatisticsResponse;
import com.streaming.user.service.WatchHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WatchHistoryResponse>> getHistory(
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(watchHistoryService.getHistory(userId));    }

    @PostMapping("/users/{userId}")
    public ResponseEntity<WatchHistoryResponse> recordWatch(
            @PathVariable("userId") Long userId,
            @RequestBody WatchHistoryRequest request) {
        return ResponseEntity.ok(watchHistoryService.recordWatch(userId, request));
    }

    @GetMapping("/users/{userId}/statistics")
    public ResponseEntity<WatchStatisticsResponse> getStatistics(
            @PathVariable("userId") Long userId) {
        return ResponseEntity.ok(watchHistoryService.getStatistics(userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> clearHistory(
            @PathVariable("userId") Long userId) {
        watchHistoryService.clearHistory(userId);
        return ResponseEntity.noContent().build();
    }
}