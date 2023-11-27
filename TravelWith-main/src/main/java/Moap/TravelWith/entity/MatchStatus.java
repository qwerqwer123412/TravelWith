package Moap.TravelWith.entity;

import Moap.TravelWith.enumer.MatchRole;
import jakarta.persistence.*;
import lombok.Builder;
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

    @ManyToOne
    @JoinColumn(name = "MATCH_POSTING_ID")
    private MatchPosting matchPosting;

    private MatchRole role;

    public static MatchStatus makeEntity(Member member, MatchPosting matchPosting, MatchRole role) {
        MatchStatus matchStatus = new MatchStatus();
        matchStatus.member = member;
        matchStatus.matchPosting = matchPosting;
        matchStatus.role = role;
        return matchStatus;
    }
}
