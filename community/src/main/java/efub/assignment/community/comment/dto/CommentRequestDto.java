package efub.assignment.community.comment.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
    private Long boardId;
    private Long postId;
    private Long memberId;
    private String content;
}
