package goorm.saerojinro.api.reservation.presentation;

import goorm.saerojinro.api.reservation.presentation.response.ReservationCancelResponse;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface ReservationController {

    ResponseEntity<ReservationCreateResponse> create(@PathVariable("id") Long attendeeId,
                                                     @PathVariable("lectureId") Long lectureId);

    ResponseEntity<ReservationCancelResponse> cancel(@PathVariable("id") Long attendeeId,
                                                     @PathVariable("lectureId") Long lectureId);
}
