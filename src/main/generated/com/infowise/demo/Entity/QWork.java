package com.infowise.demo.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWork is a Querydsl query type for Work
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWork extends EntityPathBase<Work> {

    private static final long serialVersionUID = 120638624L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWork work = new QWork("work");

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final NumberPath<Float> gongSoo = createNumber("gongSoo", Float.class);

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final QMember member;

    public final NumberPath<Integer> month = createNumber("month", Integer.class);

    public final QProject project;

    public final NumberPath<Integer> week = createNumber("week", Integer.class);

    public final NumberPath<Integer> year = createNumber("year", Integer.class);

    public QWork(String variable) {
        this(Work.class, forVariable(variable), INITS);
    }

    public QWork(Path<? extends Work> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWork(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWork(PathMetadata metadata, PathInits inits) {
        this(Work.class, metadata, inits);
    }

    public QWork(Class<? extends Work> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.project = inits.isInitialized("project") ? new QProject(forProperty("project")) : null;
    }

}

