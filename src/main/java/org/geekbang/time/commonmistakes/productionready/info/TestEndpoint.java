package org.geekbang.time.commonmistakes.productionready.info;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Endpoint(id = "adder")
@Component
public class TestEndpoint {
    private static AtomicLong atomicLong = new AtomicLong();

    @ReadOperation
    public String get() {
        return String.valueOf(atomicLong.get());
    }

    @WriteOperation
    public String increment() {
        return String.valueOf(atomicLong.incrementAndGet());
    }
}
