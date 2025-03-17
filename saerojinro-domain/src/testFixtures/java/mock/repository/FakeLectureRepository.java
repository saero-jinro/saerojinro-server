package mock.repository;

import goorm.saerojinro.common.domain.Category;
import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.lecture.domain.LectureRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeLectureRepository implements LectureRepository {
	private final List<Lecture> data = Collections.synchronizedList(new ArrayList<>());
	private final AtomicLong sequence = new AtomicLong(0);

	@Override
	public Lecture save(Lecture lecture) {
		Lecture saved = Lecture.builder()
			.id(sequence.incrementAndGet())
			.speaker(lecture.getSpeaker())
			.title(lecture.getTitle())
			.contents(lecture.getContents())
			.thumbnailFile(lecture.getThumbnailFile())
			.materialFile(lecture.getMaterialFile())
			.maxCapacity(lecture.getMaxCapacity())
			.startTime(lecture.getStartTime())
			.endTime(lecture.getEndTime())
			.location(lecture.getLocation())
			.category(lecture.getCategory())
			.build();
		data.add(saved);
		return saved;
	}

	@Override
	public Optional<Lecture> findById(Long id) {
		return data.stream()
			.filter(lecture -> lecture.getId().equals(id))
			.findFirst();
	}

	@Override
	public Optional<Lecture> findByIdWithLock(Long id) {
		return data.stream()
			.filter(lecture -> lecture.getId().equals(id))
			.findFirst();
	}

	@Override
	public void delete(Lecture lecture) {
		data.removeIf(l -> l.getId().equals(lecture.getId()));
	}

	@Override
	public List<Lecture> findByStartTimeBetween(LocalDateTime start, LocalDateTime end) {
		return data.stream()
			.filter(lecture -> {
				LocalDateTime lectureTime = lecture.getStartTime();
				boolean isAfterOrEqualStart = !lectureTime.isBefore(start);
				boolean isBeforeEnd = lectureTime.isBefore(end);
				return isAfterOrEqualStart && isBeforeEnd;
			})
			.toList();
	}

	@Override
	public List<Lecture> findByStartTime(LocalDateTime time) {
		return data.stream()
			.filter(lecture -> lecture.getStartTime().equals(time))
			.toList();
	}

	@Override
	public List<Lecture> findByStartTimeAfterAndEndTimeBefore(LocalDateTime startTime, LocalDateTime endTime) {
		return data.stream()
			.filter(lecture ->
				!lecture.getStartTime().isBefore(startTime) &&
					!lecture.getEndTime().isAfter(endTime)
			)
			.toList();
	}

	@Override
	public List<Lecture> findByCategoryInAndStartTime(List<Category> categories, LocalDateTime lectureTime) {
		return data.stream()
			.filter(lecture -> categories.contains(lecture.getCategory()))
			.filter(lecture -> lecture.getStartTime().isEqual(lectureTime))
			.toList();
	}

	@Override
	public List<Lecture> findAll() {
		return data;
	}

	@Override
	public Optional<Lecture> findBySpeakerId(Long id) {
		return data.stream()
			.filter(lecture -> lecture.getSpeaker().getId().equals(id))
			.findFirst();
	}
}
