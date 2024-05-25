package efub.assignment.community.message.dto;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.post.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageRequestDto {
    @NotNull(message = "쪽지방 id는 필수입니다.")
    private Long messageRoomId;

    @NotNull(message = "쪽지를 보내는 사람의 id는 필수입니다.")
    private Long senderId;

    @NotBlank(message = "쪽지 내용은 필수입니다.")
    private String message;

    public MessageRequestDto(Long messageRoomId, Long senderId, String message) {
        this.messageRoomId = messageRoomId;
        this.senderId = senderId;
        this.message = message;
    }

    public Message toEntity(MessageRoom messageRoom, Member sender) {
        return Message.builder()
                .messageRoom(messageRoom)
                .sender(sender)
                .message(this.message)
                .build();
    }
}
