package com.payflow.payflow.domain.common;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseOnlyCreated is a Querydsl query type for BaseOnlyCreated
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseOnlyCreated extends EntityPathBase<BaseOnlyCreated> {

    private static final long serialVersionUID = 1573292661L;

    public static final QBaseOnlyCreated baseOnlyCreated = new QBaseOnlyCreated("baseOnlyCreated");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public QBaseOnlyCreated(String variable) {
        super(BaseOnlyCreated.class, forVariable(variable));
    }

    public QBaseOnlyCreated(Path<? extends BaseOnlyCreated> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseOnlyCreated(PathMetadata metadata) {
        super(BaseOnlyCreated.class, metadata);
    }

}

