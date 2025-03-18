package goorm.saerojinro.domain.lecture.application.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record LectureCacheListDTO(
	List<LectureCacheDTO> lectureCacheListDTO
) {
	public static LectureCacheListDTO from(List<LectureCacheDTO> lectureCacheDTOList) {
		return LectureCacheListDTO.builder()
			.lectureCacheListDTO(lectureCacheDTOList)
			.build();
	}
}
