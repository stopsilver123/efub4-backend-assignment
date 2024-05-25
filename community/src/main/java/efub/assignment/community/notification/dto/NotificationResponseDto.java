package efub.assignment.community.notification.dto;

import efub.assignment.community.notification.domain.Notification;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class NotificationResponseDto {
    private Long notificationId;
    private String type;
    private String boardName;
    private String content;
    private LocalDateTime createdDate;

    public NotificationResponseDto(Notification notification) {
        this.notificationId = notification.getNotificationId();
        this.type = notification.getType();
        this.boardName = notification.getBoardName();
        this.content = notification.getContent();
        this.createdDate = notification.getCreatedDate();
    }

    public static NotificationResponseDto from(Notification notification) {
        return new NotificationResponseDto(notification);
    }
}
