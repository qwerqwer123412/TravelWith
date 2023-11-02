package Moap.TravelWith.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.criteria.CriteriaBuilder;
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


    private String location;

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



}
