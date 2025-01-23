package stackpot.stackpot.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import stackpot.stackpot.converter.FeedConverter;
import stackpot.stackpot.domain.Feed;
import stackpot.stackpot.domain.enums.Category;
import stackpot.stackpot.domain.enums.Role;
import stackpot.stackpot.service.FeedService;
import stackpot.stackpot.web.dto.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;
    private final FeedConverter feedConverter;


    @Operation(summary = "피드 미리보기 api")
    @GetMapping("")
    public ResponseEntity<FeedResponseDto.FeedResponse> getPreViewFeeds(
            @RequestParam(value = "category", required = false, defaultValue = "ALL") Category category,
            @RequestParam(value = "sort", required = false, defaultValue = "new") String sort,
            @RequestParam(value = "cursor", required = false) String cursor,
            @RequestParam(value = "limit", defaultValue = "10") int limit) {

        FeedResponseDto.FeedResponse response = feedService.getPreViewFeeds(category, sort, cursor, limit);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "feed 작성 api")
    @PostMapping("")
    public ResponseEntity<?> signup(@Valid @RequestBody FeedRequestDto.createDto requset,
                                    BindingResult bindingResult) {
        // 유효성 검사 실패 처리
        if (bindingResult.hasErrors()) {
            // 에러 메시지 수집
            List<String> errors = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errors);
        }
        // 정상 처리
        Feed feed = feedService.createFeed(requset);
        FeedResponseDto.FeedDto response = feedConverter.feedDto(feed, 0,0);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @Operation(summary = "feed 좋아요 추가 api")
    @PostMapping("/{feedId}/like")
    public ResponseEntity<?> toggleLike(@PathVariable Long feedId) {

        // 좋아요 토글
        boolean isLiked = feedService.toggleLike(feedId);
        return ResponseEntity.ok(Map.of(
                "liked", isLiked,
                "message", isLiked ? "좋아요를 눌렀습니다." : "좋아요를 취소했습니다."
        ));
    }

    @Operation(summary = "feed 저장하기 api")
    @PostMapping("/{feedId}/save")
    public ResponseEntity<?> toggleSave(@PathVariable Long feedId) {

        // 좋아요 토글
        boolean isSaved = feedService.toggleSave(feedId);
        return ResponseEntity.ok(Map.of(
                "liked", isSaved,
                "message", isSaved ? "좋아요를 눌렀습니다." : "좋아요를 취소했습니다."
        ));
    }

}
