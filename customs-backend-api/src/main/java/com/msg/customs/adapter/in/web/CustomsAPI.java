package com.msg.customs.adapter.in.web;

import java.util.List;

import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.model.LleStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.msg.customs.adapter.in.web.model.CustomsDataDto;

/**
 * API used o exchange customs data with ui.
 */
@RequestMapping(value = "/customs", produces = MediaType.APPLICATION_JSON_VALUE)
public interface CustomsAPI {

	/**
	 * Get all customs data (no filtering).
	 * @return list of all customs data
	 */
	@GetMapping
	ResponseEntity<List<CustomsDataDto>> getCustomsData();

    /// Get all customs data (no filtering).
    ///
    /// @return list of all master part data
    @GetMapping(value = "/parts")
    ResponseEntity<List<MasterPartDto>> getMasterPartsData();

    /**
	 * Get customs data by id.
	 * 
	 * @param id customs data id=catenaxId
	 * @return customs data
	 */
	@GetMapping(value = "/{id}")
	ResponseEntity<CustomsDataDto> getCustomsData(@PathVariable String id);

	/**
	 * Get masterpart data by id.
	 *
	 * @param id masterpart data id=catenaxId
	 * @return masterpart data
	 */
	@GetMapping(value = "/part/{id}")
	ResponseEntity<MasterPartDto> getMasterPartData(@PathVariable String id);

	/**
	 * Delete customs data by id.
	 * 
	 * @param catenaxId customs data id=catenaxId
	 * @return deleted customs data
	 */
	@DeleteMapping(value = "/{id}")
	ResponseEntity<CustomsDataDto> deleteCustomsData(@PathVariable String catenaxId);
	
	/**
	 * Save customs data.
	 * @param customsData the data which needs to be saved
	 * @return http status
	 */
	@PostMapping
	ResponseEntity<Void> saveCustomsData(@RequestBody CustomsDataDto customsData);

    /**
     * Save master partdata.
     *
     * @param masterPartData the data which needs to be saved
     * @return http status
     */
    @PostMapping(value = "/masterpart")
    ResponseEntity<Void> saveMasterPart(@RequestBody MasterPartDto masterPartData);


    /**
	 * Notify that customs data has been updated.
	 * 
	 * @param catenaxIds list of catenaxIds
	 * @return http status
	 */
	@GetMapping(value = "/notify") 
	ResponseEntity<Void> notifyCustomsDataUpdated(@RequestBody List<String> catenaxIds);


	/// Get all task data (no filtering).
	///
	/// @return list of all task data
	@GetMapping(value = "/tasks")
	ResponseEntity<List<TaskDto>> getTasksData();

	/**
	 * Delete masterpart data by catenax ids.
	 *
	 * @param catenaxIds comma-separated list of catenax ids
	 * @return http status
	 */
	@DeleteMapping(value = "/parts")
	ResponseEntity<Void> deleteMasterParts(@RequestBody List<String> catenaxIds);

	// ==================== Phase 3: LLE-Erweiterungen ====================

	/**
	 * FR-3.5: PDF hochladen für eine LLE.
	 * @param id LLE ID
	 * @param file PDF-Datei
	 * @return HTTP Status
	 */
	@PostMapping(value = "/{id}/pdf", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<Void> uploadPdf(@PathVariable Long id, @RequestParam("file") MultipartFile file);

	/**
	 * FR-3.5: PDF herunterladen für eine LLE.
	 * @param id LLE ID
	 * @return PDF als Byte-Array
	 */
	@GetMapping(value = "/{id}/pdf", produces = MediaType.APPLICATION_PDF_VALUE)
	ResponseEntity<byte[]> downloadPdf(@PathVariable Long id);

	/**
	 * FR-3.5: PDF löschen für eine LLE.
	 * @param id LLE ID
	 * @return HTTP Status
	 */
	@DeleteMapping(value = "/{id}/pdf")
	ResponseEntity<Void> deletePdf(@PathVariable Long id);

	/**
	 * FR-3.7: LLE erneuern (Kopie mit neuem validFrom erstellen).
	 * @param id ID der zu erneuernden LLE
	 * @return Die neue LLE
	 */
	@PostMapping(value = "/{id}/renew")
	ResponseEntity<CustomsDataDto> renewLle(@PathVariable Long id);

	/**
	 * FR-3.6: LLEs nach Status abrufen (für Dashboard).
	 * @param status LLE-Status zum Filtern
	 * @return Liste der LLEs mit diesem Status
	 */
	@GetMapping(value = "/by-status/{status}")
	ResponseEntity<List<CustomsDataDto>> getLlesByStatus(@PathVariable LleStatus status);

	/**
	 * FR-3.6: Ablaufende LLEs abrufen (für Dashboard).
	 * @param daysUntilExpiry Tage bis zum Ablauf
	 * @return Liste der ablaufenden LLEs
	 */
	@GetMapping(value = "/expiring")
	ResponseEntity<List<CustomsDataDto>> getExpiringLles(@RequestParam(defaultValue = "60") int daysUntilExpiry);

	/**
	 * FR-3.6: Dashboard-Statistiken abrufen.
	 * @return Statistik-DTO mit Anzahlen pro Status
	 */
	@GetMapping(value = "/dashboard/stats")
	ResponseEntity<DashboardStatsDto> getDashboardStats();

	/**
	 * DTO für Dashboard-Statistiken.
	 */
	record DashboardStatsDto(
		long totalCount,
		long draftCount,
		long activeCount,
		long expiringSoonCount,
		long criticalCount,
		long expiredCount
	) {}

}
