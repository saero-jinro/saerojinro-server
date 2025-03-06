package goorm.saerojinro.domain.wishlist.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "wish_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class WishList extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false, updatable = false)
    private Lecture lecture;

    public static WishList createWishList(User user, Lecture lecture){
        return WishList.builder()
                .user(user)
                .lecture(lecture)
                .build();
    }
}
