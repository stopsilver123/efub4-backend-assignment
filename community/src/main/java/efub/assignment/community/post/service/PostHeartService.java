package efub.assignment.community.post.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.domain.PostHeart;
import efub.assignment.community.post.repository.PostHeartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final BoardService boardService;
    private final PostService postService;
    private final MemberService memberService;

    public void create(Long memberId, Long boardId, Long postId) {
        Member member = memberService.findMemberById(memberId);
        Board board = boardService.findBoardById(boardId);
        Post post = postService.findPostById(postId);

        if (isExistsByWriterAndBoardAndPost(member, board, post)){
            throw new RuntimeException("이미 좋아요를 누른 게시물입니다.");
        }

        PostHeart postHeart = PostHeart.builder()
                .member(member)
                .board(board)
                .post(post)
                .build();

        postHeartRepository.save(postHeart);
    }

    public void delete(Long memberId, Long boardId, Long postId) {
        Member member = memberService.findMemberById(memberId);
        Board board = boardService.findBoardById(boardId);
        Post post = postService.findPostById(postId);
        PostHeart postLike = postHeartRepository.findByWriterAndBoardAndPost(member, board, post)
                .orElseThrow(()-> new RuntimeException("좋아요가 존재하지 않습니다."));
        postHeartRepository.delete(postLike);
    }

    public boolean isHeart(Long memberId, Board board, Post post) {
        Member member = memberService.findMemberById(memberId);
        return isExistsByWriterAndBoardAndPost(member, board, post);
    }

    @Transactional(readOnly = true)
    public boolean isExistsByWriterAndBoardAndPost(Member member, Board board, Post post) {
        return postHeartRepository.existsByWriterAndBoardAndPost(member, board, post);
    }

    @Transactional(readOnly = true)
    public Integer countPostHeart(Board board, Post post){
        Integer count = postHeartRepository.countByBoardAndPost(board, post);
        return count;
    }
}
