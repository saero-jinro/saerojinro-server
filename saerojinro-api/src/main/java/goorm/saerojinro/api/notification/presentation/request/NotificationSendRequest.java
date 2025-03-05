package goorm.saerojinro.api.notification.presentation.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Builder
public record NotificationSendRequest(
	@Schema(description = "제목", example = "\"개발 그렇게 하는거 아닌데\" 강의 취소 관련", requiredMode = REQUIRED)
	@Length(min = 1, max = 100)
	@NotBlank
	String title,

	@Schema(description = "이름", example = "\"개발 그렇게 하는거 아닌데\" 강의가 취소 되었습니다.", requiredMode = REQUIRED)
	@Length(min = 1, max = 200)
	@NotBlank
	String contents
) {
}
