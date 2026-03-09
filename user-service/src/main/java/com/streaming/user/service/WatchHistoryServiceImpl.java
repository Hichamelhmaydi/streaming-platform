package com.streaming.user.service;

import com.streaming.user.dto.WatchHistoryRequest;
import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.dto.WatchStatisticsResponse;
import com.streaming.user.entity.User;
import com.streaming.user.entity.WatchHistory;
import com.streaming.user.exception.UserNotFoundException;
import com.streaming.user.feign.VideoServiceClient;
import com.streaming.user.mapper.WatchHistoryMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final VideoServiceClient videoServiceClient;
    private final WatchHistoryMapper watchHistoryMapper;

    @Override
    public WatchHistoryResponse recordWatch(Long userId, WatchHistoryRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Optional<WatchHistory> existing = watchHistoryRepository.findByUserIdAndVideoId(userId, request.getVideoId());

        WatchHistory history;
        if (existing.isPresent()) {
            history = existing.get();
            history.setProgressTime(request.getProgressTime());
            if (request.getCompleted() != null) {
                history.setCompleted(request.getCompleted());
            }
        } else {
            history = WatchHistory.builder()
                    .user(user)
                    .videoId(request.getVideoId())
                    .progressTime(request.getProgressTime())
                    .completed(request.getCompleted() != null ? request.getCompleted() : false)
                    .build();
        }

        WatchHistory saved = watchHistoryRepository.save(history);
        Object videoDetails = videoServiceClient.getVideoById(request.getVideoId());
        return watchHistoryMapper.toResponseWithVideo(saved, videoDetails);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryResponse> getHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId).stream()
                .map(h -> {
                    Object videoDetails = videoServiceClient.getVideoById(h.getVideoId());
                    return watchHistoryMapper.toResponseWithVideo(h, videoDetails);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WatchStatisticsResponse getStatistics(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }

        long total = watchHistoryRepository.countByUserId(userId);
        long completed = watchHistoryRepository.countByUserIdAndCompleted(userId, true);
        long inProgress = watchHistoryRepository.countByUserIdAndCompleted(userId, false);
        Integer totalTime = watchHistoryRepository.sumProgressTimeByUserId(userId);

        Map<String, Long> byStatus = new HashMap<>();
        byStatus.put("COMPLETED", completed);
        byStatus.put("IN_PROGRESS", inProgress);

        return WatchStatisticsResponse.builder()
                .userId(userId)
                .totalVideosWatched(total)
                .completedVideos(completed)
                .inProgressVideos(inProgress)
                .totalWatchTimeSeconds(totalTime != null ? totalTime : 0)
                .watchCountByStatus(byStatus)
                .build();
    }

    @Override
    public void clearHistory(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        List<WatchHistory> history = watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId);
        watchHistoryRepository.deleteAll(history);
        log.info("Cleared watch history for user {}", userId);
    }
}
