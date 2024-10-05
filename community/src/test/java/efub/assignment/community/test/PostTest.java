package efub.assignment.community.test;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.post.PostRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {
    private Post post;
    private Board board;
    private Member member;

    @BeforeEach
    void setUp() {
        member = Member.builder()
                .email("jieun@ewha.ac.kr")
                .encodedPassword("password123")
                .nickname("stopsilver")
                .university("EWHA")
                .studentId("2134567")
                .build();


        board = Board.builder()
                .member(member)
                .boardName("맛집 게시판")
                .description("맛집 추천 게시판입니다.")
                .notice("글은 꼭 사진과 함께 올려주세요.")
                .hostNickname(member.getNickname())
                .build();

        post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(true)
                .content("영미김밥 싸고 맛있어요.")
                .build();
    }

    @Test
    @DisplayName("게시글 생성")
    public void testCreatePost() {
        assertNotNull(post);
        assertEquals(board, post.getBoard());
        assertEquals(member, post.getMember());
        assertTrue(post.isAnonymous());
        assertEquals("영미김밥 싸고 맛있어요.", post.getContent());
    }

    @Test
    @DisplayName("게시글 수정")
    public void testUpdatePost() {
        String newContent = "사실 우리 학교 근처에는 맛집이 없어요.";
        PostRequestDto dto = new PostRequestDto(board.getBoardName(), board.getHostNickname(), true, newContent);

        post.update(dto);

        assertEquals(newContent, post.getContent());
    }

}