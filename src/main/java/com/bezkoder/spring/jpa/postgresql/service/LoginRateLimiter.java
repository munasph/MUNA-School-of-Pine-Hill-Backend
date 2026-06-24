package com.bezkoder.spring.jpa.postgresql.service;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.bezkoder.spring.jpa.postgresql.exception.BadRequestException;

/**
 * Simple in-memory sliding-window limiter keyed by client IP + email. Protects login
 * and password-reset endpoints from credential stuffing / enumeration bursts.
 * (For multi-instance deployments this should move to Redis — fine for a single dyno.)
 */
@Service
public class LoginRateLimiter {

	private static final int MAX_ATTEMPTS = 10;
	private static final Duration WINDOW = Duration.ofMinutes(5);

	private final ConcurrentHashMap<String, Window> windows = new ConcurrentHashMap<>();

	/** Throws {@link BadRequestException} (HTTP 400) when the key exceeds its budget. */
	public void checkAndIncrement(String key) {
		Instant now = Instant.now();
		Window window = windows.compute(key, (k, existing) -> {
			if (existing == null || existing.startedAt.plus(WINDOW).isBefore(now)) {
				return new Window(now, 1);
			}
			existing.count++;
			return existing;
		});

		if (window.count > MAX_ATTEMPTS) {
			throw new BadRequestException("Too many attempts. Please wait a few minutes and try again.");
		}
	}

	/** Clears the counter after a successful authentication. */
	public void reset(String key) {
		windows.remove(key);
	}

	private static final class Window {
		private final Instant startedAt;
		private int count;

		private Window(Instant startedAt, int count) {
			this.startedAt = startedAt;
			this.count = count;
		}
	}
}
