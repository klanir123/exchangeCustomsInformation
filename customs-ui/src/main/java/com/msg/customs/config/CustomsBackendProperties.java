package com.msg.customs.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "customs.backend")
public class CustomsBackendProperties {

	private String uri;
	
	public String customsListUrl() {
		return uri + "/customs";
	}
	
	public String customsEditUrl() {
		return uri + "/customs/{id}";
	}
	
	public String customsNotifyUrl() {
		return uri + "/customs/notify";
	}
	
	public String customsUpdatetUrl() {
		return uri + "/customs";
	}

	public String customsMasterPartUpdatetUrl() { return customsUpdatetUrl() + "/masterpart"; }

	public String customsUpdatePartUrl() {
		return uri + "/customs/part/create";
	}

	public String tasksEditUrl() { return uri + "/tasks";}
}
