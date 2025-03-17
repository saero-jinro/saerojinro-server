package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ReservationDomainExceptionCode implements ExceptionCode {

    RESERVATION_NOT_FOUND(NOT_FOUND, "예약된 정보가 없습니다."),
    RESERVATION_FULL(CONFLICT,"모든 예약이 차있습니다."),
    RESERVATION_ALREADY_EXIST(BAD_REQUEST, "이미 예약된 정보가 있습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
