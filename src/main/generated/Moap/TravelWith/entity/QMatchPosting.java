package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMatchPosting is a Querydsl query type for MatchPosting
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchPosting extends EntityPathBase<MatchPosting> {

    private static final long serialVersionUID = 1173888599L;

    public static final QMatchPosting matchPosting = new QMatchPosting("matchPosting");

    public final StringPath contents = createString("contents");

    public final DatePath<java.time.LocalDate> endDate = createDate("endDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isAccommodationTogether = createBoolean("isAccommodationTogether");

    public final BooleanPath isDiningTogether = createBoolean("isDiningTogether");

    public final StringPath mainTravelSpace = createString("mainTravelSpace");

    public final NumberPath<Integer> numOfPeoples = createNumber("numOfPeoples", Integer.class);

    public final DatePath<java.time.LocalDate> startDate = createDate("startDate", java.time.LocalDate.class);

    public final StringPath title = createString("title");

    public final NumberPath<Integer> travelExpenses = createNumber("travelExpenses", Integer.class);

    public QMatchPosting(String variable) {
        super(MatchPosting.class, forVariable(variable));
    }

    public QMatchPosting(Path<? extends MatchPosting> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMatchPosting(PathMetadata metadata) {
        super(MatchPosting.class, metadata);
    }

}

