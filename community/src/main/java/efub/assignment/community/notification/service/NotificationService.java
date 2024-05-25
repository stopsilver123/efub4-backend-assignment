package efub.assignment.community.notification.service;

import efub.assignment.community.notification.domain.Notification;
import efub.assignment.community.notification.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    public void createCommentNotification(String boardName, String content) {
        Notification notification = Notification.builder()
                .type("댓글")
                .boardName(boardName)
                .content(content)
                .build();
        notificationRepository.save(notification);
    }

    public void createMessageRoomNotification() {
        Notification notification = Notification.builder()
                .type("쪽지방")
                .content("새로운 쪽지방이 생겼어요")
                .build();
        notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }
}
