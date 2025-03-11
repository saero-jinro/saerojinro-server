package goorm.saerojinro.api.reservation.presentation;

import goorm.saerojinro.api.reservation.application.ReservationFacade;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendees")
public class ReservationControllerImpl implements ReservationController {

    private final ReservationFacade reservationFacade;

    @Override
    @PostMapping("/{id}/reservations/{lectureId}")
    public ResponseEntity<ReservationCreateResponse> create(@PathVariable("id") Long attendeeId,
                                                            @PathVariable("lectureId") Long lectureId) {
        ReservationCreateResponse response = reservationFacade.create(attendeeId, lectureId);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/{id}/reservations/{lectureId}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long attendeeId,
                                                            @PathVariable("lectureId") Long lectureId) {
        reservationFacade.cancel(attendeeId, lectureId);
        return ResponseEntity.noContent().build();
    }
}
