package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Getter
@AllArgsConstructor
public enum ReservationDomainExceptionCode implements ExceptionCode {

    NOT_FOUND_RESERVATION(NOT_FOUND, "예약된 정보가 없습니다."),
    ALREADY_EXIST_RESERVATION(BAD_REQUEST, "이미 예약된 정보가 있습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
