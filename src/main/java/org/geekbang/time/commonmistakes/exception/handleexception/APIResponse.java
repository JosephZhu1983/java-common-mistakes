package org.geekbang.time.commonmistakes.exception.handleexception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
