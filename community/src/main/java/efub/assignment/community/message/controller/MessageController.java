package efub.assignment.community.message.controller;

import efub.assignment.community.message.dto.MessageListResponseDto;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import efub.assignment.community.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageResponseDto createMessage(@RequestBody MessageRequestDto dto) {
        return messageService.createMessage(dto);
    }
    @GetMapping("/{messageRoomId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MessageListResponseDto> getMessagesByRoomId(@PathVariable Long messageRoomId, @RequestParam Long requesterId) {
        return messageService.getMessagesByMessageRoomId(messageRoomId, requesterId);
    }
}
