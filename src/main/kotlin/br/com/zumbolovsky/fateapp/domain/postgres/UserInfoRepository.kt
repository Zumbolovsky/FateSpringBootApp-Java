package br.com.zumbolovsky.fateapp.domain.postgres

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserInfoRepository : JpaRepository<UserInfo, Int?> {

    @Query("""SELECT userInfo FROM UserInfo userInfo
        JOIN FETCH userInfo.roles role 
        WHERE role.name = :role""")
    fun findAllByRole(@Param("role") role: String): List<UserInfo>

    @Query("""SELECT userInfo FROM UserInfo userInfo
        JOIN FETCH userInfo.roles role 
        WHERE userInfo.user = :user 
        AND userInfo.password = :password""")
    fun findOneByUserAndPassword(
        @Param("user") user: String,
        @Param("password") password: String): Optional<UserInfo>

    @Query("""SELECT userInfo FROM UserInfo userInfo
        JOIN FETCH userInfo.roles role 
        WHERE userInfo.user = :user""")
    fun findOneByUser(@Param("user") user: String): Optional<UserInfo>
}