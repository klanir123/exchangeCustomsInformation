package com.msg.customs.application.port.in;

import java.time.LocalDate;
import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.model.LleStatus;

/**
 * UseCase für LLE-Management (Phase 3).
 * Ermöglicht PDF-Verwaltung, Status-Abfragen und LLE-Erneuerung.
 */
public interface ManageLleUseCase {

	// ==================== PDF-Verwaltung ====================

	/**
	 * Lädt ein PDF für eine LLE hoch.
	 * @param lleId ID der LLE
	 * @param pdfContent PDF-Inhalt als Byte-Array
	 * @param fileName Dateiname des PDFs
	 */
	void uploadPdf(Long lleId, byte[] pdfContent, String fileName);

	/**
	 * Lädt das PDF einer LLE herunter.
	 * @param lleId ID der LLE
	 * @return PDF-Inhalt als Byte-Array
	 */
	byte[] downloadPdf(Long lleId);

	/**
	 * Löscht das PDF einer LLE.
	 * @param lleId ID der LLE
	 */
	void deletePdf(Long lleId);

	/**
	 * Gibt den PDF-Dateinamen einer LLE zurück.
	 * @param lleId ID der LLE
	 * @return Dateiname oder null wenn kein PDF vorhanden
	 */
	String getPdfFileName(Long lleId);

	// ==================== Status-Abfragen ====================

	/**
	 * Lädt alle LLEs mit einem bestimmten Status.
	 * @param status der gewünschte LLE-Status
	 * @return Liste der LLEs mit diesem Status
	 */
	List<CustomsData> loadLlesByStatus(LleStatus status);

	/**
	 * Lädt alle LLEs, die in den nächsten X Tagen ablaufen.
	 * @param daysUntilExpiry Tage bis zum Ablauf
	 * @return Liste der ablaufenden LLEs
	 */
	List<CustomsData> loadExpiringLles(int daysUntilExpiry);

	// ==================== Dashboard-Statistiken ====================

	/**
	 * Zählt die Gesamtzahl aller LLEs.
	 * @return Gesamtzahl
	 */
	long countTotal();

	/**
	 * Zählt LLEs mit einem bestimmten Status.
	 * @param status der LLE-Status
	 * @return Anzahl
	 */
	long countByStatus(LleStatus status);

	// ==================== LLE-Erneuerung ====================

	/**
	 * Erneuert eine LLE durch Erstellen einer Kopie mit neuem validFrom.
	 * Die alte LLE wird als Predecessor verknüpft.
	 * @param lleId ID der zu erneuernden LLE
	 * @return die neue LLE
	 */
	CustomsData renewLle(Long lleId);

	/**
	 * Lädt eine LLE anhand ihrer ID.
	 * @param lleId ID der LLE
	 * @return die LLE oder null wenn nicht gefunden
	 */
	CustomsData loadLleById(Long lleId);
}
