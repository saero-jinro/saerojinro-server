package goorm.saerojinro.api.wishlist.presentation;

import goorm.saerojinro.api.lecture.presentation.response.LectureSummaryListResponse;
import goorm.saerojinro.api.wishlist.application.WishListFacade;
import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.CREATED;

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
    @GetMapping("/lectures")
    public ResponseEntity<LectureSummaryListResponse> getByUserAndStartTime(
            @RequestParam("startTime") LocalDateTime lectureStartTime) {
		LectureSummaryListResponse response = wishListFacade.getByUserAndStartTime(lectureStartTime);
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping("/lectures/{id}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public ResponseEntity<WishListCreateResponse> create(@PathVariable("id") Long id) {
        WishListCreateResponse response = wishListFacade.create(id);
        return ResponseEntity.status(CREATED).body(response);
    }

    @Override
    @DeleteMapping("/lectures/{id}")
    @PreAuthorize("hasRole('ATTENDEE')")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        wishListFacade.delete(id);
        return ResponseEntity.noContent().build();
    }
}
