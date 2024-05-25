package efub.assignment.community.message.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.message.domain.Message;
import efub.assignment.community.message.dto.MessageListResponseDto;
import efub.assignment.community.message.dto.MessageRequestDto;
import efub.assignment.community.message.dto.MessageResponseDto;
import efub.assignment.community.message.repository.MessageRepository;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.messageRoom.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final MessageRoomService messageRoomService;
    private final MemberService memberService;

    public MessageResponseDto createMessage(MessageRequestDto dto) {
        MessageRoom messageRoom = messageRoomService.findById(dto.getMessageRoomId());
        Member sender = memberService.findMemberById(dto.getSenderId());
        Message message = dto.toEntity(messageRoom, sender);
        messageRoom.addMessage(message);
        Message savedMessage = messageRepository.save(message);
        return MessageResponseDto.from(savedMessage);

    }

    public List<MessageListResponseDto> getMessagesByMessageRoomId(Long messageRoomId, Long requesterId) {
        MessageRoom messageRoom = messageRoomService.findById(messageRoomId);
        Member requester = memberService.findMemberById(requesterId);

        return messageRepository.findByMessageRoom(messageRoom).stream()
                .map(message -> MessageListResponseDto.from(message, requesterId))
                .collect(Collectors.toList());
    }
}
