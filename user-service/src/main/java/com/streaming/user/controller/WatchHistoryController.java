package com.streaming.user.controller;

import com.streaming.user.dto.WatchHistoryRequest;
import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.dto.WatchStatisticsResponse;
import com.streaming.user.service.WatchHistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class WatchHistoryController {

    private final WatchHistoryService watchHistoryService;

    @PostMapping("/users/{userId}")
    public ResponseEntity<WatchHistoryResponse> recordWatch(@PathVariable Long userId,
                                                            @Valid @RequestBody WatchHistoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(watchHistoryService.recordWatch(userId, request));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WatchHistoryResponse>> getHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(watchHistoryService.getHistory(userId));
    }

    @GetMapping("/users/{userId}/statistics")
    public ResponseEntity<WatchStatisticsResponse> getStatistics(@PathVariable Long userId) {
        return ResponseEntity.ok(watchHistoryService.getStatistics(userId));
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> clearHistory(@PathVariable Long userId) {
        watchHistoryService.clearHistory(userId);
        return ResponseEntity.noContent().build();
    }
}
