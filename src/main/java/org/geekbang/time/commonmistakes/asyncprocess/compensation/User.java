package org.geekbang.time.commonmistakes.asyncprocess.compensation;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Data
public class User implements Serializable {
    private static AtomicLong atomicLonng = new AtomicLong();
    private Long id = atomicLonng.incrementAndGet();
    private String name = UUID.randomUUID().toString();
    private LocalDateTime registerTime = LocalDateTime.now();
}
