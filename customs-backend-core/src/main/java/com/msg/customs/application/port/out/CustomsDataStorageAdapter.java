package com.msg.customs.application.port.out;

import java.time.LocalDate;
import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.model.LleStatus;

/**
 * Adapter to store and load customs data.
 */
public interface CustomsDataStorageAdapter {

	/**
	 * Load all customs data.
	 * @return list with all customs data
	 */
	List<CustomsData> loadCustomsData();
	
	/**
	 * Load customs data by catenax ids.
	 * 
	 * @param catenaxIds list with the catenax ids of the customs data to load.
	 * @return list of loaded customs data.
	 */
	List<CustomsData> loadCustomsDataByCatenaxIds(List<String> catenaxIds);
	
	/**
	 * Load customs data by catenax id.
	 * @param catenaxId the catenax id of the customs data to load.
	 * @return the loaded customs data.
	 */
	CustomsData loadCustomsDataByCatenaxId(String catenaxId);

	/**
	 * Load masterpart data by catenax id.
	 * @param catenaxId the catenax id of the masterpart data to load.
	 * @return the loaded masterpart data.
	 */
	MasterPart loadMasterPartDataByCatenaxId(String catenaxId);

	/**
	 * Save customs data.
	 * @param customsData customs data to save
	 */
	void saveCustomsData(CustomsData customsData);

	/**
	 * Save masterpart data.
	 * @param masterPartData customs data to save
	 */
	void saveMasterPartData(MasterPart masterPartData);

	/**
	 * Load all customs data.
	 * @return list with all master part data
	 */
    List<MasterPart> loadMasterPartData();

	/**
	 * Load all task data.
	 *
	 * @return list with all task data
	 */
	List<Task> loadTaskData();

	/**
	 * Delete masterpart data by catenax id.
	 * @param catenaxId the catenax id of the masterpart to delete
	 */
	void deleteMasterPartByCatenaxId(String catenaxId);

	// ==================== Phase 3: LLE-Erweiterungen ====================

	/**
	 * Lädt eine LLE anhand ihrer ID.
	 * @param id die ID der LLE
	 * @return die LLE oder null wenn nicht gefunden
	 */
	CustomsData loadCustomsDataById(Long id);

	/**
	 * Lädt alle LLEs mit einem bestimmten Status.
	 * @param status der gewünschte LLE-Status
	 * @return Liste der LLEs mit diesem Status
	 */
	List<CustomsData> loadCustomsDataByStatus(LleStatus status);

	/**
	 * Lädt alle LLEs, die zwischen zwei Daten ablaufen.
	 * @param today heutiges Datum
	 * @param futureDate zukünftiges Datum
	 * @return Liste der ablaufenden LLEs
	 */
	List<CustomsData> loadExpiringCustomsData(LocalDate today, LocalDate futureDate);

	/**
	 * Zählt LLEs pro Status.
	 * @param status der LLE-Status
	 * @return Anzahl
	 */
	long countByLleStatus(LleStatus status);

	/**
	 * Zählt die Gesamtzahl aller LLEs.
	 * @return Gesamtzahl
	 */
	long countTotal();

	/**
	 * Speichert ein PDF für eine LLE.
	 * @param id die ID der LLE
	 * @param pdfContent PDF-Inhalt als Byte-Array
	 * @param fileName Dateiname des PDFs
	 */
	void savePdf(Long id, byte[] pdfContent, String fileName);

	/**
	 * Lädt das PDF einer LLE.
	 * @param id die ID der LLE
	 * @return PDF-Inhalt als Byte-Array oder null
	 */
	byte[] loadPdf(Long id);

	/**
	 * Löscht das PDF einer LLE.
	 * @param id die ID der LLE
	 */
	void deletePdf(Long id);

	/**
	 * Gibt den PDF-Dateinamen einer LLE zurück.
	 * @param id die ID der LLE
	 * @return Dateiname oder null
	 */
	String getPdfFileName(Long id);

	/**
	 * Speichert eine neue LLE mit Predecessor-Verknüpfung.
	 * @param customsData die neue LLE
	 * @param predecessorId ID der Vorgänger-LLE
	 * @return die gespeicherte LLE mit generierter ID
	 */
	CustomsData saveCustomsDataWithPredecessor(CustomsData customsData, Long predecessorId);
}
