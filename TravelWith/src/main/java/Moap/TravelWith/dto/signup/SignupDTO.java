package Moap.TravelWith.dto.signup;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.enumer.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupDTO {
    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private LocalDate birthDate;
    private Gender gender;

    public static Member dtoToEntity(SignupDTO signupDTO){
        return Member.builder()
                .email(signupDTO.getEmail())
                .password(signupDTO.getPassword())
                .name(signupDTO.getName())
                .phoneNumber(signupDTO.getPhoneNumber())
                .birthDate(signupDTO.getBirthDate())
                .gender(signupDTO.getGender()).build();

        }

}


