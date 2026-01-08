package com.msg.customs.application.port.out;

import java.util.List;
import java.util.Optional;

import com.msg.customs.application.domain.model.CustomsRequest;
import com.msg.customs.model.NotificationDirection;
import com.msg.customs.model.RequestStatus;

/**
 * Port für die Persistenz von CustomsRequest.
 */
public interface CustomsRequestStorageAdapter {

    /**
     * Speichert eine Anfrage.
     */
    CustomsRequest save(CustomsRequest request);

    /**
     * Findet eine Anfrage nach ID.
     */
    Optional<CustomsRequest> findById(Long id);

    /**
     * Findet eine Anfrage nach Message-ID.
     */
    Optional<CustomsRequest> findByMessageId(String messageId);

    /**
     * Findet alle Anfragen.
     */
    List<CustomsRequest> findAll();

    /**
     * Findet Anfragen nach Richtung.
     */
    List<CustomsRequest> findByDirection(NotificationDirection direction);

    /**
     * Findet Anfragen nach Status.
     */
    List<CustomsRequest> findByStatus(RequestStatus status);

    /**
     * Findet Anfragen nach Status und Richtung.
     */
    List<CustomsRequest> findByStatusAndDirection(RequestStatus status, NotificationDirection direction);

    /**
     * Findet Anfragen für einen Lieferanten.
     */
    List<CustomsRequest> findBySupplierBpnl(String supplierBpnl);

    /**
     * Findet überfällige Anfragen.
     */
    List<CustomsRequest> findOverdueRequests();

    /**
     * Löscht eine Anfrage.
     */
    void deleteById(Long id);

    /**
     * Prüft ob eine Anfrage existiert.
     */
    boolean existsById(Long id);

    /**
     * Zählt offene ausgehende Anfragen.
     */
    long countOpenOutgoingRequests();

    /**
     * Zählt offene eingehende Anfragen.
     */
    long countOpenIncomingRequests();

    /**
     * Zählt überfällige Anfragen.
     */
    long countOverdueRequests();
}
