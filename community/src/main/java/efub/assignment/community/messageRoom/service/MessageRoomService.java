package efub.assignment.community.messageRoom.service;

import efub.assignment.community.member.domain.Member;
import efub.assignment.community.member.service.MemberService;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import efub.assignment.community.messageRoom.dto.MessageRoomListResponseDto;
import efub.assignment.community.messageRoom.dto.MessageRoomRequestDto;
import efub.assignment.community.messageRoom.dto.MessageRoomResponseDto;
import efub.assignment.community.messageRoom.repository.MessageRoomRepository;
import efub.assignment.community.post.domain.Post;
import efub.assignment.community.post.service.PostService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageRoomService {
    private final MessageRoomRepository messageRoomRepository;
    public final MemberService memberService;
    public final PostService postService;
    private Object MessageRoomListResponseDto;

    public MessageRoomResponseDto createMessageRoom(MessageRoomRequestDto requestDto){
        Member sender = memberService.findMemberById(requestDto.getSenderId());
        Member receiver = memberService.findMemberById(requestDto.getReceiverId());
        Post post = postService.findPostById(requestDto.getPostId());
        MessageRoom messageRoom = requestDto.toEntity(sender, receiver, post);
        MessageRoom savedMessageRoom = messageRoomRepository.save(messageRoom);
        return MessageRoomResponseDto.from(savedMessageRoom);
    }

    public Long findMessageRoom(Long senderId, Long receiverId, Long postId){
        Member sender = memberService.findMemberById(senderId);
        Member receiver = memberService.findMemberById(receiverId);
        Post post = postService.findPostById(postId);

        MessageRoom messageRoom = messageRoomRepository.findBySenderAndReceiverAndPost(sender, receiver, post);

        if (messageRoom == null) {
            throw new EntityNotFoundException("message room not found");
        }
        return messageRoom.getMessageRoomId();
    }

    public List<MessageRoomListResponseDto> findMessageRoomsByMemberId(Long memberId){
        Member member = memberService.findMemberById(memberId);
        List<MessageRoom> messageRooms = messageRoomRepository.findBySenderOrReceiver(member, member);
        return messageRooms.stream().map(efub.assignment.community.messageRoom.dto.MessageRoomListResponseDto::from).collect(Collectors.toList());


    }


    public void deleteMessageRoom(Long memberId, Long messageRoomId) {
        MessageRoom messageRoom = messageRoomRepository.findById(messageRoomId).orElseThrow(() -> new EntityNotFoundException ("해당 id의 쪽지방을 찾을 수 없습니다."));

        if (!messageRoom.getReceiver().getMemberId().equals(memberId)) {
            throw new SecurityException("쪽지방 삭제 권한이 없습니다.");
        }

        messageRoomRepository.deleteById(messageRoomId);
    }

    public MessageRoom findById(Long messageRoomId) {
        return  messageRoomRepository.findById(messageRoomId)
                .orElseThrow(() -> new EntityNotFoundException("해당 id의 쪽지방이 없습니다."));
    }
}
