package com.msg.customs.model;

/**
 * Status einer CustomsNotification.
 * Beschreibt den Lebenszyklus einer Benachrichtigung.
 */
public enum NotificationStatus {

    DRAFT("Entwurf", "Die Benachrichtigung wurde erstellt aber noch nicht versendet"),
    SENT("Versendet", "Die Benachrichtigung wurde an den Partner gesendet"),
    RECEIVED("Empfangen", "Die Benachrichtigung wurde vom Partner empfangen"),
    PROCESSED("Verarbeitet", "Die Benachrichtigung wurde verarbeitet und in LLEs übernommen"),
    FAILED("Fehlgeschlagen", "Die Übertragung ist fehlgeschlagen"),
    CANCELLED("Abgebrochen", "Die Benachrichtigung wurde abgebrochen");

    private final String displayName;
    private final String description;

    NotificationStatus(String displayName, String description) {
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
     * Prüft ob die Notification noch bearbeitet werden kann.
     */
    public boolean isEditable() {
        return this == DRAFT;
    }

    /**
     * Prüft ob die Notification versendet werden kann.
     */
    public boolean canBeSent() {
        return this == DRAFT;
    }

    /**
     * Prüft ob die Notification abgeschlossen ist.
     */
    public boolean isFinal() {
        return this == PROCESSED || this == FAILED || this == CANCELLED;
    }
}
