package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageRoomResponseDto {
    private Long messageRoomId;
    private Long postId;
    private Long senderId;
    private Long receiverId;
    private String message;
    private LocalDateTime createdDate;

    public MessageRoomResponseDto(Long messageRoomId, Long postId, Long senderId, Long receiverId, String message, LocalDateTime createdDate) {
        this.messageRoomId = messageRoomId;
        this.postId = postId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.message = message;
        this.createdDate = createdDate;
    }


    public static MessageRoomResponseDto from(MessageRoom messageRoom) {
        return new MessageRoomResponseDto(
                messageRoom.getMessageRoomId(),
                messageRoom.getPost().getPostId(),
                messageRoom.getSender().getMemberId(),
                messageRoom.getReceiver().getMemberId(),
                messageRoom.getMessage(),
                messageRoom.getCreatedDate()
        );
    }
}
