package Moap.TravelWith.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MyIntroduce {


    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    private String title;
    private String Contents;


    //여행 선호 사항
    //관심사

}
