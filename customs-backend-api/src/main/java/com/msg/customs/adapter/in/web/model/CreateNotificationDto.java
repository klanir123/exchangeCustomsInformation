package com.msg.customs.adapter.in.web.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO zum Erstellen einer neuen CustomsNotification.
 */
@Data
public class CreateNotificationDto {

    /**
     * BPNL des Empfängers.
     */
    @JsonProperty("receiverBpnl")
    private String receiverBpnl;

    /**
     * IDs der LLEs, die in die Notification aufgenommen werden sollen.
     */
    @JsonProperty("lleIds")
    private List<Long> lleIds;

    /**
     * Optional: ID der ursprünglichen Anfrage (falls Antwort auf Request).
     */
    @JsonProperty("requestId")
    private Long requestId;

    /**
     * Optional: Bemerkungen.
     */
    @JsonProperty("remarks")
    private String remarks;
}
