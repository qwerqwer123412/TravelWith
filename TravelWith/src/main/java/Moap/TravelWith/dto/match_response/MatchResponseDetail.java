package Moap.TravelWith.dto.match_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class MatchResponseDetail {
    MemberInfoDTO host;
    List<MemberInfoDTO> participants;
    private Long matchId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String contents;
    private Integer travelExpenses;
    private Integer numOfPeoples;
    private Boolean isAccommodationTogether;
    private Boolean isDiningTogether;
    //예를 들면 서울특별시 중구 봉래동1가 111-3
    private String mainTravelSpace;
    //private String imgURL;


}
