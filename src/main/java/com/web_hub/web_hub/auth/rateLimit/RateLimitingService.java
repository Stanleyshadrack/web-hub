package com.web_hub.web_hub.auth.rateLimit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;
import java.time.Duration;

@Service
public class RateLimitingService {

    private final Cache<String, Bucket> cache = Caffeine.newBuilder()
            .expireAfterAccess(Duration.ofMinutes(10))
            .build();

    public Bucket resolveBucket(String ipAddress, String requestURI) {
        // Construct a key that includes the specific bucket type
        String cacheKey = requestURI.startsWith("/v1/api/auth") ? "STRICT:" + ipAddress : "STD:" + ipAddress;

        return cache.get(cacheKey, key -> newBucket(requestURI));
    }

    private Bucket newBucket(String requestURI) {
        // Tiered Limits: Strict for Auth, Standard for everything else
        if (requestURI.startsWith("/v1/api/auth")) {
            return Bucket.builder()
                    .addLimit(Bandwidth.classic(5, Refill.greedy(5, Duration.ofMinutes(1))))
                    .build();
        }
        return Bucket.builder()
                .addLimit(Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1))))
                .build();
    }
}