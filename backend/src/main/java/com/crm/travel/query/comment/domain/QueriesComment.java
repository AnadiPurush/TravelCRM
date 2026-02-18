package com.crm.travel.query.comment.domain;

import com.crm.travel.query.domain.Queries;
import com.crm.travel.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "quarie_comment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueriesComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 2000)
    private String commentText;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;
@PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quarie_id", nullable = false)
    private Queries quarie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
