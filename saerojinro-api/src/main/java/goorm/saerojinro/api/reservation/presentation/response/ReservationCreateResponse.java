package goorm.saerojinro.api.reservation.presentation.response;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import lombok.Builder;
import lombok.Getter;

@Builder
public record ReservationCreateResponse(
        Long id
){
    public static ReservationCreateResponse from(Reservation reservation){
        return ReservationCreateResponse.builder()
                .id(reservation.getId())
                .build();
    }
}
