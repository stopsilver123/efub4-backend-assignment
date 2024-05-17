package efub.assignment.community.comment.controller;

import efub.assignment.community.comment.dto.AccountInfoRequestDto;
import efub.assignment.community.comment.service.CommentHeartService;
import efub.assignment.community.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class CommentHeartController {
    private final CommentService commentService;
    private final CommentHeartService commentHeartService;

    // 댓글 좋아요 등록
    @PostMapping("/comments/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createCommentHeart(
                                     @PathVariable("commentId") final Long commentId,
                                     @RequestBody @Valid final AccountInfoRequestDto requestDto) {
        commentHeartService.create(commentId, requestDto);
        return "좋아요를 눌렀습니다.";
    }

    // 댓글 좋아요 삭제
    @DeleteMapping("/comments/{commentId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deleteCommentHeart(
                                     @PathVariable("commentId") final Long commentId,
                                     @RequestParam("memberId") final Long memberId) {
        commentHeartService.delete(commentId, memberId);
        return "좋아요가 취소되었습니다.";
    }
}
