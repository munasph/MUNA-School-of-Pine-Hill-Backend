package com.bezkoder.spring.jpa.postgresql.dto.portal;

/** A student record a portal user is linked to. Full details only when status == APPROVED. */
public class LinkedStudentResponse {

	private Long linkId;
	private String applicationId;
	private String studentName;
	private String classApplying;
	private String relationship;
	private String linkStatus;
	private String applicationStatus;

	public Long getLinkId() { return linkId; }
	public void setLinkId(Long linkId) { this.linkId = linkId; }
	public String getApplicationId() { return applicationId; }
	public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
	public String getStudentName() { return studentName; }
	public void setStudentName(String studentName) { this.studentName = studentName; }
	public String getClassApplying() { return classApplying; }
	public void setClassApplying(String classApplying) { this.classApplying = classApplying; }
	public String getRelationship() { return relationship; }
	public void setRelationship(String relationship) { this.relationship = relationship; }
	public String getLinkStatus() { return linkStatus; }
	public void setLinkStatus(String linkStatus) { this.linkStatus = linkStatus; }
	public String getApplicationStatus() { return applicationStatus; }
	public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }
}
