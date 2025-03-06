package goorm.saerojinro.domain.wishlist.exception;

import goorm.saerojinro.common.exception.ExceptionCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum WishListDomainExceptionCode implements ExceptionCode {
    NOT_FOUND_WISHLIST(NOT_FOUND, "즐겨찾기된 강의 정보가 없습니다."),
    AREADY_EXIST_WISHLIST(BAD_REQUEST, "이미 즐겨찾기된 강의 정보가 있습니다.");

    private final HttpStatus status;
    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
