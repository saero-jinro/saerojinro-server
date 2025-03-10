package wishlist.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.exception.WishListNotFoundException;
import mock.repository.FakeWishListRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class WishListQueryServiceTest {
    private WishListQueryService wishListQueryService;

    private static final Long USER_ID = 1L;
    private static final Long LECTURE_ID = 1L;
    private static final String LECTURE_TITLE = "Title";
    private static final String LECTURE_CONTENTS = "Contents";
    private static final LocalDateTime START_TIME = LocalDateTime.of(2025, 3, 1, 10, 0);
    private static final LocalDateTime END_TIME = LocalDateTime.of(2025, 3, 1, 12, 0);
    private static final String LOCATION = "Location";
    private static final Category CATEGORY = Category.BACKEND;

    @BeforeEach
    void init(){
        WishListRepository wishListRepository = new FakeWishListRepository();
        wishListQueryService = new WishListQueryService(wishListRepository);

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
                .build();

        WishList wishList = WishList.createWishList(user, lecture);
        wishListRepository.save(wishList);
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
                .build();
    }

    @Test
    @DisplayName("getAllByUserId 는 유저 아이디에 해당 하는 즐겨찾기 정보를 조회할 수 있다.")
    public void getAllByUserId_Success(){
        // given
        User user = createUser(USER_ID);

        // when
        List<WishList> findWishLists = wishListQueryService.getAllByUser(user);

        // then
        assertThat(findWishLists)
                .isNotNull()
                .hasSize(1);

        WishList wishList = findWishLists.get(0);
        assertThat(wishList.getUser().getId()).isEqualTo(USER_ID);
        assertThat(wishList.getLecture().getId()).isEqualTo(LECTURE_ID);
    }

    @Test
    @DisplayName("getByUserAndLecture 는 유저 아이디와 강의 아이디에 해당하는 즐겨찾기 정보를 조회할 수 있다.")
    public void getByUserAndLecture_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        // when
        WishList findWishList = wishListQueryService.getByUserAndLecture(user, lecture);

        // then
        assertThat(findWishList.getUser().getId()).isEqualTo(user.getId());
        assertThat(findWishList.getLecture().getId()).isEqualTo(lecture.getId());
    }

    @Test
    @DisplayName("getByUserAndLecture 는 해당하는 즐겨찾기 정보가 없을 시 WishListNotFoundException 예외를 반환한다.")
    public void getByUserAndLecture_WishListNotFoundException(){
        // given
        User user = createUser(2L);
        Lecture lecture = createLecture(LECTURE_ID);

        // then
        assertThrows(WishListNotFoundException.class,
                () -> wishListQueryService.getByUserAndLecture(user, lecture));
    }

    @Test
    @DisplayName("existCheck 는 유저 아이디와 강의 아이디에 해당하는 즐겨찾기의 존재 여부를 확인 한다.")
    public void existCheck_Success(){
        // given
        User user = createUser(USER_ID);
        Lecture lecture = createLecture(LECTURE_ID);

        // when
        boolean isExist = wishListQueryService.existCheck(user, lecture);

        // then
        assertThat(isExist).isTrue();
    }
}
