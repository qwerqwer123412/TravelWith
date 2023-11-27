package Moap.TravelWith.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TravelSpace {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MATCH_POSTING_ID")
    private MatchPosting matchPosting;

    private String travelSpace;


}
