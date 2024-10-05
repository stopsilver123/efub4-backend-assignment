package efub.assignment.community.test;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
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
public class PostRepositoryTest {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestEntityManager testEntityManager;
    private Board board;
    private Post post;
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

        testEntityManager.persist(member);

        board = Board.builder()
                .member(member)
                .boardName("맛집 게시판")
                .description("맛집 추천 게시판입니다.")
                .notice("글은 꼭 사진과 함께 올려주세요.")
                .hostNickname(member.getNickname())
                .build();

        testEntityManager.persist(board);

        post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(true)
                .content("영미김밥 싸고 맛있어요.")
                .build();

        testEntityManager.persist(post);

    }
    @Test
    @DisplayName("게시글 생성")
    public void savePostTest(){
        Post savedPost = postRepository.save(post);

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getContent()).isEqualTo("영미김밥 싸고 맛있어요.");
    }

    @Test
    @DisplayName("게시글 조회")
    public void findPostTest(){
        Post post1 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(true)
                .content("first test post")
                .build();

        Post post2 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(false)
                .content("second test post")
                .build();

        testEntityManager.persist(post1);
        testEntityManager.persist(post2);

        List<Post> posts = postRepository.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts.get(0).getContent()).isEqualTo("first test post");
        assertThat(posts.get(1).getContent()).isEqualTo("second test post");
    }

    @Test
    @DisplayName("특정 게시글 존재 여부")
    public void existsByIdTest(){
        Post post = Post.builder()
                .board(board)
                .member(member)
                .anonymous(true)
                .content("테스트 게시글입니다.")
                .build();

        Post savedPost = postRepository.save(post);
        boolean exists = postRepository.existsById(savedPost.getPostId());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("게시글 삭제")
    public void deletePostTest(){
        Post post1 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(true)
                .content("삭제 테스트용 게시글1")
                .build();

        Post post2 = Post.builder()
                .board(board)
                .member(member)
                .anonymous(false)
                .content("삭제 테스트용 게시글2")
                .build();

        testEntityManager.persist(post1);
        testEntityManager.persist(post2);

        postRepository.deleteAll();

        assertThat(postRepository.findAll()).isEmpty();
    }
}
