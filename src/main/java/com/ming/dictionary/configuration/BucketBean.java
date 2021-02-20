package com.ming.dictionary.configuration;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author 78c8-6603
 */
@Configuration
public class BucketBean {

    @Value("${filter.throttle.call.unit-count:#{null}}")
    private Integer callUnitCount;

    @Value("${filter.throttle.call.time-unit:#{null}}")
    private String callTimeUnit;

    @Value("${filter.throttle.call.max-count:#{null}}")
    private Integer callMaxCount;

    @Bean
    public Bucket getBucket() {
        if (!verify()) {
            return null;
        }

        Refill refill = Refill.greedy(callUnitCount, Duration.parse(callTimeUnit));
        Bandwidth limit = Bandwidth.classic(callMaxCount, refill);
        return Bucket4j.builder().addLimit(limit).build();
    }

    private boolean verify() {
        if (callUnitCount == null || callUnitCount <= 0) {
            return false;
        }
        if (callMaxCount == null || callMaxCount <= 0) {
            return false;
        }
        if (StringUtils.isEmpty(callTimeUnit)) {
            return false;
        }

        try {
            Duration.parse(callTimeUnit);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
