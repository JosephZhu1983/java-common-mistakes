package org.geekbang.time.commonmistakes.serialization.mybatisjson;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JsonTestMapper extends BaseMapper<JsonTest> {
}
