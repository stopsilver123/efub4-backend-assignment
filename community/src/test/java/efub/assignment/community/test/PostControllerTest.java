package efub.assignment.community.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import efub.assignment.community.CommunityApplication;
import efub.assignment.community.post.dto.post.PostRequestDto;
import efub.assignment.community.post.repository.PostRepository;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "/data.sql")
@ActiveProfiles("test")
@ContextConfiguration(classes = CommunityApplication.class)
@TestPropertySource(locations = "classpath:application-test.yml")
class PostControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected PostRepository postRepository;

    @BeforeEach
    public void mockMvcSetup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .build();
    }

    @Test
    @DisplayName("createPost : 게시글 생성 성공")
    public void createPost() throws Exception {
        //given
        final String url = "/boards/{boardId}/posts";
        final Long boardId = 1L;
        final Long memberId = 1L;
        final boolean anonymous = true;
        final String content = "test post content";
        final PostRequestDto requestDto = createDefaultPostRequestDto(boardId, memberId, anonymous, content);

        //when
        final String requestBody = objectMapper.writeValueAsString(requestDto);

        ResultActions resultActions = mockMvc.perform(post(url, boardId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody));

        //then
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.boardId").value(boardId))
                .andExpect(jsonPath("$.postId").isNotEmpty())
                .andExpect(jsonPath("$.anonymous").value(anonymous))
                .andExpect(jsonPath("$.content").value(content));

    }

    private PostRequestDto createDefaultPostRequestDto(Long boardId, Long memberId, boolean anonymous, String content) {
        return new PostRequestDto(boardId, memberId, anonymous, content);
    }

    @Test
    @DisplayName("getPost : 게시글 상세 조회 실패")
    public void getPost() throws Exception {
        //given
        final String url = "/boards/{boardId}/posts/{postId}";
        final Long boardId = 1L;
        final Long postId = 200L;

        //when
        ResultActions resultActions = mockMvc.perform(get(url, boardId, postId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.boardId").value(boardId))
                .andExpect(jsonPath("$.postId").value(postId));
    }


}