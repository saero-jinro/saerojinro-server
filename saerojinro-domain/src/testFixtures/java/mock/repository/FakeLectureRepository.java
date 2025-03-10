package mock.repository;

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
	public List<Lecture> findAll() {
		return new ArrayList<>(data);
	}

	@Override
	public Optional<Lecture> findById(Long id) {
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
				boolean isBeforeEnd         = lectureTime.isBefore(end);
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
	public List<Lecture> findByStartTimeAfterAndEndTimeBefore(LocalDateTime time1, LocalDateTime time2) {
		return data.stream()
			.filter(lecture ->
				!lecture.getStartTime().isBefore(time1) &&
					!lecture.getEndTime().isAfter(time2)
			)
			.toList();
	}
}
