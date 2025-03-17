package goorm.saerojinro.api.reservation.presentation.response;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

import goorm.saerojinro.domain.reservation.domain.Reservation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ReservationCreateResponse(
    @Schema(description = "예약 ID", example = "1", requiredMode = REQUIRED)
    Long id
){
    public static ReservationCreateResponse from(Reservation reservation){
        return ReservationCreateResponse.builder()
                .id(reservation.getId())
                .build();
    }
}
