package com.vsu.myapp.repository;

import com.vsu.myapp.Entity.Profile;
import com.vsu.myapp.request.CreateProfileRequest;
import com.vsu.myapp.utils.PasswordHasher;
import jakarta.validation.Valid;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
public class ProfileRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProfileRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Optional<Profile> findByLogin(String login) {
        return namedParameterJdbcTemplate.query(
                "select id, login, password, salt from profile where login = :login",
                Map.of("login", login),
                rs -> {
                    if (rs.next()) {
                        return Optional.of( new Profile(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("salt")));
                    }
                    return Optional.empty();
                });
    }

    public int create(@Valid CreateProfileRequest createProfileRequest) {
        String salt = PasswordHasher.generateSalt();
        return namedParameterJdbcTemplate.update("INSERT INTO PROFILE(login,password,salt) Values(:login,:password,:salt)",
        Map.of(
                "login",createProfileRequest.getUserName(),
                "password",PasswordHasher.hashPassword( createProfileRequest.getPassword(),salt),
                "salt", salt
        ));
    }

    public Optional<Profile> findByID(long profileId) {
        return namedParameterJdbcTemplate.query(
                "select id, login, password, salt from profile where id = :id",
                Map.of("id", profileId),
                rs -> {
                    if (rs.next()) {
                        return Optional.of( new Profile(
                                rs.getLong("id"),
                                rs.getString("login"),
                                rs.getString("password"),
                                rs.getString("salt")));
                    }
                    return Optional.empty();
                });
    }
}
