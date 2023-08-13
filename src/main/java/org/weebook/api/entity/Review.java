package org.weebook.api.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.weebook.api.entity.audit.DateAudit;
import org.weebook.api.entity.embed.ReviewId;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "reviews")
public class Review extends DateAudit {
    @EmbeddedId
    private ReviewId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"userId\"", nullable = false)
    @ToString.Exclude
    private User user;

    @MapsId("bookId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "\"bookId\"", nullable = false)
    @ToString.Exclude
    private Book book;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "content", length = Integer.MAX_VALUE)
    private String content;
}