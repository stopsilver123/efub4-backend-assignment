package efub.assignment.community.message.dto;

import efub.assignment.community.message.domain.Message;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MessageListResponseDto {
    private Long messageRoomId;
    private Long senderId;
    private String message;
    private LocalDateTime createdDate;
    private Boolean isSentByRequester;

    public MessageListResponseDto(Long messageRoomId, Long senderId, String message, LocalDateTime createdDate, Boolean isSentByRequester) {
        this.messageRoomId = messageRoomId;
        this.senderId = senderId;
        this.message = message;
        this.createdDate = createdDate;
        this.isSentByRequester = isSentByRequester;
    }
    public static MessageListResponseDto from(Message message, Long requesterId) {
        return new MessageListResponseDto(
                message.getMessageRoom().getMessageRoomId(),
                message.getSender().getMemberId(),
                message.getMessage(),
                message.getCreatedDate(),
                message.getSender().getMemberId().equals(requesterId)
        );

    }

}
