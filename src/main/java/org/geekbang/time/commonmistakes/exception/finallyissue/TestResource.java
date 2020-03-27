package org.geekbang.time.commonmistakes.exception.finallyissue;

public class TestResource implements AutoCloseable {

    public void read() throws Exception {
        throw new Exception("read error");
    }

    @Override
    public void close() throws Exception {
        throw new Exception("close error");
    }
}
