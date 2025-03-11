package goorm.saerojinro.domain.reservation.domain;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    boolean existByUserAndLecture(User user, Lecture lecture);

    boolean existByUserAndStartTime(User user, Lecture lecture);

    List<Reservation> findByUser(User user);

    Optional<Reservation> findByUserAndLecture(User user, Lecture lecture);

    Reservation save(Reservation reservation);

    void delete(Reservation reservation);

	List<Reservation> findAllByLectureId(Long lectureId);
}
