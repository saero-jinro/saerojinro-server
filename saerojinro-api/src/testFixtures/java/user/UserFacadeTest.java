package user;

import static goorm.saerojinro.common.domain.BaseRole.ADMIN;
import static goorm.saerojinro.common.domain.Category.BACKEND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import goorm.saerojinro.api.user.application.UserFacade;
import goorm.saerojinro.api.user.presentation.request.UserUpdateRequest;
import goorm.saerojinro.api.user.presentation.response.UserInfoResponse;
import goorm.saerojinro.domain.user.application.UserCommandService;
import goorm.saerojinro.domain.user.application.UserQueryService;
import goorm.saerojinro.domain.user.domain.User;
import mock.repository.FakeUserRepository;

public class UserFacadeTest {
	private UserFacade userFacade;
	private User user;

	@BeforeEach
	public void init() {
		FakeUserRepository fakeUserRepository = new FakeUserRepository();
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserQueryService userQueryService = new UserQueryService(fakeUserRepository, passwordEncoder);
		userFacade = new UserFacade(
			new UserCommandService(fakeUserRepository, passwordEncoder),
			userQueryService
		);

		user = fakeUserRepository.save(User.builder()
			.email("email@email.com")
			.password(passwordEncoder.encode("password1234!"))
			.name("박민준")
			.role(ADMIN)
			.build()
		);

		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities())
		);
	}

	@Test
	@DisplayName("update는 유저의 정보를 수정한다")
	public void update_Success() {
		// given
		UserUpdateRequest request = UserUpdateRequest.builder()
			.email("update@email.com")
			.name("박준")
			.interest(BACKEND)
			.build();

		// when
		userFacade.update(request);

		// then
		assertEquals("update@email.com", user.getEmail());
		assertEquals("박준", user.getName());
		assertEquals(BACKEND, user.getInterest());
	}

	@Test
	@DisplayName("delete는 현재 로그인 된 유저 정보를 삭제한다.")
	public void delete_Success() {
		// when
		userFacade.delete();

		// then
		assertNotNull(user.getDeletedAt());
	}

	@Test
	@DisplayName("getCurrentUserInfo는 현재 로그인 된 유저의 정보를 조회한다.")
	public void getCurrentUserInfo_Success() {
		// when
		UserInfoResponse result = userFacade.getCurrentUserInfo();

		// then
		assertEquals(user.getName(), result.name());
		assertEquals(user.getEmail(), result.email());
	}
}
