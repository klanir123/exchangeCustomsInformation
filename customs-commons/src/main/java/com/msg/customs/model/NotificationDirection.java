package com.msg.customs.model;

/**
 * Richtung einer Notification (CustomsNotification oder Request).
 */
public enum NotificationDirection {

    INCOMING("Eingehend", "Von einem Partner empfangen"),
    OUTGOING("Ausgehend", "An einen Partner gesendet");

    private final String displayName;
    private final String description;

    NotificationDirection(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getDescription() {
        return description;
    }
}
