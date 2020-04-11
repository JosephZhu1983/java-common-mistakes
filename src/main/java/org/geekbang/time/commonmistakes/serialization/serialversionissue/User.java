package org.geekbang.time.commonmistakes.serialization.serialversionissue;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 6634621353121349665L;
    private String name;
    private int age;
}