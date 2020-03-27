package org.geekbang.time.commonmistakes.nullvalue.dbnull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = "SELECT SUM(score) FROM `user`")
    Long wrong1();

    @Query(nativeQuery = true, value = "SELECT COUNT(score) FROM `user`")
    Long wrong2();

    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE score=null")
    List<User> wrong3();

    @Query(nativeQuery = true, value = "SELECT IFNULL(SUM(score),0) FROM `user`")
    Long right1();

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM `user`")
    Long right2();

    @Query(nativeQuery = true, value = "SELECT * FROM `user` WHERE score IS NULL")
    List<User> right3();

}
