package com.bezkoder.spring.jpa.postgresql.admission;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

/** Parses and validates the site-settings list of enabled admission document types. */
public final class AdmissionRequiredDocuments {

	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final TypeReference<List<String>> LIST_TYPE = new TypeReference<>() {
	};

	private AdmissionRequiredDocuments() {
	}

	public static List<String> parse(String json) {
		if (json == null || json.isBlank()) {
			return List.of();
		}
		try {
			List<String> raw = MAPPER.readValue(json, LIST_TYPE);
			return sanitize(raw);
		} catch (JsonProcessingException ex) {
			return List.of();
		}
	}

	public static String serialize(List<String> types) {
		try {
			return MAPPER.writeValueAsString(sanitize(types));
		} catch (JsonProcessingException ex) {
			return "[]";
		}
	}

	public static List<String> sanitize(List<String> types) {
		if (types == null || types.isEmpty()) {
			return List.of();
		}

		Set<String> seen = new LinkedHashSet<>();
		List<String> cleaned = new ArrayList<>();
		for (String type : types) {
			if (type == null || type.isBlank()) {
				continue;
			}
			String normalized = type.trim().toUpperCase();
			if (!AdmissionDocumentType.ALLOWED.contains(normalized) || !seen.add(normalized)) {
				continue;
			}
			cleaned.add(normalized);
		}
		return List.copyOf(cleaned);
	}
}
