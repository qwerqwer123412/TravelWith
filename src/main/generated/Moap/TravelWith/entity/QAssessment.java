package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAssessment is a Querydsl query type for Assessment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAssessment extends EntityPathBase<Assessment> {

    private static final long serialVersionUID = -1353002532L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAssessment assessment = new QAssessment("assessment");

    public final QMember evaluator;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMatchPosting matchPosting;

    public final NumberPath<Integer> points = createNumber("points", Integer.class);

    public final QMember receiver;

    public QAssessment(String variable) {
        this(Assessment.class, forVariable(variable), INITS);
    }

    public QAssessment(Path<? extends Assessment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAssessment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAssessment(PathMetadata metadata, PathInits inits) {
        this(Assessment.class, metadata, inits);
    }

    public QAssessment(Class<? extends Assessment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.evaluator = inits.isInitialized("evaluator") ? new QMember(forProperty("evaluator")) : null;
        this.matchPosting = inits.isInitialized("matchPosting") ? new QMatchPosting(forProperty("matchPosting")) : null;
        this.receiver = inits.isInitialized("receiver") ? new QMember(forProperty("receiver")) : null;
    }

}

