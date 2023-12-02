package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.dto.request.FilterUsersRequestDTO;
import com.spring.bugtrackerbe.dto.response.UserResponseDTO;
import com.spring.bugtrackerbe.enums.UserRole;
import com.spring.bugtrackerbe.repositories.common.PaginationJdbcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

@Repository
public class UserJdbcRepositoryImpl implements UserJdbcRepository {

    private final PaginationJdbcRepository paginationJdbcRepository;

    @Autowired
    public UserJdbcRepositoryImpl(
            PaginationJdbcRepository paginationJdbcRepository
    ) {
        this.paginationJdbcRepository = paginationJdbcRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<UserResponseDTO> filterUsers(FilterUsersRequestDTO filterRequestDTO) {
        final MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        String sql = "FROM users u WHERE u.delete_flag = false AND u.role = 'USER'";

        final String nameOrEmailPattern = filterRequestDTO.getNameOrEmailPattern();
        if (!(nameOrEmailPattern == null || nameOrEmailPattern.trim().isEmpty())) {
            sql += " AND (u.name LIKE :nameOrEmailPattern OR u.email LIKE :nameOrEmailPattern)";
            parameterSource.addValue("nameOrEmailPattern",
                    "%" + nameOrEmailPattern + "%", Types.VARCHAR);
        }
        final Boolean status = filterRequestDTO.getStatus();
        if (status != null) {
            sql += " AND enable_flag = :status";
            parameterSource.addValue("status", status, Types.BOOLEAN);
        }

        final Sort sort = Sort.by(
                filterRequestDTO.isSortDescending() ? Sort.Direction.DESC : Sort.Direction.ASC,
                String.format("u.%s", filterRequestDTO.getSortField()));
        final Pageable pageable = PageRequest.of(
                filterRequestDTO.getPageNumber(), filterRequestDTO.getPageSize(), sort);
        final String selectSql =
                "SELECT u.id, u.email, u.name, u.role, u.enable_flag, u.created_at, u.updated_at " + sql;
        final String countSql = "SELECT COUNT(u.id) " + sql;
        return this.paginationJdbcRepository.execPaginateQuery(
                selectSql,
                countSql,
                parameterSource,
                pageable,
                this::rowToUserResponseDTO
        );
    }

    private UserResponseDTO rowToUserResponseDTO(ResultSet rs, int rowNumber) throws SQLException {
        return new UserResponseDTO(
                rs.getInt("id"),
                rs.getString("email"),
                rs.getString("name"),
                UserRole.valueOf(rs.getString("role")),
                rs.getBoolean("enable_flag"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
