package goorm.saerojinro.domain.reservation.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.reservation.exception.ReservationDomainExceptionCode.NOT_FOUND_RESERVATION;

public class ReservationNotFoundException extends CustomException {

    public ReservationNotFoundException() {
        super(NOT_FOUND_RESERVATION);
    }
}
