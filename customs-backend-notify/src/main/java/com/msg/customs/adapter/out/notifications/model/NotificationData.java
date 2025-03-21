package com.msg.customs.adapter.out.notifications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * Wrapper to split the part number data and the the rest of the customs data.
 */
@Data
public class NotificationData {
	@JsonProperty("listOfItems")
	private List<NotificationItemData> items;
}
