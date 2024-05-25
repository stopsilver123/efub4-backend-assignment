package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRoomRequestDto {
    @NotNull(message = "쪽지를 보내는 사람의 id는 필수입니다.")
    private Long senderId;

    @NotNull(message = "쪽지를 받는 사람의 id는 필수입니다.")
    private Long receiverId;

    @NotBlank(message = "쪽지 내용은 필수입니다.")
    private String message;

    @NotNull(message = "쪽지가 시작된 글의 id는 필수입니다.")
    private Long postId;

    public MessageRoomRequestDto(Long senderId, Long receiverId, String message, Long postId) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.postId = postId;
    }
    public MessageRoom toEntity(Member sender, Member receiver, Post post) {
        return MessageRoom.builder()
                .sender(sender)
                .receiver(receiver)
                .post(post)
                .message(message)
                .build();
    }
}
