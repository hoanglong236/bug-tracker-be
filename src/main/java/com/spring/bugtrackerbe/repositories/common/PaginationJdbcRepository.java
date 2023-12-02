package com.spring.bugtrackerbe.repositories.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public interface PaginationJdbcRepository {

    <T> Page<T> execPaginateQuery(
            String selectSql,
            String countSql,
            SqlParameterSource parameterSource,
            Pageable pageable,
            RowMapper<T> rowMapper
    );

    long execCountQuery(String sql, SqlParameterSource parameterSource);
}
