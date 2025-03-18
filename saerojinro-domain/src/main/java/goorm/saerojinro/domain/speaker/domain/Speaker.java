package goorm.saerojinro.domain.speaker.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.lecture.domain.Lecture;
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
	private String position;

	@Column(nullable = false)
	private String introduction;

	@Column(nullable = false)
	private String filmography;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_file_id", nullable = false)
	private File imageFile;

	@OneToOne(mappedBy = "speaker")
	private Lecture lecture;

	public static Speaker create(String name,String email, String position, String introduction, String filmography, File image) {
		return Speaker.builder()
			.name(name)
			.email(email)
			.position(position)
			.introduction(introduction)
			.filmography(filmography)
			.imageFile(image)
			.build();
	}
}
