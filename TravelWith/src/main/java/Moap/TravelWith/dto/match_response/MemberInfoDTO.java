package Moap.TravelWith.dto.match_response;

import Moap.TravelWith.entity.Member;
import Moap.TravelWith.enumer.Gender;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class MemberInfoDTO {


    private Long id;
    private String name;
    private byte[] profileImg;

    private Gender gender;


    public static MemberInfoDTO entityToDto(Member member){
        return MemberInfoDTO.builder().id(member.getId())
                .name(member.getName())
                .gender(member.getGender())
                .profileImg(member.getProfileImg()).build();
    }
}
