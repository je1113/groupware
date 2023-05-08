package com.infowise.demo.Entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -326080407L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final StringPath hp = createString("hp");

    public final NumberPath<Long> idx = createNumber("idx", Long.class);

    public final StringPath name = createString("name");

    public final StringPath pw = createString("pw");

    public final EnumPath<com.infowise.demo.Enum.RoleType> roleType = createEnum("roleType", com.infowise.demo.Enum.RoleType.class);

    public final StringPath team = createString("team");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

