package efub.assignment.community.notification.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id", updatable = false)
    private Long notificationId;

    @Column(nullable = false)
    private String type;

    @Column(nullable = true)
    private String boardName;

    @Column(nullable = false)
    private String content;

    @Builder
    public Notification(String type, String boardName, String content) {
        this.type = type;
        this.boardName = boardName;
        this.content = content;
    }
}
