package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    final private NamedParameterJdbcTemplate jdbcTemplate;
    private final static String TABLE = "Member";

    private final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member.builder()
                .id(resultSet.getLong("id"))
                .email(resultSet.getString("email"))
                .nickname(resultSet.getString("nickname"))
                .birthday(resultSet.getObject("birthday", LocalDate.class))
                .createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
                .build();
    public Optional<Member> findById(Long id){
        var sql = String.format("SELECT * FROM %s WHERE id = :id",TABLE);
        var param = new MapSqlParameterSource()
                .addValue("id",id);
        var member = jdbcTemplate.queryForObject(sql,param,rowMapper);
        return Optional.ofNullable(member);
    }
    public Member save(Member member){
        /*
        * member id 를 보고 갱신 또는 삽입을 정함
        * 반환값은 id 를 담아서 반환한다.
        * */
        if (member.getId() == null){
            return insert(member);
        }else {
            return update(member);
        }
    }

    public Member insert(Member member){
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
                .withTableName("Member")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return Member.builder()
                .id(id)
                .email(member.getEmail())
                .nickname(member.getNickname())
                .birthday(member.getBirthday())
                .build();
    }

    public Member update(Member member){
        var sql = String.format("UPDATE %s SET email = :email, nickname = :nickname, birthday = :birthday  WHERE id = :id", TABLE);
        SqlParameterSource params = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, params);
        return member;
    }
}
