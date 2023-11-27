package Moap.TravelWith.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QLoginCheck is a Querydsl query type for LoginCheck
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLoginCheck extends EntityPathBase<LoginCheck> {

    private static final long serialVersionUID = -951264711L;

    public static final QLoginCheck loginCheck = new QLoginCheck("loginCheck");

    public final StringPath email = createString("email");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public QLoginCheck(String variable) {
        super(LoginCheck.class, forVariable(variable));
    }

    public QLoginCheck(Path<? extends LoginCheck> path) {
        super(path.getType(), path.getMetadata());
    }

    public QLoginCheck(PathMetadata metadata) {
        super(LoginCheck.class, metadata);
    }

}

