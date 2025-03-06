package goorm.saerojinro.api.wishlist.presentation;

import goorm.saerojinro.api.wishlist.api.WishListFacade;
import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListDeleteResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attendees")
public class WishListControllerImpl implements WishListController{
    private final WishListFacade wishListFacade;

    @Override
    @GetMapping("/{id}/wishlist")
    public ResponseEntity<WishListResponse> getAllWishList(@PathVariable("id") Long attendeeId){
        WishListResponse response = wishListFacade.getAllWishList(attendeeId);
        return ResponseEntity.status(OK).body(response);
    }

    @Override
    @PostMapping("/{id}/wishlist/{lectureId}")
    public ResponseEntity<WishListCreateResponse> create(@PathVariable("id") Long attendeeId,
                                                         @PathVariable("lectureId") Long lectureId) {
        WishListCreateResponse response = wishListFacade.create(attendeeId, lectureId);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/{id}/wishlist/{lectureId}")
    public ResponseEntity<WishListDeleteResponse> delete(@PathVariable("id") Long attendeeId,
                                                         @PathVariable("lectureId") Long lectureId) {
        WishListDeleteResponse response = wishListFacade.delete(attendeeId, lectureId);
        return ResponseEntity.status(OK).body(response);
    }
}
