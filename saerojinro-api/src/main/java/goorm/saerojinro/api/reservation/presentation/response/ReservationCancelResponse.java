package goorm.saerojinro.api.reservation.presentation.response;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

@Builder
public record ReservationCancelResponse(
        Long id
){
    public static ReservationCancelResponse from(Reservation reservation){
        return ReservationCancelResponse.builder()
                .id(reservation.getId())
                .build();
    }
}
