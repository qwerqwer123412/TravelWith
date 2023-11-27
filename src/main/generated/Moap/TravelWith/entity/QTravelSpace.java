package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTravelSpace is a Querydsl query type for TravelSpace
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTravelSpace extends EntityPathBase<TravelSpace> {

    private static final long serialVersionUID = -606033358L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTravelSpace travelSpace1 = new QTravelSpace("travelSpace1");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatchPosting matchPosting;

    public final StringPath travelSpace = createString("travelSpace");

    public QTravelSpace(String variable) {
        this(TravelSpace.class, forVariable(variable), INITS);
    }

    public QTravelSpace(Path<? extends TravelSpace> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTravelSpace(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTravelSpace(PathMetadata metadata, PathInits inits) {
        this(TravelSpace.class, metadata, inits);
    }

    public QTravelSpace(Class<? extends TravelSpace> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matchPosting = inits.isInitialized("matchPosting") ? new QMatchPosting(forProperty("matchPosting")) : null;
    }

}

