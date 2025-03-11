package mock.repository;


import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.reservation.domain.Reservation;
import goorm.saerojinro.domain.reservation.domain.ReservationRepository;
import goorm.saerojinro.domain.user.domain.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

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
    public boolean existByUserAndLecture(User user, Lecture lecture){
        return data.stream()
                .anyMatch(r -> r.getUser().getId().equals(user.getId()) &&
                        r.getLecture().getId().equals(lecture.getId()));
    }

    @Override
    public boolean existByUserAndStartTime(User user, Lecture lecture) {
        return data.stream()
                .anyMatch(r -> r.getUser().getId().equals(user.getId()) &&
                        r.getLecture().getStartTime().equals(lecture.getStartTime()));
    }

    @Override
    public List<Reservation> findByUser(User user) {
        return data.stream()
                .filter( r -> r.getUser().getId().equals(user.getId()))
                .toList();
    }

    @Override
    public Optional<Reservation> findByUserAndLecture(User user, Lecture lecture) {
        return data.stream()
                .filter( r -> r.getUser().getId().equals(user.getId()) &&
                        r.getLecture().getId().equals(lecture.getId()))
                .findFirst();
    }

    @Override
    public List<Reservation> findAllByLectureId(Long lectureId) {
        return data.stream()
            .filter(r -> r.getLecture().getId().equals(lectureId))
            .toList();
    }
}
