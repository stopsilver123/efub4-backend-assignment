package efub.assignment.community.post.controller;

import efub.assignment.community.comment.dto.HeartRequestDto;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.dto.post.AllPostsResponseDto;
import efub.assignment.community.post.dto.post.PostRequestDto;
import efub.assignment.community.post.dto.post.PostResponseDto;
import efub.assignment.community.post.service.PostHeartService;
import efub.assignment.community.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/{boardId}/posts")
public class PostController {
    private final PostService postService;
    private final PostHeartService postHeartService;

    //게시글 생성
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public PostResponseDto createPost(@RequestBody @Valid final PostRequestDto dto) {
        Post savedPost = postService.createPost(dto);
        return PostResponseDto.from(savedPost);
    }
    //게시글 전체 조회
    @GetMapping
    public AllPostsResponseDto getAllPosts(){
        List<PostResponseDto> list = new ArrayList<>();
        List<Post> posts = postService.findAllPosts();
        for (Post post : posts) {
            PostResponseDto dto = PostResponseDto.from(post);
            list.add(dto);
        }

        long count = postService.countAllPosts();

        return new AllPostsResponseDto(list, count);
    }
    //게시글 1개 조회
    @GetMapping("/{postId}")
    public PostResponseDto getOnePost(@PathVariable final long postId) {
        Post post = postService.findPostById(postId);
        return PostResponseDto.from(post);
    }
    //게시글 수정
    @PutMapping("/{postId}")
    public PostResponseDto updatePost(@PathVariable final long postId, @RequestBody @Valid final PostRequestDto dto) {
        Long id = postService.updatePost(postId, dto);
        Post post = postService.findPostById(id);
        return PostResponseDto.from(post);
    }
    //게시글 삭제
    @DeleteMapping("/{postId}")
    public String deletePost(@PathVariable final long postId, @RequestParam(name = "memberId") Long memberId) {
        postService.deletePost(postId, memberId);

        return "성공적으로 삭제되었습니다.";
    }

    //게시글 좋아요 등록
    @PostMapping("/{postId}/hearts")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createPostHeart(@PathVariable("boardId") final Long boardId, @PathVariable("postId") final Long postId, @RequestBody final HeartRequestDto requestDto){
        postHeartService.create(requestDto.getMemberId(), boardId, postId);
        return "좋아요를 눌렀습니다.";
    }
    //게시글 좋아요 삭제
    @DeleteMapping("/{postId}/hearts")
    @ResponseStatus(value = HttpStatus.OK)
    public String deletePostHeart(@PathVariable("boardId") final Long boardId, @PathVariable("postId") final Long postId, @RequestParam("memberId") final Long memberId){
        postHeartService.delete(memberId, boardId, postId);
        return "좋아요가 취소되었습니다.";
    }
}
