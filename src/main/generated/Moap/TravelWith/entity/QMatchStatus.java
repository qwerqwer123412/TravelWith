package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMatchStatus is a Querydsl query type for MatchStatus
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMatchStatus extends EntityPathBase<MatchStatus> {

    private static final long serialVersionUID = 1374762557L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMatchStatus matchStatus = new QMatchStatus("matchStatus");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatchPosting matchPosting;

    public final QMember member;

    public final EnumPath<Moap.TravelWith.enumer.MatchRole> role = createEnum("role", Moap.TravelWith.enumer.MatchRole.class);

    public QMatchStatus(String variable) {
        this(MatchStatus.class, forVariable(variable), INITS);
    }

    public QMatchStatus(Path<? extends MatchStatus> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMatchStatus(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMatchStatus(PathMetadata metadata, PathInits inits) {
        this(MatchStatus.class, metadata, inits);
    }

    public QMatchStatus(Class<? extends MatchStatus> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.matchPosting = inits.isInitialized("matchPosting") ? new QMatchPosting(forProperty("matchPosting")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

