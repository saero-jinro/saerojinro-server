package goorm.saerojinro.api.reservation.presentation;

import goorm.saerojinro.api.reservation.application.ReservationFacade;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('USER')")
@RequestMapping("/api/reservations")
public class ReservationControllerImpl implements ReservationController {
    private final ReservationFacade reservationFacade;

    @Override
    @PostMapping("/lectures/{id}")
    public ResponseEntity<ReservationCreateResponse> create(@PathVariable("id") Long id) {
        ReservationCreateResponse response = reservationFacade.create(id);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/lectures/{id}")
    public ResponseEntity<Void> cancel(@PathVariable("id") Long id) {
        reservationFacade.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
