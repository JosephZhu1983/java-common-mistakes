package org.geekbang.time.commonmistakes.serialization.mybatisjson;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

public class InfoListTypeHandler extends ListTypeHandler<Info> {

    @Override
    protected TypeReference<List<Info>> specificType() {
        return new TypeReference<List<Info>>() {
        };
    }
}
