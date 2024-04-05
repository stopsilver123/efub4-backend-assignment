package efub.assignment.community.member.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.lang.model.element.NestingKind;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", updatable = false)
    private Long memberId;

    @Column(nullable = false, length = 60)
    private String email;

    @Column(nullable = false)
    private String encodedPassword;

    @Column(nullable = false, length = 16)
    private String nickname;

    @Column(nullable = false, length = 16)
    private String university;

    @Column(nullable = false, length = 16)
    private String studentId;

    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public Member(String email, String encodedPassword, String nickname, String university, String studentId){
        this.email = email;
        this.encodedPassword = encodedPassword;
        this.nickname = nickname;
        this.university = university;
        this.studentId = studentId;
        this.status = MemberStatus.REGISTERED;
    }

    public void updateMember(String nickname) {
        this.nickname = nickname;
    }

    public void withdrawMember() {
        this.status = MemberStatus.UNREGISTERED;
    }
}
