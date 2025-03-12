package goorm.saerojinro.api.reservation.presentation;

import goorm.saerojinro.api.reservation.application.ReservationFacade;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reservations")
public class ReservationControllerImpl implements ReservationController {

    private final ReservationFacade reservationFacade;

    @Override
    @PostMapping("/{lectureId}")
    public ResponseEntity<ReservationCreateResponse> create(@PathVariable("lectureId") Long lectureId) {
        ReservationCreateResponse response = reservationFacade.create(lectureId);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> cancel(@PathVariable("lectureId") Long lectureId) {
        reservationFacade.cancel(lectureId);
        return ResponseEntity.noContent().build();
    }
}
