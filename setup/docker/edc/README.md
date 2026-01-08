# EDC Setup für Customs KIT

Dieses Setup stellt zwei EDC Connectoren (Eclipse Dataspace Connector) für lokale Tests der Customs KIT Anwendung bereit.

## Übersicht

Das Setup verwendet die [sovity EDC Community Edition](https://github.com/sovity/edc-ce), die mit Tractus-X kompatibel ist.

### Architektur

```
┌─────────────────────────────────────────────────────────────────────┐
│                        Docker Network (edc-network)                  │
│                                                                      │
│  ┌──────────────────────┐        ┌──────────────────────┐           │
│  │   Connector A (OEM)  │        │  Connector B (Supplier)│          │
│  │   BPNL000000000OEM   │◄──────►│   BPNL000000000SUP    │          │
│  │                      │  DSP   │                       │           │
│  │  UI: localhost:11000 │        │  UI: localhost:22000  │           │
│  │  API: localhost:11002│        │  API: localhost:22002 │           │
│  └──────────┬───────────┘        └───────────┬───────────┘           │
│             │                                │                       │
│  ┌──────────▼───────────┐        ┌───────────▼──────────┐           │
│  │    PostgreSQL A      │        │    PostgreSQL B      │           │
│  │   (connector-a-db)   │        │   (connector-b-db)   │           │
│  └──────────────────────┘        └──────────────────────┘           │
└─────────────────────────────────────────────────────────────────────┘

                              ▲
                              │ HTTP
                              ▼
              ┌───────────────────────────────┐
              │     Customs KIT Backend       │
              │     (localhost:8080)          │
              └───────────────────────────────┘
```

### Komponenten

| Komponente | Beschreibung | URL |
|------------|--------------|-----|
| Connector A | EDC für eigenes Unternehmen (OEM/Kunde) | http://localhost:11000 |
| Connector B | EDC für Lieferant (Supplier) | http://localhost:22000 |
| Connector A Management API | API für Asset/Contract Management | http://localhost:11002/api/management |
| Connector B Management API | API für Asset/Contract Management | http://localhost:22002/api/management |

## Voraussetzungen

- Docker Desktop oder Docker Engine
- Docker Compose v2+
- Mindestens 4 GB RAM verfügbar für Docker
- Internet-Verbindung (zum Herunterladen der Images)

## Installation & Start

### 1. GitHub Container Registry Login (einmalig)

```bash
# Login mit GitHub Token (read:packages Berechtigung erforderlich)
docker login ghcr.io -u GITHUB_USERNAME -p GITHUB_TOKEN
```

### 2. EDC starten

```bash
cd setup/docker/edc
docker compose up -d
```

### 3. Status prüfen

```bash
docker compose ps
docker compose logs -f
```

### 4. Health Check

```bash
# Connector A
curl http://localhost:11001/api/check/health

# Connector B
curl http://localhost:22001/api/check/health
```

## Verwendung mit Customs KIT

### Epic 5: RequestCustomsInformation

1. **Anfrage erstellen (OEM → Supplier)**
   - OEM erstellt in Customs KIT eine Anfrage für LLE-Daten
   - Backend sendet über Connector A (EDC) die Anfrage an Connector B
   - Supplier empfängt die Anfrage in seiner Customs KIT Instanz

2. **Anfrage beantworten (Supplier → OEM)**
   - Supplier beantwortet die Anfrage mit LLE-Daten
   - Antwort wird über Connector B an Connector A gesendet
   - OEM kann die Daten in sein System übernehmen

### Epic 6: CustomsNotification

1. **Notification senden**
   - Lieferant erstellt CustomsNotification mit LLE-Daten
   - Notification wird über EDC an den Empfänger gesendet

2. **Notification empfangen**
   - Empfänger erhält Notification
   - Daten können in lokale LLE übernommen werden

## API-Beispiele

### Asset erstellen (Connector A)

```bash
curl -X POST http://localhost:11002/api/management/v3/assets \
  -H "Content-Type: application/json" \
  -H "X-Api-Key: ApiKeyDefaultValue" \
  -d '{
    "@context": {
      "edc": "https://w3id.org/edc/v0.0.1/ns/"
    },
    "@id": "customs-lle-data",
    "properties": {
      "name": "Customs LLE Data",
      "description": "Langzeitlieferantenerklärung für Sachnummer XYZ"
    },
    "dataAddress": {
      "@type": "DataAddress",
      "type": "HttpData",
      "baseUrl": "http://host.docker.internal:8080/customs/notification"
    }
  }'
```

### Contract Definition erstellen

```bash
curl -X POST http://localhost:11002/api/management/v2/contractdefinitions \
  -H "Content-Type: application/json" \
  -H "X-Api-Key: ApiKeyDefaultValue" \
  -d '{
    "@context": {
      "edc": "https://w3id.org/edc/v0.0.1/ns/"
    },
    "@id": "customs-contract-def",
    "accessPolicyId": "customs-access-policy",
    "contractPolicyId": "customs-contract-policy",
    "assetsSelector": {
      "operandLeft": "https://w3id.org/edc/v0.0.1/ns/id",
      "operator": "=",
      "operandRight": "customs-lle-data"
    }
  }'
```

### Katalog von Connector B abfragen (von Connector A aus)

```bash
curl -X POST http://localhost:11002/api/management/v2/catalog/request \
  -H "Content-Type: application/json" \
  -H "X-Api-Key: ApiKeyDefaultValue" \
  -d '{
    "@context": {
      "edc": "https://w3id.org/edc/v0.0.1/ns/"
    },
    "counterPartyAddress": "http://connector-b:22003/api/dsp",
    "protocol": "dataspace-protocol-http"
  }'
```

## Konfiguration

### Umgebungsvariablen

| Variable | Beschreibung | Standard |
|----------|--------------|----------|
| `MY_EDC_PARTICIPANT_ID` | BPNL des Teilnehmers | - |
| `EDC_API_AUTH_KEY` | API Key für Management API | `ApiKeyDefaultValue` |
| `EDC_DSP_CALLBACK_ADDRESS` | DSP Callback URL | - |

### Ports

| Port | Connector A | Connector B | Beschreibung |
|------|-------------|-------------|--------------|
| UI | 11000 | 22000 | Web UI |
| Default API | 11001 | 22001 | Health Check etc. |
| Management | 11002 | 22002 | Asset/Contract Management |
| DSP | 11003 | 22003 | Dataspace Protocol |
| Public | 11004 | 22004 | Public Data API |
| Control | 11005 | 22005 | Control API |

## Troubleshooting

### Container starten nicht

```bash
# Logs prüfen
docker compose logs connector-a
docker compose logs connector-b

# Container neu starten
docker compose down
docker compose up -d
```

### Datenbank-Probleme

```bash
# Volumes löschen und neu starten
docker compose down -v
docker compose up -d
```

### Netzwerk-Probleme

```bash
# Netzwerk prüfen
docker network inspect customs-edc_edc-network

# Konnektivität zwischen Containern testen
docker exec customs-edc-connector-a curl -v http://connector-b:22003/api/dsp
```

## Integration mit Customs Backend

Um das Customs Backend mit dem EDC zu verbinden, müssen folgende Endpunkte in der Anwendung konfiguriert werden:

```yaml
# application.yaml
edc:
  connector-a:
    management-url: http://localhost:11002/api/management
    api-key: ApiKeyDefaultValue
    participant-id: BPNL000000000OEM
  connector-b:
    dsp-endpoint: http://connector-b:22003/api/dsp
    participant-id: BPNL000000000SUP
```

## Stoppen & Aufräumen

```bash
# Stoppen (Daten bleiben erhalten)
docker compose down

# Stoppen und Volumes löschen
docker compose down -v

# Alle Images entfernen
docker compose down --rmi all -v
```

## Weiterführende Links

- [sovity EDC Community Edition](https://github.com/sovity/edc-ce)
- [Eclipse Dataspace Connector](https://github.com/eclipse-edc/Connector)
- [Tractus-X EDC](https://github.com/eclipse-tractusx/tractusx-edc)
- [Catena-X Documentation](https://catena-x.net/en/offers-standards/semantic-layer)
