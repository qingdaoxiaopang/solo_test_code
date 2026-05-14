package com.operation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SnowflakeConfig {

    @Bean
    public IdGenerator snowflakeIdGenerator() {
        return new IdGenerator(1, 1);
    }

    public static class IdGenerator {
        private final long workerId;
        private final long datacenterId;
        private long sequence = 0L;
        private long lastTimestamp = -1L;

        private static final long TWEPOCH = 1609459200000L;
        private static final long WORKER_ID_BITS = 5L;
        private static final long DATACENTER_ID_BITS = 5L;
        private static final long SEQUENCE_BITS = 12L;
        private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
        private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

        public IdGenerator(long workerId, long datacenterId) {
            if (workerId > MAX_WORKER_ID || workerId < 0) {
                throw new IllegalArgumentException("worker Id can't be greater than " + MAX_WORKER_ID);
            }
            if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
                throw new IllegalArgumentException("datacenter Id can't be greater than " + MAX_DATACENTER_ID);
            }
            this.workerId = workerId;
            this.datacenterId = datacenterId;
        }

        public synchronized long nextId() {
            long timestamp = timeGen();
            if (timestamp < lastTimestamp) {
                throw new RuntimeException("Clock moved backwards.");
            }
            if (lastTimestamp == timestamp) {
                sequence = (sequence + 1) & ((1 << SEQUENCE_BITS) - 1);
                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }
            } else {
                sequence = 0L;
            }
            lastTimestamp = timestamp;
            return ((timestamp - TWEPOCH) << (WORKER_ID_BITS + DATACENTER_ID_BITS + SEQUENCE_BITS))
                | (datacenterId << (WORKER_ID_BITS + SEQUENCE_BITS))
                | (workerId << SEQUENCE_BITS)
                | sequence;
        }

        protected long tilNextMillis(long lastTimestamp) {
            long timestamp = timeGen();
            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        protected long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
