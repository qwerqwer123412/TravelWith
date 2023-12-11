package Moap.TravelWith.entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue
    private Long id;


    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Member sender;


    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    private String title;

    private String contents;

    private LocalDateTime sendTime;

    @Builder
    public Message(Member sender, Member receiver, String title, String contents, LocalDateTime sendTime) {
        this.sender = sender;
        this.receiver = receiver;
        this.title = title;
        this.contents = contents;
        this.sendTime = sendTime;
    }
}
