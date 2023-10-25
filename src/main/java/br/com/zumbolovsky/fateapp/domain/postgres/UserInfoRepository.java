package br.com.zumbolovsky.fateapp.domain.postgres;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    @Query("SELECT userInfo FROM UserInfo userInfo " +
            "JOIN FETCH userInfo.roles role " +
            "WHERE role.name = :role")
    List<UserInfo> findAllByRole(@Param("role") String role);

    @Query("SELECT userInfo FROM UserInfo userInfo " +
            "JOIN FETCH userInfo.roles role " +
            "WHERE userInfo.user = :user " +
            "AND userInfo.password = :password")
    Optional<UserInfo> findOneByUserAndPassword(
        @Param("user") String user,
        @Param("password") String password);

    @Query("SELECT userInfo FROM UserInfo userInfo " +
            "JOIN FETCH userInfo.roles role " +
            "WHERE userInfo.user = :user")
    Optional<UserInfo> findOneByUser(@Param("user") String user);
}