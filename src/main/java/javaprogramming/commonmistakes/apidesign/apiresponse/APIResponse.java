package javaprogramming.commonmistakes.apidesign.apiresponse;

import lombok.Data;

@Data
public class APIResponse<T> {
    private boolean success;
    private T data;
    private int code;
    private String message;
}
