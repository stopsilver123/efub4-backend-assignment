package efub.assignment.community.post.dto.post;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentResponseDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PostCommentResponseDto {
    private Long boardId;
    private Long postId;
    private List<CommentResponseDto> postCommentList;
    private Long count;

    public static PostCommentResponseDto of(Long boardId, Long postId, List<Comment> commentList) {
        return PostCommentResponseDto.builder()
                .boardId(boardId)
                .postId(postId)
                .postCommentList(commentList.stream().map(CommentResponseDto::of).collect(Collectors.toList()))
                .count((long) commentList.size())
                .build();
    }
}
