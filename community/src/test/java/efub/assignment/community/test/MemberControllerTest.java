package efub.assignment.community.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.member.dto.SignUpRequestDto;
import efub.assignment.community.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class MemberControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MemberRepository memberRepository;

    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("creatMember : 회원 가입 성공")
    public void createMember() throws Exception {
        //given
        final String url = "/members";
        final String email = "ewha123@ewha.ac.kr";
        final String password = "ewha123!";
        final String nickname = "ewha123";
        final String university = "EWHA";
        final String studentId = "2133444";
        final SignUpRequestDto requestDto = createDefaultSignUpRequestDto(email, password, nickname, university, studentId);

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId").isNotEmpty())
                .andExpect(jsonPath("$.email").value(email))
                .andExpect(jsonPath("$.nickname").value(nickname))
                .andExpect(jsonPath("$.university").value(university))
                .andExpect(jsonPath("$.studentId").value(studentId));
    }

    private SignUpRequestDto createDefaultSignUpRequestDto(String email, String password, String nickname, String university, String studentId) {
        return SignUpRequestDto.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .university(university)
                .studentId(studentId)
                .build();
    }

    @Test
    @DisplayName("getMember: 회원 정보 조회 실패")
    public void getMember() throws Exception {
        //given
        final String url = "/members/{memberId}";
        final Long memberId = 10000L;

        //when
        ResultActions resultActions = mockMvc.perform(get(url, memberId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId").value(memberId));
    }
}