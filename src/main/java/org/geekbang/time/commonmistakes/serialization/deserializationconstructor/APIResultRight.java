package org.geekbang.time.commonmistakes.serialization.deserializationconstructor;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class APIResultRight {
    private boolean success;
    private int code;

    public APIResultRight() {
    }

    @JsonCreator
    public APIResultRight(@JsonProperty("code") int code) {
        this.code = code;
        if (code == 2000) success = true;
        else success = false;
    }
}
