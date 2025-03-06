package wishlist.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListCommandService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.exception.WishListExistException;
import mock.repository.FakeWishListRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

public class WishListCommandServiceTest {
    private WishListCommandService wishListCommandService;
    private WishListQueryService wishListQueryService;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;
    private static final LectureStatus STATUS = LectureStatus.PENDING_APPROVAL;

    @BeforeEach
    void init(){
        WishListRepository wishListRepository = new FakeWishListRepository();
        wishListQueryService = new WishListQueryService(wishListRepository);
        wishListCommandService = new WishListCommandService(wishListRepository, wishListQueryService);
        User user = User.builder()
                .id(USER_ID)
                .build();

        Lecture lecture = Lecture.builder()
                .id(LECTURE_ID)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();
    }

    private User createUser(Long id) {
        return User.builder().id(id).build();
    }

    private Lecture createLecture(Long id) {
        return Lecture.builder()
                .id(id)
                .title(LECTURE_TITLE)
                .contents(LECTURE_CONTENTS)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .location(LOCATION)
                .category(CATEGORY)
                .lectureStatus(STATUS)
                .build();
    }

    @Test
    @DisplayName("create 는 새로운 즐겨찾기 정보를 생성한다.")
    public void create_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        // when
        WishList wishList = wishListCommandService.create(user, lecture);

        // then
        assertNotNull(wishList);
        assertThat(wishList.getUser().getId()).isEqualTo(user.getId());
        assertThat(wishList.getLecture().getId()).isEqualTo(lecture.getId());
    }

    @Test
    @DisplayName("create 는 이미 저장된 즐겨찾기 정보를 생성할 때, WishListExistException 반환한다.")
    public void create_WishListExistException(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        WishList wishList1 = wishListCommandService.create(user, lecture);

        // when
        assertThrows(WishListExistException.class,
                () -> wishListCommandService.create(user, lecture));
    }

    @Test
    @DisplayName("delete 는 저장된 즐겨찾기 정보를 삭제한다.")
    public void delete_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        WishList wishList = wishListCommandService.create(user, lecture);
        assertNotNull(wishList);

        // when
        wishListCommandService.delete(wishList);

        // then
        boolean isExist = wishListQueryService.existCheck(user, lecture);
        Assertions.assertThat(isExist).isFalse();
    }

}
