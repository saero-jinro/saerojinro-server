package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.reservation.exception.ReservationDomainExceptionCode.RESERVATION_NOT_FOUND;

public class ReservationNotFoundException extends CustomException {

    public ReservationNotFoundException() {
        super(RESERVATION_NOT_FOUND);
    }
}
