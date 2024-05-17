package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostHeart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시판은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "게시글은 필수로 입력되어야 합니다.")
    @JoinColumn(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "작성자는 필수로 입력되어야 합니다.")
    @JoinColumn(name = "member_id", updatable = false)
    private Member writer;

    @Builder
    public PostHeart(Board board, Post post, Member member) {
        this.board = board;
        this.post = post;
        this.writer = member;
    }
}
