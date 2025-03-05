package goorm.saerojinro.api.reservation.presentation.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelResponse {

    private final Long reservationId;

}
