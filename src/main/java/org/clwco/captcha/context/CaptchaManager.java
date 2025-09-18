package org.clwco.captcha.context;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Managed by clwco-captcha-engine
 * Create by Milad on 9/17/2025 11:32 PM
 */
public class CaptchaManager {
    private static final ConcurrentHashMap<String, Map<String, Long>> KEY_QUEUE = new ConcurrentHashMap<>();
    private static final ScheduledExecutorService CLEANUP_SCHEDULER = Executors.newScheduledThreadPool(1);
    private static final long EXPIRATION_MINUTES = 10;

    // Initialize the scheduled service
    static {
        // Run cleanup every 2 minutes
        CLEANUP_SCHEDULER.scheduleAtFixedRate(
                CaptchaManager::cleanExpiredKeys,
                0, 2, TimeUnit.MINUTES
        );

        // Add shutdown hook for proper cleanup when application terminates
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            CLEANUP_SCHEDULER.shutdown();
            try {
                if (!CLEANUP_SCHEDULER.awaitTermination(5, TimeUnit.SECONDS)) {
                    CLEANUP_SCHEDULER.shutdownNow();
                }
            } catch (InterruptedException e) {
                CLEANUP_SCHEDULER.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }));
    }

    public static void put(String key, String value) {
        KEY_QUEUE.put(key, Map.of(value, System.currentTimeMillis()));
    }

    public static String get(String key) {
        Map<String, Long> valueMap = KEY_QUEUE.get(key);
        if (valueMap != null && !valueMap.isEmpty()) {
            // Check expiration when retrieving
            Long timestamp = valueMap.values().iterator().next();
            if (isExpired(timestamp)) {
                remove(key);
                return null;
            }
            return valueMap.keySet().iterator().next();
        }
        return null;
    }

    public static void remove(String key) {
        KEY_QUEUE.remove(key);
    }

    /**
     * Remove expired keys (created 10 minutes ago or more)
     */
    private static void cleanExpiredKeys() {
        long currentTime = System.currentTimeMillis();
        long expirationTime = TimeUnit.MINUTES.toMillis(EXPIRATION_MINUTES);

        KEY_QUEUE.entrySet().removeIf(entry -> {
            Map<String, Long> valueMap = entry.getValue();
            if (valueMap != null && !valueMap.isEmpty()) {
                Long timestamp = valueMap.values().iterator().next();
                return (currentTime - timestamp) > expirationTime;
            }
            return true; // If value is empty, remove it
        });
    }

    /**
     * Check if a timestamp is expired
     */
    private static boolean isExpired(Long timestamp) {
        long currentTime = System.currentTimeMillis();
        long expirationTime = TimeUnit.MINUTES.toMillis(EXPIRATION_MINUTES);
        return (currentTime - timestamp) > expirationTime;
    }

    /**
     * Helper method to get the number of existing keys
     */
    public static int size() {
        return KEY_QUEUE.size();
    }
}