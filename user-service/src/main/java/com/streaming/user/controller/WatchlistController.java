package com.streaming.user.controller;

import com.streaming.user.dto.WatchlistResponse;
import com.streaming.user.service.WatchlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/watchlist")
@RequiredArgsConstructor
public class WatchlistController {

    private final WatchlistService watchlistService;

    @PostMapping("/users/{userId}/videos/{videoId}")
    public ResponseEntity<WatchlistResponse> addToWatchlist(@PathVariable Long userId,
                                                            @PathVariable Long videoId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(watchlistService.addToWatchlist(userId, videoId));
    }

    @DeleteMapping("/users/{userId}/videos/{videoId}")
    public ResponseEntity<Void> removeFromWatchlist(@PathVariable Long userId,
                                                    @PathVariable Long videoId) {
        watchlistService.removeFromWatchlist(userId, videoId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<WatchlistResponse>> getWatchlist(@PathVariable Long userId) {
        return ResponseEntity.ok(watchlistService.getWatchlist(userId));
    }

    @GetMapping("/users/{userId}/videos/{videoId}/check")
    public ResponseEntity<Map<String, Boolean>> checkInWatchlist(@PathVariable Long userId,
                                                                 @PathVariable Long videoId) {
        boolean inWatchlist = watchlistService.isInWatchlist(userId, videoId);
        return ResponseEntity.ok(Map.of("inWatchlist", inWatchlist));
    }
}
