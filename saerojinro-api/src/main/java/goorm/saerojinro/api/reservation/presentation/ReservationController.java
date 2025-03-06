package goorm.saerojinro.api.reservation.presentation;

import goorm.saerojinro.api.reservation.presentation.response.ReservationCancelResponse;
import goorm.saerojinro.api.reservation.presentation.response.ReservationCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Reservation", description = "예약 API")
public interface ReservationController {

    @Operation(
        summary = "예약 생성",
        description = "유저 ID와 강의 ID를 통해 예약을 생성합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = ReservationCreateResponse.class))
            )
        }
    )
    ResponseEntity<ReservationCreateResponse> create(@PathVariable("id") Long attendeeId,
                                                     @PathVariable("lectureId") Long lectureId);

    @Operation(
        summary = "예약 취소",
        description = "유저 ID와 강의 ID를 통해 예약을 취소합니다.",
        responses = {
            @ApiResponse(
                responseCode = "200",
                content = @Content(schema = @Schema(implementation = ReservationCreateResponse.class))
            )
        }
    )
    ResponseEntity<ReservationCancelResponse> cancel(@PathVariable("id") Long attendeeId,
                                                     @PathVariable("lectureId") Long lectureId);
}
