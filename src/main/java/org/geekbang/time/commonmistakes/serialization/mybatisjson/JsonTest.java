package org.geekbang.time.commonmistakes.serialization.mybatisjson;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "jsontest", autoResultMap = true)
public class JsonTest {
    private Long id;
    @TableField(value = "info", typeHandler = InfoListTypeHandler.class)
    private List<Info> inforight;
    @TableField(value = "info", typeHandler = JacksonTypeHandler.class)
    private List<Info> infowrong;
}
