package goorm.saerojinro.domain.lecture.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.file.domain.File;
import goorm.saerojinro.domain.speaker.domain.Speaker;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Table(
	indexes = {
		@Index(name = "idx_category_time", columnList = "category, startTime"),
		@Index(name = "idx_start_time", columnList = "startTime")
	}
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "speaker_id", nullable = false)
	private Speaker speaker;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "thumbnail_file_id", nullable = false)
	private File thumbnailFile;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_file_id", nullable = false)
	private File materialFile;

	@Column(nullable = false, unique = true)
	private String title;

	@Column(nullable = false, columnDefinition = "text")
	private String contents;

	@Column(nullable = false)
	private Long maxCapacity;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = false)
	private LocalDateTime endTime;

	@Column(nullable = false)
	private String location;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;

	public static Lecture create(Speaker speaker, String title, String contents,
								 File thumbnailFile, File materialFile, Long maxCapacity,
								 LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {
		return Lecture.builder()
			.speaker(speaker)
			.title(title)
			.contents(contents)
			.thumbnailFile(thumbnailFile)
			.materialFile(materialFile)
			.maxCapacity(maxCapacity)
			.startTime(startTime)
			.endTime(endTime)
			.location(location)
			.category(category)
			.build();
	}

	public void update(String title, String contents, Long maxCapacity, LocalDateTime startTime,
					   LocalDateTime endTime, String location, Category category) {
		if (title != null) {
			this.title = title;
		}
		if (contents != null) {
			this.contents = contents;
		}
		if (maxCapacity != null) {
			this.maxCapacity = maxCapacity;
		}
		if (startTime != null) {
			this.startTime = startTime;
		}
		if (endTime != null) {
			this.endTime = endTime;
		}
		if (location != null) {
			this.location = location;
		}
		if (category != null) {
			this.category = category;
		}
	}

	@Override
	public void delete() {
		super.delete();
	}
}
