package efub.assignment.community.board.dto.board;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BoardRequestDto {
    @NotBlank(message = "계정 ID는 필수입니다.")
    private String memberId;

    @NotBlank(message = "게시판 주인은 필수입니다.")
    private String hostNickname;

    @NotBlank(message = "게시판 이름은 필수입니다.")
    private String boardName;

    @NotBlank(message = "게시판 설명은 필수입니다.")
    private String description;

    @NotBlank(message = "게시판 공지는 필수입니다.")
    private String notice;

    public Board toEntity(Member member) {
        return Board.builder()
                .member(member)
                .hostNickname(hostNickname)
                .boardName(this.boardName)
                .description(this.description)
                .notice(this.notice)
                .build();
    }
}
