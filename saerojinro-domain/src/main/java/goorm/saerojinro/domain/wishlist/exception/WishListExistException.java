package goorm.saerojinro.domain.wishlist.exception;

import goorm.saerojinro.common.exception.CustomException;

import static goorm.saerojinro.domain.wishlist.exception.WishListDomainExceptionCode.AREADY_EXIST_WISHLIST;

public class WishListExistException extends CustomException {
    public WishListExistException() {
        super(AREADY_EXIST_WISHLIST);
    }
}
