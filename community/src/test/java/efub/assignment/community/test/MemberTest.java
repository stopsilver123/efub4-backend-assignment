package efub.assignment.community.test;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.domain.MemberStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    private Member member;

    @BeforeEach
    public void setUp() {
        member = Member.builder()
                .email("jieun@ewha.ac.kr")
                .encodedPassword("password123")
                .nickname("stopsilver")
                .university("EWHA")
                .studentId("2134567")
                .build();
    }

    @Test
    @DisplayName("계정 상태 전환")
    public void withdrawMemberTest(){
        member.withdrawMember();
        assertEquals(MemberStatus.UNREGISTERED, member.getStatus());
    }

    @Test
    @DisplayName("멤버 닉네임 수정")
    public void memberUpdateTest(){
        String newNickname = "hahaha";
        member.updateMember(newNickname);
        assertEquals("stopsilver", member.getNickname());
    }

}