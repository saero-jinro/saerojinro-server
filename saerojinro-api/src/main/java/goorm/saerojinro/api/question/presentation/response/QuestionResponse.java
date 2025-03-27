package goorm.saerojinro.api.question.presentation.response;

import goorm.saerojinro.domain.question.domain.Question;
import goorm.saerojinro.domain.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.NOT_REQUIRED;
import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record QuestionResponse(
	@Schema(description = "질문 ID", example = "1", requiredMode = REQUIRED)
	Long id,

	@Schema(description = "질문 내용", example = "이 분야에 대한 공부는 어떤 방식으로 하는 것이 좋을까요?",
		requiredMode = REQUIRED)
	String content,

	@Schema(description = "질문자 ID", example = "1", requiredMode = REQUIRED)
	Long userId,

	@Schema(description = "질문자 이름", example = "홍길동", requiredMode = REQUIRED)
	String userName,

	@Schema(description = "질문자 프로필 사진 위치", example = "https://img1.kakaocdn.net/thumb/111111:",
		requiredMode = NOT_REQUIRED)
	String profileImage,

	@Schema(description = "질문자가 로그인한 유저인지", example = "True", requiredMode = REQUIRED)
	Boolean isWriter
) {
	public static QuestionResponse from(Question question, User currentUser) {
		return QuestionResponse.builder()
			.id(question.getId())
			.content(question.getContent())
			.userId(question.getUser().getId())
			.userName(question.getUser().getName())
			.profileImage(question.getUser().getProfileImage())
			.isWriter(question.getUser().getId().equals(currentUser.getId()))
			.build();
	}
}
