package org.geekbang.time.commonmistakes.dataandcode.sqlinject;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserDataMapper {
    @Select("SELECT id,name FROM `userdata` WHERE name LIKE '%${name}%'")
    List<UserData> findByNameWrong(@Param("name") String name);

    List<UserData> findByNamesWrong(@Param("names") String names);

    @Select("SELECT id,name FROM `userdata` WHERE name LIKE CONCAT('%',#{name},'%')")
    List<UserData> findByNameRight(@Param("name") String name);

    List<UserData> findByNamesRight(@Param("names") List<String> names);

}
