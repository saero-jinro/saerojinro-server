package mock.repository;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.reservation.dto.LectureReservationCountDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

public class FakeReservationRepository implements ReservationRepository {
    private final List<Reservation> data = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong sequence = new AtomicLong(1);

    @Override
    public Reservation save(Reservation reservation){
        Reservation newReservation = Reservation.builder()
                .id(sequence.incrementAndGet())
                .user(reservation.getUser())
                .lecture(reservation.getLecture())
                .build();

        data.add(newReservation);
        return newReservation;
    }

    @Override
    public void delete(Reservation reservation) {
        data.remove(reservation);
    }

    @Override
    public boolean existByUserIdAndStartTime(Long userId, LocalDateTime startTime) {
        return data.stream()
                .anyMatch(r -> r.getUser().getId().equals(userId) &&
                        r.getLecture().getStartTime().equals(startTime));
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return data.stream()
                .filter( r -> r.getUser().getId().equals(userId))
                .toList();
    }

    @Override
    public Optional<Reservation> findByUserIdAndLectureId(Long userId, Long lectureId) {
        return data.stream()
                .filter( r -> r.getUser().getId().equals(userId) &&
                        r.getLecture().getId().equals(lectureId))
                .findFirst();
    }

    @Override
    public List<Reservation> findAllByLectureId(Long lectureId) {
        return data.stream()
            .filter(r -> r.getLecture().getId().equals(lectureId))
            .toList();
    }

    @Override
    public int countByLectureId(Long lectureId) {
        return data.stream()
            .filter(r -> r.getLecture().getId().equals(lectureId))
            .toList()
            .size();
    }

    @Override
    public List<LectureReservationCountDto> countReservationAllLecture() {
        Map<Long, Long> counts = data.stream()
            .collect(Collectors.groupingBy(r -> r.getLecture().getId(), Collectors.counting()));

        return counts.entrySet().stream()
            .map(entry -> new LectureReservationCountDto(entry.getKey(), entry.getValue()))
            .toList();
    }
}
