package com.streaming.user.service;

import com.streaming.user.dto.WatchlistResponse;
import com.streaming.user.entity.User;
import com.streaming.user.entity.Watchlist;
import com.streaming.user.exception.UserNotFoundException;
import com.streaming.user.feign.VideoServiceClient;
import com.streaming.user.mapper.WatchlistMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final VideoServiceClient videoServiceClient;
    private final WatchlistMapper watchlistMapper;

    @Override
    public WatchlistResponse addToWatchlist(Long userId, Long videoId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new RuntimeException("Video already in watchlist");
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .videoId(videoId)
                .build();

        Watchlist saved = watchlistRepository.save(watchlist);
        Object videoDetails = videoServiceClient.getVideoById(videoId);
        return watchlistMapper.toResponseWithVideo(saved, videoDetails);
    }

    @Override
    public void removeFromWatchlist(Long userId, Long videoId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        watchlistRepository.deleteByUserIdAndVideoId(userId, videoId);
        log.info("Removed video {} from watchlist of user {}", videoId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WatchlistResponse> getWatchlist(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException(userId);
        }
        return watchlistRepository.findByUserId(userId).stream()
                .map(wl -> {
                    Object videoDetails = videoServiceClient.getVideoById(wl.getVideoId());
                    return watchlistMapper.toResponseWithVideo(wl, videoDetails);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInWatchlist(Long userId, Long videoId) {
        return watchlistRepository.existsByUserIdAndVideoId(userId, videoId);
    }
}
