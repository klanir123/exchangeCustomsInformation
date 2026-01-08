package com.msg.customs.adapter.in.web.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * DTO für NotificationItem.
 */
@Data
public class NotificationItemDto {

    @JsonProperty("id")
    private Long id;

    // customsInformation
    @JsonProperty("catenaXId")
    private String catenaXId;

    @JsonProperty("type")
    private String type;

    @JsonProperty("confirmationDate")
    private LocalDate confirmationDate;

    @JsonProperty("validFrom")
    private LocalDate validFrom;

    @JsonProperty("validUntil")
    private LocalDate validUntil;

    @JsonProperty("commercialCountryOfOrigin")
    private String commercialCountryOfOrigin;

    @JsonProperty("federalState")
    private String federalState;

    @JsonProperty("preferredStatus")
    private String preferredStatus;

    @JsonProperty("completionStatus")
    private String completionStatus;

    // tracedWorth
    @JsonProperty("tracedWorthValue")
    private String tracedWorthValue;

    @JsonProperty("tracedWorthCurrency")
    private String tracedWorthCurrency;

    // contactInformation
    @JsonProperty("contactPerson")
    private String contactPerson;

    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @JsonProperty("email")
    private String email;

    @JsonProperty("jobTitle")
    private String jobTitle;

    // originInformation
    @JsonProperty("tradeRelations")
    private String tradeRelations;

    // partData
    @JsonProperty("customerPartId")
    private String customerPartId;

    @JsonProperty("manufacturerPartId")
    private String manufacturerPartId;

    @JsonProperty("nameAtCustomer")
    private String nameAtCustomer;

    @JsonProperty("nameAtManufacturer")
    private String nameAtManufacturer;

    // Metadaten
    @JsonProperty("sourceLleId")
    private Long sourceLleId;

    @JsonProperty("adopted")
    private boolean adopted;

    @JsonProperty("adoptedLleId")
    private Long adoptedLleId;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("updatedAt")
    private LocalDateTime updatedAt;

    // Berechnete Felder
    @JsonProperty("isCompleted")
    private boolean completed;

    @JsonProperty("isPreferential")
    private boolean preferential;
}
