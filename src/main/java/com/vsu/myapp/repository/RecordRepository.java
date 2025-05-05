package com.vsu.myapp.repository;

import com.vsu.myapp.Entity.Profile;
import com.vsu.myapp.Entity.Record;
import com.vsu.myapp.request.CreateRecordRequest;
import com.vsu.myapp.utils.CryptoUtils;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class RecordRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public RecordRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Long create(@Valid Record record) {
        return namedParameterJdbcTemplate.queryForObject(
                "INSERT INTO record(site_address,login,password,profile_id) Values(:site_address,:login,:password,:profile_id) RETURNING id ",
                Map.of(
                        "site_address", record.getSiteAddress(),
                        "login", record.getLogin(),
                        "password", record.getPassword(),
                        "profile_id", record.getProfileId()
                ),
                Long.class
        );
    }

    public Long update(@Valid Record record) {
        return namedParameterJdbcTemplate.queryForObject(
                "UPDATE record SET site_address = :site_address, login = :login,password = :password, profile_id =:profile_id WHERE id = :id",
                Map.of(
                        "site_address", record.getSiteAddress(),
                        "login", record.getLogin(),
                        "password", record.getPassword(),
                        "profile_id", record.getProfileId(),
                        "id", record.getId()

                ),Long.class
        );
    }

    public Optional<Record> findById(Long id) {
        return Optional.ofNullable(namedParameterJdbcTemplate.query(
                "SELECT id, site_address, login, password, profile_id FROM record WHERE id = :id",
                Map.of("id",id),
                rs -> {
                    if (rs.next()) {
                        return new Record(
                                rs.getLong("id"),
                                rs.getLong("profile_id"),
                                rs.getString("site_address"),
                                rs.getString("login"),
                                rs.getString("password")
                        );
                    }
                    return null;
                }
        ));
    }

    public Optional<List<Record>> findByAddress(Long profileId, String address) {

        List<Record> records = namedParameterJdbcTemplate.query(
                "SELECT id, site_address, login, password, profile_id FROM record " +
                        "WHERE profile_id = :profileId AND site_address LIKE :address",
                Map.of(
                        "profileId", profileId,
                        "address", "%" + address + "%"
                ),
                (rs, rowNum) -> new Record(
                        rs.getLong("id"),
                        rs.getLong("profile_id"),
                        rs.getString("site_address"),
                        rs.getString("login"),
                        rs.getString("password")
                )
        );

        return records.isEmpty() ? Optional.empty() : Optional.of(records);
    }

    public int deleteById(Long id) {
        return namedParameterJdbcTemplate.update(
                "DELETE FROM record WHERE id = :id",
                Map.of("id", id)
        );
    }
}
