package goorm.saerojinro.api.wishlist.presentation;

import goorm.saerojinro.api.wishlist.application.*;
import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishListControllerImpl implements WishListController{
    private final WishListFacade wishListFacade;

    @Override
    @GetMapping
    public ResponseEntity<WishListResponse> getAllWishList(){
        WishListResponse response = wishListFacade.getAllWishList();
        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("/{startTime}")
    public ResponseEntity<WishListResponse> getByUserAndStartTime(
            @PathVariable("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startTime) {
        WishListResponse response = wishListFacade.getByUserAndStartTime(startTime);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/{lectureId}")
    public ResponseEntity<WishListCreateResponse> create(@PathVariable("lectureId") Long lectureId) {
        WishListCreateResponse response = wishListFacade.create(lectureId);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/{lectureId}")
    public ResponseEntity<Void> delete(@PathVariable("lectureId") Long lectureId) {
        wishListFacade.delete(lectureId);
        return ResponseEntity.noContent().build();
    }
}
