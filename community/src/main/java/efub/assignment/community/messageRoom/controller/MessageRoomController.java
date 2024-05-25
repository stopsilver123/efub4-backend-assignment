package efub.assignment.community.messageRoom.controller;

import efub.assignment.community.messageRoom.dto.MessageRoomListResponseDto;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomResponseDto;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messageRooms")
@RequiredArgsConstructor
public class MessageRoomController {
    private final MessageRoomService messageRoomService;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public MessageRoomResponseDto createMessageRoom(@Valid @RequestBody MessageRoomRequestDto dto) {
        return messageRoomService.createMessageRoom(dto);
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Long isExistMessageRooms(@RequestParam Long senderId, @RequestParam Long receiverId, @RequestParam Long postId) {
        return messageRoomService.findMessageRoom(senderId, receiverId, postId);
    }

    @GetMapping("/members/{memberId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<MessageRoomListResponseDto> getMessageRooms(@PathVariable("memberId") Long memberId) {
        return messageRoomService.findMessageRoomsByMemberId(memberId);
    }

    @DeleteMapping("/{messageRoomId}")
    public String deleteMessageRoom(@PathVariable("messageRoomId") Long messageRoomId, @RequestParam(name = "memberId") Long memberId) {
        messageRoomService.deleteMessageRoom(memberId, messageRoomId);
        return "성공적으로 삭제되었습니다.";
    }

}
