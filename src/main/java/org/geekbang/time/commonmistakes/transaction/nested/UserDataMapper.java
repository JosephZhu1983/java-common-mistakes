package org.geekbang.time.commonmistakes.transaction.nested;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserDataMapper {
    @Insert("insert into userdata(name,source)values(#{name},#{source})")
    void insert(@Param("name") String name, @Param("source") String source);

    @Select("select count(*) from userdata where name=#{name}")
    int count(@Param("name") String name);
}
