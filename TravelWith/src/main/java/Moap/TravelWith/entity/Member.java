package Moap.TravelWith.entity;


import Moap.TravelWith.enumer.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
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
    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private String profileImg = "https://tobehonest.s3.ap-northeast-2.amazonaws.com/default.jpeg";
    private LocalDate birthDate;

    private Gender gender;


    //생략 가능..
    //private Nationality nationality;

    public void changeProfileImg(String url){
        this.profileImg = url;
    }
    @Builder
    public Member(Long id, String email, String password, String name, String phoneNumber, LocalDate birthDate, Gender gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}

