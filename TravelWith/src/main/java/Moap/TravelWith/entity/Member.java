package Moap.TravelWith.entity;


import Moap.TravelWith.enumer.Gender;
import Moap.TravelWith.enumer.Nationality;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String profileImg;
    private LocalDate birthDate;

    private Gender gender;


    //생략 가능..
    //private Nationality nationality;

}

