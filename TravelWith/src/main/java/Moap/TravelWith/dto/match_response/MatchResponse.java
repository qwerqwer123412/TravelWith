package Moap.TravelWith.dto.match_response;


import Moap.TravelWith.entity.MatchPosting;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data

public class MatchResponse {

    private Long matchId;

    private LocalDate startDate;

    private LocalDate endDate;

    private String title;

    private String contents;

    private String hostName;
    //정원
    private Integer capacityNum;


    //예를 들면 서울특별시 중구 봉래동1가 111-3
    private String mainTravelSpace;

    @Builder
    public MatchResponse(Long matchId, LocalDate startDate, LocalDate endDate, String title, String contents, Integer capacityNum, String mainTravelSpace) {
        this.matchId = matchId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.contents = contents;
        this.capacityNum = capacityNum;
        this.mainTravelSpace = mainTravelSpace;
    }

    public static MatchResponse entityToDTO(MatchPosting matchPosting){
        return MatchResponse.builder()
                .matchId(matchPosting.getId())
                .startDate(matchPosting.getStartDate())
                .endDate(matchPosting.getEndDate())
                .title(matchPosting.getTitle())
                .contents(matchPosting.getContents())
                .capacityNum(matchPosting.getNumOfPeoples())
                .mainTravelSpace(matchPosting.getMainTravelSpace()).build();

    }

}
