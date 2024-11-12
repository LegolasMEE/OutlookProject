package com.Trainee.ProjectOutlook.rateLimiter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Aspect
@Component
public class RateLimitAspect {

    private final ConcurrentMap<String, Bucket> bucketCache = new ConcurrentHashMap<>();

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();

        Bucket bucket = bucketCache.computeIfAbsent(methodName, key -> createBucket(rateLimit));

        if (bucket.tryConsume(1)) {
            return joinPoint.proceed();
        } else {
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "Rate limit exceeded");
        }
    }

    private Bucket createBucket(RateLimit rateLimit) {
        Refill refill = Refill.intervally(rateLimit.refillTokens(), Duration.ofSeconds(rateLimit.refillPeriod()));
        Bandwidth limit = Bandwidth.classic(rateLimit.capacity(), refill);
        return Bucket.builder().addLimit(limit).build();
    }
}
