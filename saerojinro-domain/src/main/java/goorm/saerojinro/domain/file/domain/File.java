package goorm.saerojinro.domain.file.domain;

import goorm.saerojinro.common.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@Table(name = "file")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String logicalName;

	@Column(nullable = false, unique = true)
	private String physicalPath;

	@Column(nullable = false)
	private Long fileSize;

	@Column(nullable = false)
	private String extension;

	public static File create(String logicalName, String physicalPath, Long fileSize, String extension) {
		return File.builder()
			.logicalName(logicalName)
			.physicalPath(physicalPath)
			.fileSize(fileSize)
			.extension(extension)
			.build();
	}
}
