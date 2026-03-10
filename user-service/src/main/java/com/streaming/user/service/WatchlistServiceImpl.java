package com.streaming.user.service;

import com.streaming.user.dto.WatchlistResponse;
import com.streaming.user.entity.User;
import com.streaming.user.entity.Watchlist;
import com.streaming.user.exception.UserNotFoundException;
import com.streaming.user.mapper.WatchlistMapper;
import com.streaming.user.repository.UserRepository;
import com.streaming.user.repository.WatchlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistServiceImpl implements WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final UserRepository userRepository;
    private final WatchlistMapper watchlistMapper;

    @Override
    @Transactional(readOnly = true)
    public List<WatchlistResponse> getWatchlist(Long userId) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        return watchlistRepository.findByUserId(userId)
                .stream()
                .map(watchlistMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public WatchlistResponse addToWatchlist(Long userId, Long videoId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        if (watchlistRepository.existsByUserIdAndVideoId(userId, videoId)) {
            throw new RuntimeException("Video already in watchlist");
        }

        Watchlist watchlist = Watchlist.builder()
                .user(user)
                .videoId(videoId)
                .build();

        return watchlistMapper.toResponse(watchlistRepository.save(watchlist));
    }

    @Override
    @Transactional
    public void removeFromWatchlist(Long userId, Long videoId) {

        Watchlist item = watchlistRepository.findByUserIdAndVideoId(userId, videoId)
                .orElseThrow(() -> new RuntimeException("Item not found in watchlist"));

        watchlistRepository.delete(item);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isInWatchlist(Long userId, Long videoId) {
        return watchlistRepository.existsByUserIdAndVideoId(userId, videoId);
    }
}