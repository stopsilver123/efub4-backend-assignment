package efub.assignment.community.post.domain;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.dto.post.PostRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id", updatable = false)
    private Long postId;

    @ManyToOne
    @JoinColumn(name = "board_id", updatable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "member_id", updatable = false)
    private Member member;

    @Column(nullable = false)
    private boolean anonymous;

    @Column(nullable = false, length = 1000)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostHeart> postHeartList = new ArrayList<>();

    @Builder
    public Post(Board board, Member member, boolean anonymous, String content) {
        this.board = board;
        this.member = member;
        this.anonymous = anonymous;
        this.content = content;
    }


    public void update(PostRequestDto dto) {
        this.content = dto.getContent();
    }
}
