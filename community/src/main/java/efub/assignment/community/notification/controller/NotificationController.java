package efub.assignment.community.notification.controller;

import efub.assignment.community.notification.dto.NotificationResponseDto;
import efub.assignment.community.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<NotificationResponseDto> getAllNotifications() {
        return notificationService.getAllNotifications().stream()
                .map(NotificationResponseDto::from)
                .collect(Collectors.toList());
    }

}
