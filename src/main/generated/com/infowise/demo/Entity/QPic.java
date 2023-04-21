package com.infowise.demo.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPic is a Querydsl query type for Pic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPic extends EntityPathBase<Pic> {

    private static final long serialVersionUID = 2082094619L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPic pic = new QPic("pic");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMember Member;

    public final QProject project;

    public QPic(String variable) {
        this(Pic.class, forVariable(variable), INITS);
    }

    public QPic(Path<? extends Pic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPic(PathMetadata metadata, PathInits inits) {
        this(Pic.class, metadata, inits);
    }

    public QPic(Class<? extends Pic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.Member = inits.isInitialized("Member") ? new QMember(forProperty("Member")) : null;
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project")) : null;
    }

}

