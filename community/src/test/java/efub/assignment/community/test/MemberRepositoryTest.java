package efub.assignment.community.test;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE, connection = EmbeddedDatabaseConnection.H2)
public class MemberRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("멤버 생성")
    public void saveMemberTest(){
        Member member = Member.builder()
                .email("jieun@ewha.ac.kr")
                .encodedPassword("password123")
                .nickname("stopsilver")
                .university("EWHA")
                .studentId("2134567")
                .build();

        testEntityManager.persist(member);

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember).isNotNull();
        assertThat(savedMember.getEmail()).isEqualTo("jieun@ewha.ac.kr");
    }

    @Test
    @DisplayName("멤버 조회")
    public void findMemberTest(){
        Member member1 = Member.builder()
                .email("jieun@ewha.ac.kr")
                .encodedPassword("password123")
                .nickname("stopsilver")
                .university("EWHA")
                .studentId("2134567")
                .build();
        Member member2 = Member.builder()
                .email("oooo@ewha.ac.kr")
                .encodedPassword("psldkfsdfsd3")
                .nickname("wow")
                .university("EWHA")
                .studentId("23444555")
                .build();

        testEntityManager.persist(member1);
        testEntityManager.persist(member2);

        List<Member> members = memberRepository.findAll();

        assertThat(members).hasSize(2);
        assertThat(members.get(0).getEmail()).isEqualTo("jieun@ewha.ac.kr");
        assertThat(members.get(0).getNickname()).isEqualTo("wow");
    }

    @Test
    @DisplayName("이메일 존재 여부 확인")
    public void existsByEmailTest(){

        Member member2 = Member.builder()
                .email("oooo@ewha.ac.kr")
                .encodedPassword("psldkfsdfsd3")
                .nickname("wow")
                .university("EWHA")
                .studentId("23444555")
                .build();

        testEntityManager.persist(member2);

        boolean exists = memberRepository.existsByEmail("jieun@ewha.ac.kr");
        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("멤버 삭제")
    public void deleteMemberTest(){
        Member member1 = Member.builder()
                .email("jieun@ewha.ac.kr")
                .encodedPassword("password123")
                .nickname("stopsilver")
                .university("EWHA")
                .studentId("2134567")
                .build();

        Member member2 = Member.builder()
                .email("oooo@ewha.ac.kr")
                .encodedPassword("psldkfsdfsd3")
                .nickname("wow")
                .university("EWHA")
                .studentId("23444555")
                .build();

        testEntityManager.persist(member1);
        testEntityManager.persist(member2);

        memberRepository.deleteAll();
        assertThat(memberRepository.findAll()).isEmpty();
    }
}
