package com.spring.bugtrackerbe.repositories.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class PaginationJdbcRepositoryImpl implements PaginationJdbcRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public PaginationJdbcRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional(readOnly = true)
    @Override
    public <T> Page<T> execPaginateQuery(
            String selectSql,
            String countSql,
            SqlParameterSource parameterSource,
            Pageable pageable,
            RowMapper<T> rowMapper
    ) {
        final String paginateSql = selectSql
                + " " + this.getOrderClause(pageable.getSort().stream().toList())
                + " " + this.getOffsetClause(pageable);

        final List<T> currentPageItems = this.namedParameterJdbcTemplate.query(
                paginateSql, parameterSource, rowMapper);

        long totalItems = currentPageItems.size();
        if (pageable.getPageNumber() > 0 || pageable.getPageSize() == currentPageItems.size()) {
            totalItems = this.execCountQuery(countSql, parameterSource);
        }

        return new PageImpl<>(currentPageItems, pageable, totalItems);
    }

    @Transactional(readOnly = true)
    @Override
    public long execCountQuery(String sql, SqlParameterSource parameterSource) {
        final Long totalItems = this.namedParameterJdbcTemplate
                .queryForObject(sql, parameterSource, Long.class);
        assert totalItems != null;
        return totalItems;
    }

    private String getOrderClause(List<Sort.Order> sortOrders) {
        final StringBuilder orderClauseBuilder = new StringBuilder("ORDER BY ");
        for (int i = 0; i < sortOrders.size(); i++) {
            final Sort.Order sortOrder = sortOrders.get(i);
            if (i > 0) {
                orderClauseBuilder.append(", ");
            }
            orderClauseBuilder.append(String.format("%s %s",
                    sortOrder.getProperty(), sortOrder.isDescending() ? "DESC" : "ASC"));
        }
        return orderClauseBuilder.toString();
    }

    private String getOffsetClause(Pageable pageable) {
        return String.format("OFFSET %d ROWS FETCH FIRST %d ROWS ONLY",
                pageable.getPageNumber() * pageable.getPageSize(), pageable.getPageSize());
    }
}
