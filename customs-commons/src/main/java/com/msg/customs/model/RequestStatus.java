package com.msg.customs.model;

/**
 * Status-Enum für CustomsRequest (RequestCustomsInformation).
 * Repräsentiert den Lebenszyklus einer Zoll-Anfrage.
 */
public enum RequestStatus {

    DRAFT("Entwurf", "Die Anfrage wurde erstellt aber noch nicht versendet"),
    SENT("Versendet", "Die Anfrage wurde an den Lieferanten gesendet"),
    RECEIVED("Empfangen", "Die Anfrage wurde vom Lieferanten empfangen"),
    IN_PROGRESS("In Bearbeitung", "Der Lieferant bearbeitet die Anfrage"),
    ANSWERED("Beantwortet", "Der Lieferant hat die Anfrage beantwortet"),
    OVERDUE("Überfällig", "Die Antwortfrist wurde überschritten"),
    REJECTED("Abgelehnt", "Die Anfrage wurde abgelehnt");

    private final String displayName;
    private final String description;

    RequestStatus(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Prüft ob die Anfrage noch offen ist (noch nicht beantwortet/abgelehnt).
     */
    public boolean isOpen() {
        return this == DRAFT || this == SENT || this == RECEIVED || this == IN_PROGRESS;
    }

    /**
     * Prüft ob die Anfrage abgeschlossen ist.
     */
    public boolean isClosed() {
        return this == ANSWERED || this == REJECTED;
    }

    /**
     * Prüft ob auf die Anfrage reagiert werden muss.
     */
    public boolean requiresAction() {
        return this == RECEIVED || this == IN_PROGRESS || this == OVERDUE;
    }
}
