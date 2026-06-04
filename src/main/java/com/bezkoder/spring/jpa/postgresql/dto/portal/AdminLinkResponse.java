package com.bezkoder.spring.jpa.postgresql.dto.portal;

/** Admin view of a parent/student link awaiting (or past) approval. */
public class AdminLinkResponse {

	private Long id;
	private String portalUserEmail;
	private String portalUserName;
	private String applicationId;
	private String studentName;
	private String relationship;
	private String status;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public String getPortalUserEmail() { return portalUserEmail; }
	public void setPortalUserEmail(String portalUserEmail) { this.portalUserEmail = portalUserEmail; }
	public String getPortalUserName() { return portalUserName; }
	public void setPortalUserName(String portalUserName) { this.portalUserName = portalUserName; }
	public String getApplicationId() { return applicationId; }
	public void setApplicationId(String applicationId) { this.applicationId = applicationId; }
	public String getStudentName() { return studentName; }
	public void setStudentName(String studentName) { this.studentName = studentName; }
	public String getRelationship() { return relationship; }
	public void setRelationship(String relationship) { this.relationship = relationship; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
}
