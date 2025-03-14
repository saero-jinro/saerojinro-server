package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.reservation.exception.ReservationDomainExceptionCode.RESERVATION_ALREADY_EXIST;

public class ReservationExistException extends CustomException {

    public ReservationExistException() {
        super(RESERVATION_ALREADY_EXIST);
    }
}
