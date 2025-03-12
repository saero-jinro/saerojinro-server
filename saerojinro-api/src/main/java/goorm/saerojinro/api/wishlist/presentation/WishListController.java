package goorm.saerojinro.api.wishlist.presentation;

import goorm.saerojinro.api.wishlist.presentation.response.WishListCreateResponse;
import goorm.saerojinro.api.wishlist.presentation.response.WishListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "WishList", description = "즐겨찾기 API")
public interface WishListController {
    @Operation(
            summary = "즐겨찾기 조회",
            description = "유저 ID로 즐겨찾기 목록을 조회 합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(schema = @Schema(implementation = WishListResponse.class))
                    )
            }
    )
    ResponseEntity<WishListResponse> getAllWishList();

    @Operation(
            summary = "즐겨찾기 생성",
            description = "유저 ID와 강의 ID를 통해 즐겨찾기를 생성합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            content = @Content(schema = @Schema(implementation = WishListCreateResponse.class))
                    )
            }
    )
    ResponseEntity<WishListCreateResponse> create(@PathVariable("lectureId") Long lectureId);

    @Operation(
            summary = "즐겨찾기 삭제",
            description = "유저 ID와 강의 ID를 통해 즐겨찾기를 삭제 합니다.",
            responses = @ApiResponse(responseCode = "204")
    )
    ResponseEntity<Void> delete(@PathVariable("lectureId") Long lectureId);
}
