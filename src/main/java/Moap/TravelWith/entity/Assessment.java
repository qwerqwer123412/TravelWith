package Moap.TravelWith.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Assessment {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "EVALUATOR_ID")
    private Member evaluator;

    @ManyToOne
    @JoinColumn(name = "RECEIVER_ID")
    private Member receiver;

    private Integer points;

    @ManyToOne
    @JoinColumn(name = "MATCH_POSTING_ID")
    private MatchPosting matchPosting;
    @Builder
    public Assessment(Member evaluator, Member receiver, Integer points, MatchPosting matchPosting) {
        this.evaluator = evaluator;
        this.receiver = receiver;
        this.points = points;
        this.matchPosting = matchPosting;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
