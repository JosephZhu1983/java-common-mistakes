package org.geekbang.time.commonmistakes.asyncprocess.rabbitmqdlx;

public class Consts {
    public static final Integer RETRY_INTERNAL = 3000;
    public static final Integer RETRY_COUNT = 2;


    public static final String EXCHANGE = "worker";
    public static final String QUEUE = "worker";
    public static final String ROUTING_KEY = "worker";

    public static final String BUFFER_QUEUE = "buffer";
    public static final String BUFFER_EXCHANGE = "buffer";
    public static final String BUFFER_ROUTING_KEY = "buffer";

    public static final String DEAD_EXCHANGE = "dead";
    public static final String DEAD_QUEUE = "dead";
    public static final String DEAD_ROUTING_KEY = "dead";
}
