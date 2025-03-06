package goorm.saerojinro.domain.wishlist.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.wishlist.exception.WishListDomainExceptionCode.NOT_FOUND_WISHLIST;

public class WishListNotFoundException extends CustomException {
    public WishListNotFoundException() {super(NOT_FOUND_WISHLIST);
    }
}
