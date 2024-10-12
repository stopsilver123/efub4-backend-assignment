package efub.assignment.community.post.dto.post;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PostRequestDto {
    @NotNull(message = "게시판 ID는 필수입니다.")
    private Long boardId;

    @NotNull(message = "계정 ID는 필수입니다.")
    private Long memberId;

    @NotNull(message = "익명 여부 설정은 필수입니다.")
    private Boolean anonymous;

    @NotBlank(message = "내용은 필수입니다.")
    private String content;

    public Post toEntity(Member member, Board board) {
        return Post.builder()
                .board(board)
                .member(member)
                .anonymous(anonymous)
                .content(content)
                .build();
    }
}
