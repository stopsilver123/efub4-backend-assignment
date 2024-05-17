package efub.assignment.community.member.dto;

import efub.assignment.community.comment.domain.Comment;
import efub.assignment.community.comment.dto.CommentResponseDto;
import efub.assignment.community.member.domain.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberCommentResponseDto {
    private String writerNickname;
    private List<CommentResponseDto> memberCommentList;
    private Long count;


    public static MemberCommentResponseDto of(Member writer, List<Comment> commentList) {
        return MemberCommentResponseDto.builder()
                .writerNickname(writer.getNickname())
                .memberCommentList(commentList.stream().map(CommentResponseDto::of).collect(Collectors.toList()))
                .count((long) commentList.size())
                .build();
    }
}
