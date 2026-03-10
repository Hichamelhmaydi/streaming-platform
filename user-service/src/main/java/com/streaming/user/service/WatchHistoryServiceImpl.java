package com.streaming.user.service;

import com.streaming.user.dto.WatchHistoryRequest;
import com.streaming.user.dto.WatchHistoryResponse;
import com.streaming.user.dto.WatchStatisticsResponse;
import com.streaming.user.entity.User;
import com.streaming.user.entity.WatchHistory;
import com.streaming.user.exception.UserNotFoundException;
import com.streaming.user.mapper.WatchHistoryMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.repository.WatchHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchHistoryServiceImpl implements WatchHistoryService {

    private final WatchHistoryRepository watchHistoryRepository;
    private final UserRepository userRepository;
    private final WatchHistoryMapper watchHistoryMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WatchHistoryResponse> getHistory(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId)
                .stream()
                .map(watchHistoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WatchHistoryResponse recordWatch(Long userId, WatchHistoryRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        WatchHistory history = WatchHistory.builder()
                .user(user)
                .videoId(request.getVideoId())
                .progressTime(request.getProgressTime())
                .completed(request.getCompleted() != null ? request.getCompleted() : false)
                .build();

        return watchHistoryMapper.toResponse(
                watchHistoryRepository.save(history)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public WatchStatisticsResponse getStatistics(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        List<WatchHistory> history =
                watchHistoryRepository.findByUserIdOrderByWatchedAtDesc(userId);

        long completed =
                history.stream().filter(h -> Boolean.TRUE.equals(h.getCompleted())).count();

        long inProgress =
                history.stream().filter(h -> !Boolean.TRUE.equals(h.getCompleted())).count();

        long totalSeconds =
                history.stream()
                        .mapToLong(h -> h.getProgressTime() != null ? h.getProgressTime() : 0)
                        .sum();

        return WatchStatisticsResponse.builder()
                .userId(userId)
                .totalVideosWatched((long) history.size())
                .completedVideos(completed)
                .inProgressVideos(inProgress)
                .totalWatchTimeSeconds(totalSeconds)
                .build();
    }

    @Override
    @Transactional
    public void clearHistory(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        watchHistoryRepository.deleteByUserId(userId);
    }
}