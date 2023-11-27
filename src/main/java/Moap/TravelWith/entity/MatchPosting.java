package Moap.TravelWith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class MatchPosting {

    @Id
    @GeneratedValue
    private Long id;

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

    @Builder
    public MatchPosting(LocalDate startDate, LocalDate endDate, String title, String contents, Integer travelExpenses, Integer numOfPeoples, Boolean isAccommodationTogether, Boolean isDiningTogether, String mainTravelSpace) {

        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.contents = contents;
        this.travelExpenses = travelExpenses;
        this.numOfPeoples = numOfPeoples;
        this.isAccommodationTogether = isAccommodationTogether;
        this.isDiningTogether = isDiningTogether;
        this.mainTravelSpace = mainTravelSpace;
    }
}
