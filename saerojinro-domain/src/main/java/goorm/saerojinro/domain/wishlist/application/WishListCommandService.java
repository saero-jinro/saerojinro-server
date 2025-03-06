package goorm.saerojinro.domain.wishlist.application;

import goorm.saerojinro.domain.lecture.domain.Lecture;
import goorm.saerojinro.domain.user.domain.User;
import goorm.saerojinro.domain.wishlist.domain.WishList;
import goorm.saerojinro.domain.wishlist.domain.WishListRepository;
import goorm.saerojinro.domain.wishlist.exception.WishListExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WishListCommandService {
    private final WishListRepository wishListRepository;
    private final WishListQueryService wishListQueryService;

    public WishList create(User user, Lecture lecture){
        if(wishListQueryService.existCheck(user, lecture)) {
            throw new WishListExistException();
        }
        WishList wishList = WishList.createWishList(user, lecture);
        return wishListRepository.save(wishList);
    }

    public void delete(WishList wishList){
        wishListRepository.delete(wishList);
    }

}
