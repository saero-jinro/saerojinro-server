package goorm.saerojinro.api.notification.presentation.request;

// TODO 스웨거, validation 추가
public record NotificationSendRequest(
	Long lectureId,
	String title,
	String contents
) {
}
