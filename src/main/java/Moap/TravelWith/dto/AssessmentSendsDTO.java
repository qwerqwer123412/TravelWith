package Moap.TravelWith.dto;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
@Data
public class AssessmentSendsDTO {

    Long memberId;
    Long assessedMemberId;
    Long matchId;
    Integer points;

}
