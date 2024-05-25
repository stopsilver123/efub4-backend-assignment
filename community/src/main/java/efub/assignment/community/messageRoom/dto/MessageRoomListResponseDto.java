package efub.assignment.community.messageRoom.dto;

import efub.assignment.community.messageRoom.domain.MessageRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageRoomListResponseDto {
    private Long messageRoomId;
    private String message;
    private LocalDateTime createdDate;

    public MessageRoomListResponseDto(Long messageRoomId, String message, LocalDateTime createdDate) {
        this.messageRoomId = messageRoomId;
        this.message = message;
        this.createdDate = createdDate;
    }


    public static MessageRoomListResponseDto from(MessageRoom messageRoom) {
        return new MessageRoomListResponseDto(
                messageRoom.getMessageRoomId(),
                messageRoom.getMessage(),
                messageRoom.getCreatedDate()
        );
    }
}

