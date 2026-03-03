package com.streaming.user.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "watch_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private Long videoId;

    @Column(nullable = false)
    private LocalDateTime watchedAt;

    private Integer progressTime; // in seconds

    private Boolean completed;

    @PrePersist
    protected void onCreate() {
        watchedAt = LocalDateTime.now();
        if (completed == null) completed = false;
    }
}
