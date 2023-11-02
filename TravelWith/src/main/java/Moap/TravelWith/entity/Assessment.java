package Moap.TravelWith.entity;

import jakarta.persistence.*;
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
}
