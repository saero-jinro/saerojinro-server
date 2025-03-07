package goorm.saerojinro.domain.review.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "review")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Review extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false, updatable = false)
    private Lecture lecture;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Double rating;

    public static Review createReview(User user, Lecture lecture, String content, Double rating){
        return Review.builder()
                .user(user)
                .lecture(lecture)
                .content(content)
                .rating(rating)
                .build();
    }

    public void update(String content, Double rating){
        this.content = content;
        this.rating = rating;
    }
}
