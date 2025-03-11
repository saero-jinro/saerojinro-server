package goorm.saerojinro.domain.speaker.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.domain.file.domain.File;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "speaker")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Speaker extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false)
	private String position; // 기업 / 직급

	@Column(nullable = false)
	private String introduction; // 강의 리스트에 보여줄 한 줄 소개

	@Column(nullable = false)
	private String filmography; // 상세 정보 약력

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", nullable = false)
	private File file;

	public static Speaker create(String name,String email, String position, String introduction, String filmography, File file) {
		return Speaker.builder()
			.name(name)
			.email(email)
			.position(position)
			.introduction(introduction)
			.filmography(filmography)
			.file(file)
			.build();
	}
}
