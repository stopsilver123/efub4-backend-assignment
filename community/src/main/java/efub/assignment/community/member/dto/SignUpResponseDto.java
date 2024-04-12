package efub.assignment.community.member.dto;

import efub.assignment.community.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignUpResponseDto {
    private Long memberId;
    private String email;
    private String nickname;
    private String university;
    private String studentId;
    @Builder
    public SignUpResponseDto(Long memberId, String email, String nickname, String university, String studentId){
        this.memberId = memberId;
        this.email = email;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
    }
}