package com.bezkoder.spring.jpa.postgresql.dto.cms;

public class CmsModuleInfo {

	private String key;
	private String label;
	private String description;
	private String adminPath;
	private String apiPath;
	private boolean scaffoldOnly;

	public String getKey() { return key; }
	public void setKey(String key) { this.key = key; }
	public String getLabel() { return label; }
	public void setLabel(String label) { this.label = label; }
	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }
	public String getAdminPath() { return adminPath; }
	public void setAdminPath(String adminPath) { this.adminPath = adminPath; }
	public String getApiPath() { return apiPath; }
	public void setApiPath(String apiPath) { this.apiPath = apiPath; }
	public boolean isScaffoldOnly() { return scaffoldOnly; }
	public void setScaffoldOnly(boolean scaffoldOnly) { this.scaffoldOnly = scaffoldOnly; }
}
