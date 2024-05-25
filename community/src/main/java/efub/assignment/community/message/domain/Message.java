package efub.assignment.community.message.domain;

import efub.assignment.community.global.entity.BaseTimeEntity;
import efub.assignment.community.member.domain.Member;
import efub.assignment.community.messageRoom.domain.MessageRoom;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", updatable = false)
    private Long messageId;

    @Setter
    @ManyToOne
    @JoinColumn(name = "message_room_id", updatable = false)
    private MessageRoom messageRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private Member sender;

    @Column(nullable = false)
    private String message;

    @Builder
    public Message(MessageRoom messageRoom, Member sender, Member receiver, String message) {
        this.messageRoom = messageRoom;
        this.sender = sender;
        this.message = message;
    }

}
