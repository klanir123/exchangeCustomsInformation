# Implementierungsplan: Customs KIT

## Priorisierung nach Abhängigkeiten

Die Features müssen in einer bestimmten Reihenfolge implementiert werden, da sie aufeinander aufbauen:

```
Phase 1: Lieferantenverwaltung (Basis)
    ↓
Phase 2: SNR-Lieferanten-Verknüpfung
    ↓
Phase 3: LLE-Erweiterungen
    ↓
Phase 4: Nachrichtenstruktur (Header/Content)
    ↓
Phase 5: RequestCustomsInformation
    ↓
Phase 6: CustomsNotification
    ↓
Phase 7: Präferenzkalkulation
```

---

## Phase 1: Lieferantenverwaltung (Epic 1)

### 1.1 Datenbank-Entitäten erstellen

**Datei:** `customs-backend-jpa/src/main/java/com/msg/customs/adapter/out/storage/model/SupplierEntity.java`

```java
@Entity
@Table(name = "supplier")
public class SupplierEntity {
    @Id
    @Column(name = "bpnl", length = 16)
    private String bpnl;  // Format: BPNL + 12 alphanumerische Zeichen

    @Column(nullable = false)
    private String name;

    private String street;
    private String postalCode;
    private String city;

    @Column(length = 2)
    private String country;  // ISO 3166-1

    @Enumerated(EnumType.STRING)
    private SupplierStatus status;  // ACTIVE, INACTIVE, BLOCKED

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<SupplierContactEntity> contacts;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

**Datei:** `customs-backend-jpa/src/main/java/com/msg/customs/adapter/out/storage/model/SupplierContactEntity.java`

```java
@Entity
@Table(name = "supplier_contact")
public class SupplierContactEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_bpnl", nullable = false)
    private SupplierEntity supplier;

    @Column(nullable = false)
    private String contactPerson;

    private String phoneNumber;
    private String email;
    private String jobTitle;

    private boolean isPrimary;  // Hauptansprechpartner für Zollfragen
    private boolean preferredForNotifications;  // Bevorzugt für CustomsNotification
}
```

### 1.2 Enum für Supplier-Status

**Datei:** `customs-commons/src/main/java/com/msg/customs/enums/SupplierStatus.java`

```java
public enum SupplierStatus {
    ACTIVE("Aktiv"),
    INACTIVE("Inaktiv"),
    BLOCKED("Gesperrt");

    private final String displayName;
}
```

### 1.3 Domain-Modelle

**Datei:** `customs-backend-core/src/main/java/com/msg/customs/application/domain/model/Supplier.java`

```java
public record Supplier(
    String bpnl,
    String name,
    Address address,
    String country,
    SupplierStatus status,
    List<SupplierContact> contacts,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}
```

### 1.4 Repository

**Datei:** `customs-backend-jpa/src/main/java/com/msg/customs/adapter/out/storage/SupplierRepository.java`

```java
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {
    List<SupplierEntity> findByStatus(SupplierStatus status);
    List<SupplierEntity> findByNameContainingIgnoreCase(String name);
    List<SupplierEntity> findByCountry(String country);
    boolean existsByBpnl(String bpnl);
}
```

### 1.5 API-Endpunkte

**Datei:** `customs-backend-api/src/main/java/com/msg/customs/adapter/in/web/SupplierAPI.java`

```java
public interface SupplierAPI {
    @GetMapping("/suppliers")
    List<SupplierDto> getAllSuppliers();

    @GetMapping("/suppliers/{bpnl}")
    SupplierDto getSupplierByBpnl(@PathVariable String bpnl);

    @PostMapping("/suppliers")
    ResponseEntity<Void> createSupplier(@RequestBody SupplierDto supplier);

    @PutMapping("/suppliers/{bpnl}")
    ResponseEntity<Void> updateSupplier(@PathVariable String bpnl, @RequestBody SupplierDto supplier);

    @PatchMapping("/suppliers/{bpnl}/status")
    ResponseEntity<Void> updateSupplierStatus(@PathVariable String bpnl, @RequestBody SupplierStatus status);

    @GetMapping("/suppliers/{bpnl}/contacts")
    List<SupplierContactDto> getSupplierContacts(@PathVariable String bpnl);

    @PostMapping("/suppliers/{bpnl}/contacts")
    ResponseEntity<Void> addSupplierContact(@PathVariable String bpnl, @RequestBody SupplierContactDto contact);
}
```

### 1.6 UI-Seiten

**Neue Templates:**
- `suppliers.html` - Lieferanten-Übersicht mit Filter/Suche
- `supplierEdit.html` - Lieferant anlegen/bearbeiten
- `supplierDetail.html` - Detailansicht mit Kontakten, SNRs, LLEs

### 1.7 BPNL-Validierung

**Datei:** `customs-commons/src/main/java/com/msg/customs/validation/BpnlValidator.java`

```java
public class BpnlValidator {
    private static final Pattern BPNL_PATTERN = Pattern.compile("^BPNL[A-Z0-9]{12}$");

    public static boolean isValid(String bpnl) {
        return bpnl != null && BPNL_PATTERN.matcher(bpnl).matches();
    }
}
```

---

## Phase 2: SNR-Lieferanten-Verknüpfung (Epic 2.3)

### 2.1 Zwischentabelle für n:m Beziehung

**Datei:** `customs-backend-jpa/src/main/java/com/msg/customs/adapter/out/storage/model/SnrSupplierEntity.java`

```java
@Entity
@Table(name = "snr_supplier")
public class SnrSupplierEntity {
    @EmbeddedId
    private SnrSupplierId id;

    @ManyToOne
    @MapsId("snrId")
    @JoinColumn(name = "snr_id")
    private MasterPartEntity snr;

    @ManyToOne
    @MapsId("supplierBpnl")
    @JoinColumn(name = "supplier_bpnl")
    private SupplierEntity supplier;

    private boolean isPrimary;  // Hauptlieferant für diese SNR
}

@Embeddable
public class SnrSupplierId implements Serializable {
    private Long snrId;
    private String supplierBpnl;
}
```

### 2.2 MasterPartEntity erweitern

```java
// In MasterPartEntity hinzufügen:
@OneToMany(mappedBy = "snr")
private List<SnrSupplierEntity> supplierLinks;
```

### 2.3 UI-Anpassung

- In `customsPartEdit.html`: Dropdown zur Lieferanten-Auswahl (BPNL)
- Mehrere Lieferanten pro SNR zuordenbar
- Kontaktinformationen werden aus Lieferantenstammdaten übernommen

---

## Phase 3: LLE-Erweiterungen (Epic 3)

### 3.1 CustomsEntity erweitern

```java
// In CustomsEntity hinzufügen:
@ManyToOne
@JoinColumn(name = "supplier_bpnl")
private SupplierEntity supplier;  // Verknüpfung mit Lieferant

@Column(name = "valid_until")
private LocalDate validUntil;  // Berechnet: validFrom + 365 Tage

@Enumerated(EnumType.STRING)
private LleStatus lleStatus;  // DRAFT, ACTIVE, EXPIRED

@ManyToOne
@JoinColumn(name = "predecessor_id")
private CustomsEntity predecessor;  // Vorgänger-LLE bei Erneuerung

@Lob
private byte[] pdfDocument;  // PDF-Speicherung

private String pdfFileName;
private LocalDateTime pdfUploadedAt;
```

### 3.2 LLE-Status Enum

```java
public enum LleStatus {
    DRAFT("Entwurf"),
    ACTIVE("Aktiv"),
    EXPIRING_SOON("Bald ablaufend"),  // < 60 Tage
    CRITICAL("Kritisch"),              // < 30 Tage
    EXPIRED("Abgelaufen");
}
```

### 3.3 Gültigkeitsberechnung

```java
@PrePersist
@PreUpdate
public void calculateValidUntil() {
    if (validFrom != null) {
        this.validUntil = validFrom.plusDays(365);
    }
    updateLleStatus();
}

private void updateLleStatus() {
    if (validUntil == null) return;
    long daysRemaining = ChronoUnit.DAYS.between(LocalDate.now(), validUntil);

    if (daysRemaining < 0) {
        this.lleStatus = LleStatus.EXPIRED;
    } else if (daysRemaining < 30) {
        this.lleStatus = LleStatus.CRITICAL;
    } else if (daysRemaining < 60) {
        this.lleStatus = LleStatus.EXPIRING_SOON;
    } else {
        this.lleStatus = LleStatus.ACTIVE;
    }
}
```

### 3.4 n:m Beziehung LLE zu SNR

```java
@Entity
@Table(name = "lle_snr")
public class LleSnrEntity {
    @EmbeddedId
    private LleSnrId id;

    @ManyToOne
    @MapsId("lleId")
    private CustomsEntity lle;

    @ManyToOne
    @MapsId("snrId")
    private MasterPartEntity snr;
}
```

### 3.5 PDF-Upload/Download Endpunkte

```java
@PostMapping("/customs/{id}/pdf")
ResponseEntity<Void> uploadPdf(@PathVariable Long id, @RequestParam MultipartFile file);

@GetMapping("/customs/{id}/pdf")
ResponseEntity<byte[]> downloadPdf(@PathVariable Long id);

@GetMapping("/customs/{id}/pdf/generate")
ResponseEntity<byte[]> generatePdf(@PathVariable Long id);  // PDF aus Daten erzeugen
```

### 3.6 Dashboard für ablaufende LLEs

**Neue Datei:** `dashboard.html`

```html
<!-- Kacheln für Dashboard -->
<div class="dashboard-tiles">
    <div class="tile warning" th:if="${expiringCount > 0}">
        <span class="count" th:text="${expiringCount}"></span>
        <span class="label">LLEs ablaufend (< 60 Tage)</span>
    </div>
    <div class="tile critical" th:if="${criticalCount > 0}">
        <span class="count" th:text="${criticalCount}"></span>
        <span class="label">LLEs kritisch (< 30 Tage)</span>
    </div>
</div>
```

### 3.7 LLE-Erneuerung

```java
@PostMapping("/customs/{id}/renew")
ResponseEntity<CustomsDataDto> renewLle(@PathVariable Long id) {
    // Kopiert Daten, setzt predecessor, neues validFrom
}
```

---

## Phase 4: Nachrichtenstruktur (Header/Content)

### 4.1 Notification Header Entity

```java
@Entity
@Table(name = "notification_header")
public class NotificationHeaderEntity {
    @Id
    private String messageId;  // UUID

    private String context;  // "IndustryCore-DigitalTwinEventAPI-ConnectToParent:3.0.0"
    private LocalDateTime sentDateTime;

    @Column(name = "sender_bpnl")
    private String senderBpn;

    @Column(name = "receiver_bpnl")
    private String receiverBpn;

    private LocalDateTime expectedResponseBy;
    private String version;
}
```

### 4.2 CustomsNotification Entity

```java
@Entity
@Table(name = "customs_notification")
public class CustomsNotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "header_id")
    private NotificationHeaderEntity header;

    @OneToMany(mappedBy = "notification", cascade = CascadeType.ALL)
    private List<NotificationItemEntity> listOfItems;

    @Enumerated(EnumType.STRING)
    private NotificationDirection direction;  // INCOMING, OUTGOING

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;  // DRAFT, SENT, RECEIVED, PROCESSED
}
```

### 4.3 NotificationItem Entity (listOfItems[])

```java
@Entity
@Table(name = "notification_item")
public class NotificationItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "notification_id")
    private CustomsNotificationEntity notification;

    // customsInformation
    private String catenaXId;
    private String type;
    private LocalDate confirmationDate;
    private LocalDate validFrom;
    private String commercialCountryOfOrigin;
    private String federalState;
    private String preferredStatus;
    private String status;  // Completed / Not-Completed

    // tracedWorth
    private BigDecimal tracedWorthValue;
    private String tracedWorthCurrency;

    // contactInformation
    private String contactPerson;
    private String phoneNumber;
    private String email;
    private String jobTitle;

    // originInformation (als JSON oder separate Tabelle)
    @ElementCollection
    private List<String> tradeRelations;

    // partData
    private String customerPartId;
    private String manufacturerPartId;
    private String nameAtCustomer;
    private String nameAtManufacturer;
}
```

---

## Phase 5: RequestCustomsInformation (Epic 4)

### 5.1 Request Entity

```java
@Entity
@Table(name = "customs_request")
public class CustomsRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private NotificationHeaderEntity header;

    private String customerPartId;  // Angefragte SNR

    @ManyToOne
    @JoinColumn(name = "supplier_bpnl")
    private SupplierEntity supplier;

    private String tradeRelation;  // Gewünschtes Handelsabkommen
    private LocalDate requestedValidFrom;
    private LocalDate requestedValidUntil;

    @Enumerated(EnumType.STRING)
    private RequestStatus status;  // DRAFT, SENT, RECEIVED, ANSWERED, OVERDUE, REJECTED

    private LocalDateTime sentAt;
    private LocalDateTime answeredAt;

    @OneToOne
    @JoinColumn(name = "response_notification_id")
    private CustomsNotificationEntity response;  // Verknüpfung zur Antwort
}
```

### 5.2 Request Status Enum

```java
public enum RequestStatus {
    DRAFT("Entwurf"),
    SENT("Versendet"),
    RECEIVED("Empfangen"),
    IN_PROGRESS("In Bearbeitung"),
    ANSWERED("Beantwortet"),
    OVERDUE("Überfällig"),
    REJECTED("Abgelehnt");
}
```

### 5.3 API-Endpunkte

```java
// Ausgehende Anfragen (als Kunde)
@PostMapping("/requests")
ResponseEntity<Void> createRequest(@RequestBody RequestDto request);

@PostMapping("/requests/{id}/send")
ResponseEntity<Void> sendRequest(@PathVariable Long id);

@GetMapping("/requests/outgoing")
List<RequestDto> getOutgoingRequests();

// Eingehende Anfragen (als Lieferant)
@GetMapping("/requests/incoming")
List<RequestDto> getIncomingRequests();

@PostMapping("/requests/{id}/answer")
ResponseEntity<Void> answerRequest(@PathVariable Long id, @RequestBody CustomsNotificationDto response);

@PostMapping("/requests/{id}/reject")
ResponseEntity<Void> rejectRequest(@PathVariable Long id, @RequestBody String reason);
```

### 5.4 UI-Seiten

- `requests-outgoing.html` - Ausgehende Anfragen (Kundensicht)
- `requests-incoming.html` - Eingehende Anfragen (Lieferantensicht)
- `requestCreate.html` - Neue Anfrage erstellen
- `requestAnswer.html` - Anfrage beantworten

### 5.5 Überfälligkeits-Prüfung

```java
@Scheduled(cron = "0 0 8 * * *")  // Täglich um 8:00
public void checkOverdueRequests() {
    List<CustomsRequestEntity> overdue = requestRepository
        .findByStatusAndExpectedResponseByBefore(RequestStatus.SENT, LocalDateTime.now());

    for (CustomsRequestEntity request : overdue) {
        request.setStatus(RequestStatus.OVERDUE);
        // Optional: E-Mail-Benachrichtigung senden
    }
}
```

---

## Phase 6: CustomsNotification (Epic 5)

### 6.1 Erweiterte Notification-Erstellung

```java
@Service
public class CustomsNotificationService {

    public CustomsNotificationEntity createNotification(
            String receiverBpnl,
            List<Long> lleIds,
            Long originalRequestId  // Optional: Bezug zur Anfrage
    ) {
        CustomsNotificationEntity notification = new CustomsNotificationEntity();

        // Header automatisch befüllen
        NotificationHeaderEntity header = new NotificationHeaderEntity();
        header.setMessageId(UUID.randomUUID().toString());
        header.setSenderBpn(configService.getOwnBpnl());
        header.setReceiverBpn(receiverBpnl);
        header.setSentDateTime(LocalDateTime.now());
        header.setContext("IndustryCore-DigitalTwinEventAPI-ConnectToParent:3.0.0");
        header.setVersion("3.0.0");

        // Items aus LLEs erstellen
        List<NotificationItemEntity> items = lleIds.stream()
            .map(this::createItemFromLle)
            .collect(Collectors.toList());

        notification.setHeader(header);
        notification.setListOfItems(items);
        notification.setDirection(NotificationDirection.OUTGOING);
        notification.setStatus(NotificationStatus.DRAFT);

        return notificationRepository.save(notification);
    }
}
```

### 6.2 EDC-Integration (Placeholder erweitern)

```java
@Service
public class EdcNotificationService {

    public void sendNotification(CustomsNotificationEntity notification) {
        // 1. EDC Endpoint des Empfängers ermitteln
        String edcEndpoint = discoveryService.getEdcEndpoint(notification.getHeader().getReceiverBpn());

        // 2. Contract Negotiation
        String contractId = contractService.negotiateContract(edcEndpoint, "customs-notification");

        // 3. Data Transfer initiieren
        transferService.initiateTransfer(contractId, notification);

        // 4. Status aktualisieren
        notification.setStatus(NotificationStatus.SENT);
        notification.getHeader().setSentDateTime(LocalDateTime.now());
    }

    public void receiveNotification(String jsonPayload) {
        // 1. JSON zu Entity mappen
        CustomsNotificationEntity notification = mapper.fromJson(jsonPayload);
        notification.setDirection(NotificationDirection.INCOMING);
        notification.setStatus(NotificationStatus.RECEIVED);

        // 2. Sender-BPNL mit Lieferantenstammdaten abgleichen
        String senderBpnl = notification.getHeader().getSenderBpn();
        if (!supplierRepository.existsByBpnl(senderBpnl)) {
            // Neuen Lieferanten automatisch anlegen
            createSupplierFromBpnl(senderBpnl);
        }

        // 3. Speichern
        notificationRepository.save(notification);
    }
}
```

### 6.3 Übernahme in LLE

```java
@PostMapping("/notifications/{id}/items/{itemId}/adopt")
ResponseEntity<Void> adoptItemToLle(
    @PathVariable Long id,
    @PathVariable Long itemId,
    @RequestParam String internalSnrId  // Mapping auf interne SNR
) {
    // 1. Item laden
    NotificationItemEntity item = itemRepository.findById(itemId).orElseThrow();

    // 2. Neue LLE erstellen oder bestehende aktualisieren
    CustomsEntity lle = new CustomsEntity();
    lle.setCatenaxId(item.getCatenaXId());
    lle.setValidFrom(item.getValidFrom());
    // Gültigkeit automatisch berechnen: validFrom + 1 Jahr
    lle.setValidUntil(item.getValidFrom().plusDays(365));
    // ... weitere Felder übernehmen

    // 3. Mit Lieferant verknüpfen (über senderBpn)
    lle.setSupplier(supplierRepository.findByBpnl(item.getNotification().getHeader().getSenderBpn()));

    // 4. Warnung bei status = "Not-Completed"
    if ("Not-Completed".equals(item.getStatus())) {
        // Warnung in UI anzeigen
    }

    customsRepository.save(lle);
}
```

---

## Phase 7: Präferenzkalkulation (Epic 6)

### 7.1 Handelsabkommen-Verwaltung

```java
@Entity
@Table(name = "trade_agreement")
public class TradeAgreementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;  // z.B. "EU-MX", "EU-CA"
    private String name;  // z.B. "EU-Mexiko Freihandelsabkommen"

    @ElementCollection
    private List<String> participatingCountries;

    private BigDecimal tariffRate;  // Zollsatz in %
    private LocalDate validFrom;
    private LocalDate validUntil;

    private boolean isActive;
}
```

### 7.2 Präferenz-Berechnungsservice

```java
@Service
public class PreferenceCalculationService {

    public PreferenceResult calculatePreference(String snrId, String targetCountry) {
        // 1. LLE für SNR laden
        CustomsEntity lle = customsRepository.findBySnrIdAndStatus(snrId, LleStatus.ACTIVE);

        if (lle == null) {
            return PreferenceResult.noLleAvailable();
        }

        // 2. Ursprungsland prüfen
        String origin = lle.getCountry();

        // 3. Handelsabkommen prüfen
        List<String> tradeRelations = lle.getOriginInformation();
        Optional<TradeAgreementEntity> applicableAgreement = findApplicableAgreement(origin, targetCountry, tradeRelations);

        // 4. Präferenzstatus aus LLE
        if ("Y-Certified".equals(lle.getPreferredStatus())) {
            if (applicableAgreement.isPresent()) {
                return PreferenceResult.preferential(applicableAgreement.get());
            }
        }

        return PreferenceResult.nonPreferential(lle.getTracedWorth());
    }
}
```

### 7.3 Ergebnis-Modell

```java
public record PreferenceResult(
    boolean isPreferential,
    String reason,
    TradeAgreementEntity agreement,
    BigDecimal tariffRate,
    TracedWorth tracedWorth
) {
    public static PreferenceResult preferential(TradeAgreementEntity agreement) {
        return new PreferenceResult(true, "Präferenzberechtigt gemäß " + agreement.getName(),
            agreement, agreement.getTariffRate(), null);
    }

    public static PreferenceResult nonPreferential(TracedWorth worth) {
        return new PreferenceResult(false, "Keine Präferenzberechtigung", null, null, worth);
    }
}
```

---

## Datenbank-Migrationen

### Liquibase Changelog

**Datei:** `customs-backend-app/src/main/resources/db/changelog/changes/002-supplier-tables.xml`

```xml
<changeSet id="002-1" author="customs">
    <createTable tableName="supplier">
        <column name="bpnl" type="varchar(16)">
            <constraints primaryKey="true"/>
        </column>
        <column name="name" type="varchar(255)">
            <constraints nullable="false"/>
        </column>
        <column name="street" type="varchar(255)"/>
        <column name="postal_code" type="varchar(20)"/>
        <column name="city" type="varchar(100)"/>
        <column name="country" type="varchar(2)"/>
        <column name="status" type="varchar(20)"/>
        <column name="created_at" type="timestamp"/>
        <column name="updated_at" type="timestamp"/>
    </createTable>

    <createTable tableName="supplier_contact">
        <column name="id" type="bigint" autoIncrement="true">
            <constraints primaryKey="true"/>
        </column>
        <column name="supplier_bpnl" type="varchar(16)">
            <constraints nullable="false" foreignKeyName="fk_contact_supplier" references="supplier(bpnl)"/>
        </column>
        <column name="contact_person" type="varchar(255)"/>
        <column name="phone_number" type="varchar(50)"/>
        <column name="email" type="varchar(255)"/>
        <column name="job_title" type="varchar(100)"/>
        <column name="is_primary" type="boolean"/>
        <column name="preferred_for_notifications" type="boolean"/>
    </createTable>
</changeSet>
```

---

## UI-Navigation erweitern

**Aktualisierte Navigation in `header.html`:**

```
CUSTOMS KIT                                    [Benutzer ▼]
├── Dashboard        (NEU)
├── Stammdaten
│   ├── Sachnummern (SNR)    (/parts)
│   └── Lieferanten          (/suppliers)     (NEU)
├── LLE                      (/customs)
├── Anfragen
│   ├── Ausgehend            (/requests/outgoing)  (NEU)
│   └── Eingehend            (/requests/incoming)  (NEU)
└── Benachrichtigungen
    ├── Ausgehend            (/notifications/outgoing)  (NEU)
    └── Eingehend            (/notifications/incoming)  (NEU)
```

---

## Zusammenfassung der neuen Dateien

### Backend-API
- `SupplierAPI.java`
- `SupplierDto.java`
- `SupplierContactDto.java`
- `RequestDto.java`
- `CustomsNotificationDto.java`
- `NotificationHeaderDto.java`
- `NotificationItemDto.java`

### Backend-Core
- `Supplier.java` (Domain)
- `SupplierContact.java` (Domain)
- `CustomsRequest.java` (Domain)
- `CustomsNotification.java` (Domain)
- `SupplierService.java`
- `RequestService.java`
- `NotificationService.java`
- `PreferenceCalculationService.java`

### Backend-JPA
- `SupplierEntity.java`
- `SupplierContactEntity.java`
- `SnrSupplierEntity.java`
- `LleSnrEntity.java`
- `CustomsRequestEntity.java`
- `CustomsNotificationEntity.java`
- `NotificationHeaderEntity.java`
- `NotificationItemEntity.java`
- `TradeAgreementEntity.java`
- `SupplierRepository.java`
- `RequestRepository.java`
- `NotificationRepository.java`

### UI-Templates
- `dashboard.html`
- `suppliers.html`
- `supplierEdit.html`
- `supplierDetail.html`
- `requests-outgoing.html`
- `requests-incoming.html`
- `requestCreate.html`
- `requestAnswer.html`
- `notifications-outgoing.html`
- `notifications-incoming.html`
- `notificationDetail.html`

---

## Empfohlene Reihenfolge der Implementierung

1. **Woche 1-2:** Phase 1 (Lieferantenverwaltung) - Basis für alles andere
2. **Woche 3:** Phase 2 (SNR-Lieferanten-Verknüpfung)
3. **Woche 4-5:** Phase 3 (LLE-Erweiterungen inkl. Dashboard)
4. **Woche 6:** Phase 4 (Nachrichtenstruktur)
5. **Woche 7-8:** Phase 5 (RequestCustomsInformation)
6. **Woche 9-10:** Phase 6 (CustomsNotification)
7. **Woche 11-12:** Phase 7 (Präferenzkalkulation)

---

*Erstellt am: 2024-12-19*
*Basierend auf: Lastenheft Customs KIT v6.0*
