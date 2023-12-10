package Moap.TravelWith.dto;

import Moap.TravelWith.enumer.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
// 로그인회원 인식용, 테스트
public class MemberDTO {

    private String email;
    private String name;
    private String phoneNumber;
    private String profileImg;
    private LocalDate birthDate;
    private Gender gender;


}
