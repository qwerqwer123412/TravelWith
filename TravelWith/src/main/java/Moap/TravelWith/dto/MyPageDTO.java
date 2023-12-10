package Moap.TravelWith.dto;

import Moap.TravelWith.dto.match_response.MemberInfoDTO;
import Moap.TravelWith.enumer.Gender;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@NoArgsConstructor
public class MyPageDTO {
    private MemberInfoDTO memberInfoDTO;
    private String myIntroduceTitle;
    private String myIntroduceContents;
    @Builder
    public MyPageDTO(MemberInfoDTO memberInfoDTO, String myIntroduceTitle, String myIntroduceContents) {
        this.memberInfoDTO = memberInfoDTO;
        this.myIntroduceTitle = myIntroduceTitle;
        this.myIntroduceContents = myIntroduceContents;
    }
}
