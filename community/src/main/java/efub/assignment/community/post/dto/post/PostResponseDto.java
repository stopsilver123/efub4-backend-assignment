package efub.assignment.community.post.dto.post;

import efub.assignment.community.post.domain.Post;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostResponseDto {
    private Long boardId;
    private Long postId;
    private String writerName;
    private boolean anonymous;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public static PostResponseDto from(Post post, String writerNickname) {
        String writerName = (writerNickname != null && !writerNickname.isEmpty())
                ? writerNickname
                : (post.isAnonymous() ? "익명" : post.getMember().getNickname());

        return new PostResponseDto(
                post.getBoard().getBoardId(),
                post.getPostId(),
                writerName,
                post.isAnonymous(),
                post.getContent(),
                post.getCreatedDate(),
                post.getModifiedDate()
        );
    }
}
