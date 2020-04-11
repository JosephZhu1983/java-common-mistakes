package org.geekbang.time.commonmistakes.serialization.deserializationconstructor;

import lombok.Data;

@Data
public class APIResultWrong {
    private boolean success;
    private int code;

    public APIResultWrong() {
    }

    public APIResultWrong(int code) {
        this.code = code;
        if (code == 2000) success = true;
        else success = false;
    }
}
