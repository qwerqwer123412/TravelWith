package Moap.TravelWith.dto;

import Moap.TravelWith.entity.MatchPosting;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MatchPostingWrite {

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


    public static MatchPosting entityToDTO(MatchPostingWrite matchPostingWrite){


        return MatchPosting.builder()
                .startDate(matchPostingWrite.startDate)
                .endDate(matchPostingWrite.endDate)
                .title(matchPostingWrite.title)
                .contents(matchPostingWrite.contents)
                .travelExpenses(matchPostingWrite.travelExpenses)
                .numOfPeoples(matchPostingWrite.numOfPeoples)
                .isAccommodationTogether(matchPostingWrite.isAccommodationTogether)
                .isDiningTogether(matchPostingWrite.isDiningTogether)
                .mainTravelSpace(matchPostingWrite.getMainTravelSpace()).build();
    }
}
