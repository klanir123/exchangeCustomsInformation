package com.msg.customs.adapter.out.notifications.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Wrapper for a single notification item.
 */
@Data
public class NotificationItemData {

    @JsonProperty("catenaXId")
    private String catenaxId;

    private NotificationCustomsData customsData;

    private NotificationPart customerPart;

    private NotificationPart manufacturerPart;
}

