package goorm.saerojinro.api.notification.presentation.request;

// TODO 스웨거, validation 추가
public record NotificationSendRequest(
	String title,
	String contents
) {
}
