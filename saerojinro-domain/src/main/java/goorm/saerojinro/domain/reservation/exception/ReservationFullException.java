package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.reservation.exception.ReservationDomainExceptionCode.RESERVATION_FULL;

public class ReservationFullException extends CustomException {
    public ReservationFullException() {
        super(RESERVATION_FULL);
    }
}
