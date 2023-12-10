package Moap.TravelWith.dto.match_response;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.enumer.Gender;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Builder
@Data
public class MemberInfoDTO {


    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String  profileImg;
    private LocalDate birthDate;
    private Gender gender;


    public static MemberInfoDTO entityToDto(Member member){
        return MemberInfoDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .profileImg(member.getProfileImg())
                .birthDate(member.getBirthDate())
                .gender(member.getGender())
                .build();
    }


}
