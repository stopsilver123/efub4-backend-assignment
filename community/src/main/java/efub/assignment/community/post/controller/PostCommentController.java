package efub.assignment.community.post.controller;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.comment.service.CommentService;
import efub.assignment.community.post.dto.post.PostCommentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/posts/{postId}/comments")
public class PostCommentController {

    private final CommentService commentService;

    //게시글에 댓글 생성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("boardId") Long boardId,
                                                            @PathVariable("postId") Long postId,
                                                            @RequestBody CommentRequestDto requestDto){

        Comment comment = commentService.saveComment(boardId, postId, requestDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommentResponseDto.of(comment));

    }

    //게시글별 댓글 목록 조회
    @GetMapping
    public ResponseEntity<PostCommentResponseDto> getPostCommentList(@PathVariable("boardId") Long boardId,
                                                                     @PathVariable("postId") Long postId){
        List<Comment> commentList = commentService.findPostCommentList(boardId, postId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(PostCommentResponseDto.of(boardId, postId, commentList));
    }

    //게시글에 댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable("commentId") Long commentId,
                                                            @RequestBody CommentRequestDto requestDto){
        Comment comment = commentService.updateComment(commentId, requestDto);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommentResponseDto.of(comment));
    }

    //게시글에 댓글 삭제
    @DeleteMapping("/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId,
                                @RequestParam(name = "memberId") Long memberId){
        commentService.deleteComment(commentId, memberId);

        return "성공적으로 삭제되었습니다.";
    }
}
