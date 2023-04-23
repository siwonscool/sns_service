package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;
    private final static String TABLE = "MemberNicknameHistory";

    private final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory.builder()
                .id(resultSet.getLong("id"))
                .memberId(resultSet.getLong("memberId"))
                .nickname(resultSet.getString("nickname"))
                .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                .build();

    public List<MemberNicknameHistory> findAllByMemberId(Long memberId){
        var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE);
        var params = new MapSqlParameterSource().addValue("memberId",memberId);
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory){
        if (memberNicknameHistory.getId() == null){
            return insert(memberNicknameHistory);
        }
        throw new UnsupportedOperationException("MemberNicknameHistory 갱신을 지원하지 않습니다.");
    }

    public MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("MemberNicknameHistory")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return MemberNicknameHistory.builder()
                .id(id)
                .memberId(memberNicknameHistory.getMemberId())
                .nickname(memberNicknameHistory.getNickname())
                .build();
    }
}
