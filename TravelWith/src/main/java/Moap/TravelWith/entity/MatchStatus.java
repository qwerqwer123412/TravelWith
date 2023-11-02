package Moap.TravelWith.entity;

import Moap.TravelWith.enumer.MatchRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class MatchStatus {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @OneToOne
    @JoinColumn(name = "MATCH_POSTING_ID")
    private MatchPosting matchPosting;

    private MatchRole role;

}
