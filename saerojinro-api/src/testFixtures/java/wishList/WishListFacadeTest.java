package wishList;

import goorm.saerojinro.api.wishlist.application.WishListFacade;
import goorm.saerojinro.api.wishlist.presentation.response.*;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.application.LectureQueryService;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.enums.LectureStatus;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.application.WishListCommandService;
import goorm.saerojinro.domain.wishlist.application.WishListQueryService;
import mock.repository.FakeLectureRepository;
import mock.repository.FakeUserRepository;
import mock.repository.FakeWishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class WishListFacadeTest {
    private WishListFacade wishListFacade;

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
        FakeWishListRepository wishListRepository = new FakeWishListRepository();
        FakeUserRepository userRepository = new FakeUserRepository();
        FakeLectureRepository lectureRepository = new FakeLectureRepository();
        WishListQueryService wishListQueryService = new WishListQueryService(wishListRepository);

        wishListFacade = new WishListFacade(
                wishListQueryService,
                new WishListCommandService(wishListRepository, wishListQueryService),
                new UserQueryService(userRepository, new BCryptPasswordEncoder()),
                new LectureQueryService(lectureRepository)
        );

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

        userRepository.save(user);
        lectureRepository.save(lecture);
    }

    @Test
    @DisplayName("getAllWishList 는 모든 유저 아이디에 해당 하는 모든 즐겨찾기 정보를 조회한다. ")
    public void getAllWishList_Success(){
        // given
        wishListFacade.create(USER_ID, LECTURE_ID);

        // when
        WishListResponse response = wishListFacade.getAllWishList(USER_ID);

        // then
        assertNotNull(response);
        assertEquals(1, response.wishLists().size());
        assertEquals(LECTURE_ID, response.wishLists().get(0).getLecture().getId());
    }

    @Test
    @DisplayName("create 는 해당하는 아이디와 강의에 대한 새로운 즐겨찾기 정보를 생성한다.")
    public void create_Success(){
        // when
        WishListCreateResponse Response = wishListFacade.create(USER_ID, LECTURE_ID);

        // then
        assertNotNull(Response);
        assertTrue(Response.id() > 0);
    }

    @Test
    @DisplayName("delete 는 해당하는 아이디와 강의에 대한 즐겨찾기 정보를 삭제한다.")
    public void delete_Success(){
        // given
        WishListCreateResponse createResponse = wishListFacade.create(USER_ID, LECTURE_ID);
        long createdWishListId = createResponse.id();

        // when
        WishListDeleteResponse deleteResponse = wishListFacade.delete(USER_ID, LECTURE_ID);

        // then
        assertNotNull(deleteResponse);
    }
}
