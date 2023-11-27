package Moap.TravelWith.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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



}
