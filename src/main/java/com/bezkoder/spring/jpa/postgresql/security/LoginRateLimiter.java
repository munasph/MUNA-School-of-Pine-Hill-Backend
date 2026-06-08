package com.bezkoder.spring.jpa.postgresql.security;

import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LoginRateLimiter {

	private static final int MAX_ATTEMPTS = 10;
	private static final long WINDOW_SECONDS = 900;

	private final Map<String, AttemptWindow> attempts = new ConcurrentHashMap<>();

	public void assertAllowed(String key) {
		AttemptWindow window = attempts.computeIfAbsent(key, ignored -> new AttemptWindow());
		synchronized (window) {
			window.refreshIfExpired();
			if (window.count >= MAX_ATTEMPTS) {
				throw new IllegalStateException("Too many login attempts. Please try again later.");
			}
			window.count++;
		}
	}

	public void reset(String key) {
		attempts.remove(key);
	}

	private static final class AttemptWindow {
		private int count;
		private Instant windowStart = Instant.now();

		private void refreshIfExpired() {
			if (Instant.now().isAfter(windowStart.plusSeconds(WINDOW_SECONDS))) {
				count = 0;
				windowStart = Instant.now();
			}
		}
	}
}
