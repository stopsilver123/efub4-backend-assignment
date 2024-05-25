package efub.assignment.community.message.dto;

import efub.assignment.community.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageResponseDto {
    private Long messageRoomId;
    private Long senderId;
    private String message;
    private LocalDateTime createdDate;

    public MessageResponseDto(Long messageRoomId, Long senderId, String message, LocalDateTime createdDate) {
        this.messageRoomId = messageRoomId;
        this.senderId = senderId;
        this.message = message;
        this.createdDate = createdDate;
    }
    public static MessageResponseDto from(Message message) {
        return new MessageResponseDto(
                message.getMessageRoom().getMessageRoomId(),
                message.getSender().getMemberId(),
                message.getMessage(),
                message.getCreatedDate()
        );
    }
}
