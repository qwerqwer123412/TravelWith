package Moap.TravelWith.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
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

    private LocalDate sendTime;


}
