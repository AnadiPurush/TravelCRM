package com.crm.travel.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Spring configuration for Redis template bean provisioning.
 *
 * <p>Domain purpose: Centralizes Redis client configuration and provides
 * a standardized StringRedisTemplate for string-based operations across
 * the application.</p>
 *
 * <p>Core responsibilities: Configure and expose a Redis template bean
 * that handles string key-value operations with proper serialization
 * settings for the CRM system's caching needs.</p>
 *
 * <p>Layer boundary: Configuration layer that bridges Spring's
 * dependency injection container with Redis infrastructure.</p>
 *
 * <p>Concurrency model: Thread-safe configuration class that creates
 * thread-safe StringRedisTemplate instances managed by Spring.</p>
 *
 * @author Utsav Sharma
 * @since 2026-02-15T00:00:00
 */
@Configuration
public class RedisConfig {

    /**
     * Creates and configures a StringRedisTemplate bean for Redis operations.
     *
     * @param redisConnectionFactory the Redis connection factory
     * @return configured StringRedisTemplate for string operations
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return new StringRedisTemplate(redisConnectionFactory);
    }
}
