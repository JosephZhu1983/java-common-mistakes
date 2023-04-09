package org.geekbang.time.commonmistakes.serialization.mybatisjson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@MappedTypes({List.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
public abstract class ListTypeHandler<T> extends BaseTypeHandler<List<T>> {

    private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, List<T> parameter, JdbcType jdbcType) {
        String content = CollectionUtils.isEmpty(parameter) ? null : OBJECT_MAPPER.writeValueAsString(parameter);
        ps.setString(i, content);
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return this.getListByJsonArrayString(rs.getString(columnName));
    }

    @Override
    public List<T> getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return this.getListByJsonArrayString(rs.getString(columnIndex));
    }

    @Override
    public List<T> getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return this.getListByJsonArrayString(cs.getString(columnIndex));
    }

    @SneakyThrows
    private List<T> getListByJsonArrayString(String content) {
        return StringUtils.isEmpty(content) ? new ArrayList<>() : OBJECT_MAPPER.readValue(content, this.specificType());
    }

    protected abstract TypeReference<List<T>> specificType();
}
