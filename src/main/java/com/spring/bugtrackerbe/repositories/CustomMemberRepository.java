package com.spring.bugtrackerbe.repositories;

import com.spring.bugtrackerbe.dto.MemberResponseDTO;
import com.spring.bugtrackerbe.enums.ProjectRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Repository
public class CustomMemberRepository implements ICustomMemberRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CustomMemberRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MemberResponseDTO> findMemberResponseDTOByProjectId(Integer projectId) {
        final String sql = "SELECT m.id, m.project_id, m.user_id, u.email, u.name, m.role, m.created_at, m.updated_at " +
                "FROM members m " +
                "INNER JOIN users u ON m.user_id = u.id " +
                "WHERE m.project_id = ?";

        return this.jdbcTemplate.query(sql,
                this::rowToMemberResponseDTO,
                new SqlParameterValue(Types.INTEGER, projectId)
        );
    }

    private MemberResponseDTO rowToMemberResponseDTO(ResultSet rs, int rowNumber) throws SQLException {
        return new MemberResponseDTO(
                rs.getInt("id"),
                rs.getInt("project_id"),
                rs.getInt("user_id"),
                rs.getString("email"),
                rs.getString("name"),
                ProjectRole.valueOf(rs.getString("role")),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getTimestamp("updated_at").toLocalDateTime()
        );
    }
}
