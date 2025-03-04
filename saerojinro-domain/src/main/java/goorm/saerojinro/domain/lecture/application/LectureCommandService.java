package goorm.saerojinro.domain.lecture.application;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.user.domain.UserRepository;
import goorm.saerojinro.domain.user.exception.InvalidUserRoleException;
import goorm.saerojinro.domain.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static goorm.saerojinro.common.domain.BaseRole.SPEAKER;

@Service
@RequiredArgsConstructor
public class LectureCommandService {
	private final LectureRepository lectureRepository;
	private final UserRepository userRepository;

	public Lecture createLecture(Long speakerId, String title, String contents, Long maxCapacity,LocalDateTime startTime, LocalDateTime endTime, String location, Category category) {
		User user = userRepository.findById(speakerId).orElseThrow(UserNotFoundException::new);
		if (user.getRole() != SPEAKER) {
			throw new InvalidUserRoleException();
		}
		Lecture lecture = Lecture.createLecture(user, title, contents, maxCapacity, startTime, endTime, location, category);
		return lectureRepository.save(lecture);
	}
}
