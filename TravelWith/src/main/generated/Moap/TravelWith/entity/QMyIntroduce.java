package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMyIntroduce is a Querydsl query type for MyIntroduce
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMyIntroduce extends EntityPathBase<MyIntroduce> {

    private static final long serialVersionUID = 2003698937L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMyIntroduce myIntroduce = new QMyIntroduce("myIntroduce");

    public final StringPath contents = createString("contents");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public final StringPath title = createString("title");

    public QMyIntroduce(String variable) {
        this(MyIntroduce.class, forVariable(variable), INITS);
    }

    public QMyIntroduce(Path<? extends MyIntroduce> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMyIntroduce(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMyIntroduce(PathMetadata metadata, PathInits inits) {
        this(MyIntroduce.class, metadata, inits);
    }

    public QMyIntroduce(Class<? extends MyIntroduce> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

