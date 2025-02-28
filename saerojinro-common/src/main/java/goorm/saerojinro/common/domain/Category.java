package goorm.saerojinro.common.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
	BACKEND("백엔드 개발 - 서버, 데이터베이스, API 개발 등"),
	FRONTEND("프론트엔드 개발 - 웹, 모바일 UI 개발 등"),
	AI("인공지능 - 머신러닝, 딥러닝, AI 모델 개발 등"),
	DATA_SCIENCE("데이터 사이언스 - 데이터 분석, 데이터 마이닝, 통계 분석 등"),
	CLOUD("클라우드 - AWS, GCP, Azure 등 클라우드 인프라 관리 및 개발"),
	DEVOPS("DevOps - CI/CD, 인프라 자동화, 서버 운영 및 배포 등"),
	DESIGN("디자인 - UX/UI 디자인, 그래픽 디자인 등"),
	SECURITY("보안 - 웹 보안, 네트워크 보안, 시스템 보안 등"),
	PRODUCT_MANAGEMENT("프로덕트 매니지먼트 - 서비스 기획, 프로젝트 관리, 요구사항 정의 등");

	private final String description;
}
