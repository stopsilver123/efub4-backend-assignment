package efub.assignment.community.post.service;


import efub.assignment.community.board.domain.Board;
import efub.assignment.community.board.service.BoardService;
import efub.assignment.community.exception.CustomDeleteException;
import efub.assignment.community.exception.ErrorCode;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.post.PostRequestDto;
import efub.assignment.community.post.repository.PostRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberService memberService;
    private final BoardService boardService;

    public Post createPost(PostRequestDto dto) {
        Member member = memberService.findMemberById(dto.getMemberId());
        Board board = boardService.findBoardById(dto.getBoardId());
        Post post = dto.toEntity(member, board);
        Post savedPost = postRepository.save(post);
        return savedPost;
    }

    @Transactional
    public List<Post> findAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts;
    }


    public long countAllPosts() {
        return postRepository.count();
    }

    @Transactional
    public Post findPostById(long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시글을 찾을 수 없습니다. id = " + postId));
        return post;
    }

    public Long updatePost(long postId, PostRequestDto dto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시글을 찾을 수 없습니다. id = " + postId));
        post.update(dto);
        return post.getPostId();
    }

    public void deletePost(long postId, Long memberId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id를 가진 게시글을 찾을 수 없습니다. id = " + postId));
        if (memberId!=post.getMember().getMemberId()){
            throw new CustomDeleteException(ErrorCode.PERMISSION_REJECTED_USER);
        }
        postRepository.delete(post);
    }
}
