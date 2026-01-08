package com.msg.customs.application.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

/**
 * Domain model für NotificationItem.
 * Repräsentiert ein LLE-Element innerhalb einer CustomsNotification.
 */
@Data
public class NotificationItem {

    private Long id;

    // customsInformation
    private String catenaXId;
    private String type;
    private LocalDate confirmationDate;
    private LocalDate validFrom;
    private LocalDate validUntil;
    private String commercialCountryOfOrigin;
    private String federalState;
    private String preferredStatus;
    private String completionStatus;

    // tracedWorth
    private String tracedWorthValue;
    private String tracedWorthCurrency;

    // contactInformation
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String jobTitle;

    // originInformation
    private String tradeRelations;

    // partData
    private String customerPartId;
    private String manufacturerPartId;
    private String nameAtCustomer;
    private String nameAtManufacturer;

    // Metadaten
    private Long sourceLleId;
    private boolean adopted;
    private Long adoptedLleId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Erstellt ein NotificationItem aus einer LLE.
     */
    public static NotificationItem fromCustomsData(CustomsData lle) {
        NotificationItem item = new NotificationItem();
        item.setCatenaXId(lle.getCatenaxId());
        item.setType(lle.getType() != null ? lle.getType().name() : null);
        item.setValidFrom(lle.getValidFrom());
        item.setValidUntil(lle.getValidUntil());

        if (lle.getCountry() != null) {
            item.setCommercialCountryOfOrigin(lle.getCountry().getCode());
        }
        if (lle.getFederalState() != null) {
            item.setFederalState(lle.getFederalState().getCode());
        }

        if (lle.getPreferredStatus() != null) {
            item.setPreferredStatus(lle.getPreferredStatus().name());
        }

        // Completion status based on mandatory fields being present
        boolean isComplete = lle.getCountry() != null && lle.getValidFrom() != null
                && lle.getContact() != null && lle.getPreferredStatus() != null;
        item.setCompletionStatus(isComplete ? "Completed" : "Not-Completed");

        if (lle.getTracedWorth() != null) {
            item.setTracedWorthValue(lle.getTracedWorth().getValue());
            item.setTracedWorthCurrency(lle.getTracedWorth().getCurrency());
        }

        if (lle.getContact() != null) {
            item.setContactPerson(lle.getContact().getName());
            item.setPhoneNumber(lle.getContact().getPhone());
            item.setEmail(lle.getContact().getEmail());
            item.setJobTitle(lle.getContact().getJobTitle());
        }

        if (lle.getCustomerPart() != null) {
            item.setCustomerPartId(lle.getCustomerPart().getNumber());
            item.setNameAtCustomer(lle.getCustomerPart().getName());
        }

        if (lle.getManufacturerPart() != null) {
            item.setManufacturerPartId(lle.getManufacturerPart().getNumber());
            item.setNameAtManufacturer(lle.getManufacturerPart().getName());
        }

        item.setSourceLleId(lle.getId());
        item.setAdopted(false);
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());

        return item;
    }

    /**
     * Prüft ob der Status "Completed" ist.
     */
    public boolean isCompleted() {
        return "Completed".equalsIgnoreCase(completionStatus);
    }

    /**
     * Prüft ob das Item präferenzberechtigt ist.
     */
    public boolean isPreferential() {
        return preferredStatus != null && preferredStatus.startsWith("Y");
    }
}
