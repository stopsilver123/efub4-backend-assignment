package efub.assignment.community.board.domain;

import efub.assignment.community.board.dto.board.BoardResponseDto;
import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id", updatable = false)
    private Long boardId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false, length = 50)
    private String boardName;

    @Column(nullable = false, length = 1000)
    private String description;

    @Column(nullable = false, length = 100)
    private String notice;

    @Column(nullable = false, length = 50)
    private String hostNickname;

    @Builder
    public Board(Member member, String boardName, String description, String notice, String hostNickname) {
        this.member = member;
        this.boardName = boardName;
        this.description = description;
        this.notice = notice;
        this.hostNickname = member.getNickname();
    }

    public void update(Member member) {
        this.member = member;
        this.hostNickname = member.getNickname();
    }
}
