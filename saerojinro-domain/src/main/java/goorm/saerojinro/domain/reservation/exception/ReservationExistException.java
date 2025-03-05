package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.reservation.exception.ReservationDomainExceptionCode.ALREADY_EXIST_RESERVATION;

public class ReservationExistException extends CustomException {

    public ReservationExistException() {
        super(ALREADY_EXIST_RESERVATION);
    }
}
