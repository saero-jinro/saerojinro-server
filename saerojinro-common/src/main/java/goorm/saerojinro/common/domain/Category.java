package goorm.saerojinro.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
	BACKEND("서버, DB, API 개발"),
	FRONTEND("웹, 모바일 UI 개발"),
	AI("머신러닝, 딥러닝, AI 모델"),
	DATA("데이터 분석, 통계"),
	CLOUD("AWS, GCP, Azure"),
	DEVOPS("CI/CD, 인프라 자동화"),
	UX_UI("UX/UI 디자인, 그래픽 디자인"),
	SEC("웹, 네트워크, 시스템 보안"),
	PM("서비스 기획, 프로젝트 관리"),
	BLOCKCHAIN("블록체인, 스마트 컨트랙트"),
	MOBILE("안드로이드, iOS 앱 개발");

	private final String description;
}
