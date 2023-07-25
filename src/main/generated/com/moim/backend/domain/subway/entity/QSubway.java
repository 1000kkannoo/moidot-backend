package com.moim.backend.domain.subway.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubway is a Querydsl query type for Subway
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSubway extends EntityPathBase<Subway> {

    private static final long serialVersionUID = -397006096L;

    public static final QSubway subway = new QSubway("subway");

    public final NumberPath<java.math.BigDecimal> latitude = createNumber("latitude", java.math.BigDecimal.class);

    public final NumberPath<java.math.BigDecimal> longitude = createNumber("longitude", java.math.BigDecimal.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> subwayId = createNumber("subwayId", Long.class);

    public QSubway(String variable) {
        super(Subway.class, forVariable(variable));
    }

    public QSubway(Path<? extends Subway> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubway(PathMetadata metadata) {
        super(Subway.class, metadata);
    }

}

