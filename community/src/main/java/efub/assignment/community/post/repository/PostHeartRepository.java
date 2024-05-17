package efub.assignment.community.post.repository;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {
    Integer countByBoardAndPost(Board board, Post post);
    List<PostHeart> findByWriter(Member member);
    boolean existsByWriterAndBoardAndPost(Member member, Board board, Post post);
    Optional<PostHeart> findByWriterAndBoardAndPost(Member member, Board board, Post post);
}
