package efub.assignment.community.comment.service;

import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentRequestDto;
import efub.assignment.community.comment.repository.CommentRepository;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final PostService postService;
    private final CommentRepository commentRepository;
    private final BoardService boardService;

    public Comment saveComment(Long boardId, Long postId, CommentRequestDto requestDto){
        Member writer = memberService.findMemberById(requestDto.getMemberId());
        Board board = boardService.findBoardById(boardId);
        Post post = postService.findPostById(postId);

        Comment comment = Comment.builder()
                .content(requestDto.getContent())
                .writer(writer)
                .board(board)
                .post(post)
                .build();
        commentRepository.save(comment);

        return comment;
    }
    //게시글별 댓글 목록 조회
    public List<Comment> findPostCommentList(Long boardId, Long postId) {
        Board board = boardService.findBoardById(boardId);
        Post post = postService.findPostById(postId);
        return commentRepository.findAllByBoardAndPost(board, post);
    }

    public List<Comment> findMemberCommentList(Member writer) {
        return commentRepository.findAllByWriter(writer);
    }

    public Comment updateComment(Long commentId, CommentRequestDto requestDto) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->new EntityNotFoundException("해당 id를 가진 Comment를 찾을 수 없습니다. id=" + commentId));
        Member writer = memberService.findMemberById(requestDto.getMemberId());
        if (!comment.getWriter().equals(writer)) {
            throw new IllegalStateException("작성자가 아닙니다. 댓글 수정 권한이 없습니다.");
        }
        comment.update(requestDto.getContent());
        commentRepository.save(comment);
        return comment;
    }

    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 Comment를 찾을 수 없습니다. id=" + commentId));
        if (!comment.getWriter().equals(memberService.findMemberById(memberId))) {
            throw new IllegalStateException("작성자가 아닙니다. 댓글 삭제 권한이 없습니다.");
        }
        commentRepository.delete(comment);
    }
}
