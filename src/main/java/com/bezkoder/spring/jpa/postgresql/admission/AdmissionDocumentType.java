package com.bezkoder.spring.jpa.postgresql.admission;

import java.util.List;
import java.util.Set;

/** Document types accepted on the public registration form. */
public final class AdmissionDocumentType {

	public static final String BIRTH_CERTIFICATE = "BIRTH_CERTIFICATE";
	public static final String SOCIAL_SECURITY_CARD = "SOCIAL_SECURITY_CARD";
	public static final String PARENT_GUARDIAN_ID = "PARENT_GUARDIAN_ID";
	public static final String EMERGENCY_CONTACT_ID = "EMERGENCY_CONTACT_ID";
	public static final String PHYSICAL_EXAM = "PHYSICAL_EXAM";
	public static final String DENTAL_EXAM = "DENTAL_EXAM";
	public static final String IMMUNIZATION = "IMMUNIZATION";
	public static final String TAX_1040 = "TAX_1040";
	public static final String REPORT_CARD = "REPORT_CARD";
	public static final String TRANSCRIPTS = "TRANSCRIPTS";
	public static final String MAP_SCORES = "MAP_SCORES";
	public static final String PSSA_SCORES = "PSSA_SCORES";
	public static final String KEYSTONE_SCORES = "KEYSTONE_SCORES";
	public static final String IEP = "IEP";

	public static final List<String> REQUIRED = List.of(
			BIRTH_CERTIFICATE,
			SOCIAL_SECURITY_CARD,
			PARENT_GUARDIAN_ID,
			EMERGENCY_CONTACT_ID,
			PHYSICAL_EXAM,
			DENTAL_EXAM,
			IMMUNIZATION,
			TAX_1040);

	public static final Set<String> ALLOWED = Set.of(
			BIRTH_CERTIFICATE,
			SOCIAL_SECURITY_CARD,
			PARENT_GUARDIAN_ID,
			EMERGENCY_CONTACT_ID,
			PHYSICAL_EXAM,
			DENTAL_EXAM,
			IMMUNIZATION,
			TAX_1040,
			REPORT_CARD,
			TRANSCRIPTS,
			MAP_SCORES,
			PSSA_SCORES,
			KEYSTONE_SCORES,
			IEP);

	private AdmissionDocumentType() {
	}
}
