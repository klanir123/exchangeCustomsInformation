package com.msg.customs.adapter.in.web.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.NotificationStatus;

import lombok.Data;

/**
 * DTO für CustomsNotification.
 */
@Data
public class CustomsNotificationDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("header")
    private NotificationHeaderDto header;

    @JsonProperty("listOfItems")
    private List<NotificationItemDto> listOfItems;

    @JsonProperty("direction")
    private NotificationDirection direction;

    @JsonProperty("status")
    private NotificationStatus status;

    @JsonProperty("statusDisplayName")
    private String statusDisplayName;

    @JsonProperty("supplierBpnl")
    private String supplierBpnl;

    @JsonProperty("supplierName")
    private String supplierName;

    @JsonProperty("originalRequestId")
    private Long originalRequestId;

    @JsonProperty("remarks")
    private String remarks;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Berechnete Felder
    @JsonProperty("itemCount")
    private int itemCount;

    @JsonProperty("unadoptedItemCount")
    private long unadoptedItemCount;

    @JsonProperty("isEditable")
    private boolean editable;

    @JsonProperty("canBeSent")
    private boolean canBeSent;
}
