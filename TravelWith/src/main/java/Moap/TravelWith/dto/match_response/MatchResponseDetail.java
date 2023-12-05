package Moap.TravelWith.dto.match_response;

import Moap.TravelWith.entity.MatchPosting;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Builder
@Data
@AllArgsConstructor
public class MatchResponseDetail {
    MatchPosting matchPosting;
    MemberInfoDTO host;
    List<MemberInfoDTO> participants;


}
