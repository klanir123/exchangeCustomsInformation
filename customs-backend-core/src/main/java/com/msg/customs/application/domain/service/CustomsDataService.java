package com.msg.customs.application.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.application.port.in.*;
import com.msg.customs.model.LleStatus;
import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to manage customs data storage UseCases.
 * Erweitert für Phase 3: LLE-Management.
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CustomsDataService implements LoadCustomsDataListUseCase, LoadMasterPartDataListUseCase, LoadTaskDataListUseCase, SaveCustomsDataUseCase, LoadCustomsDataByCatenaxIdUseCase, ManageLleUseCase {
	
	private final CustomsDataStorageAdapter customsStorageAdapter;
	
	@Override
	public List<CustomsData> loadCustomsData(LoadCustomsDataListQuery queryCustomsData) {
		// validate query and filters
		// call CustomStorageService (probably with query filter)
		// return result
		return customsStorageAdapter.loadCustomsData();
	}

	@Override
	public CustomsData loadCustomsData(String catenaxId) {
		return customsStorageAdapter.loadCustomsDataByCatenaxId(catenaxId);
	}

	@Override
	public MasterPart loadMasterPart(String catenaxId) {
		return customsStorageAdapter.loadMasterPartDataByCatenaxId(catenaxId);
	}
	
	@Override
	public void saveCustomsData(SaveCustomsDataCommand commandCustomsData) {
		customsStorageAdapter.saveCustomsData(commandCustomsData.getCustomsData());		
	}

	@Override
	public void saveMasterPartData(SaveCustomsDataCommand masterPartData) {
		customsStorageAdapter.saveMasterPartData(masterPartData.getMasterPart());
	}

	@Override
	public List<MasterPart> loadMasterPartData(LoadMasterPartListQuery queryMasterPartData) {
		return customsStorageAdapter.loadMasterPartData();
	}

	@Override
	public List<Task> loadTaskData(LoadTaskListQuery queryTaskData) {
		return customsStorageAdapter.loadTaskData();
	}

	@Override
	public void deleteMasterParts(List<String> catenaxIds) {
		for (String catenaxId : catenaxIds) {
			customsStorageAdapter.deleteMasterPartByCatenaxId(catenaxId);
		}
	}

	// ==================== Phase 3: LLE-Management ====================

	@Override
	public void uploadPdf(Long lleId, byte[] pdfContent, String fileName) {
		log.info("Uploading PDF '{}' for LLE {}", fileName, lleId);
		customsStorageAdapter.savePdf(lleId, pdfContent, fileName);
	}

	@Override
	public byte[] downloadPdf(Long lleId) {
		log.info("Downloading PDF for LLE {}", lleId);
		return customsStorageAdapter.loadPdf(lleId);
	}

	@Override
	public void deletePdf(Long lleId) {
		log.info("Deleting PDF for LLE {}", lleId);
		customsStorageAdapter.deletePdf(lleId);
	}

	@Override
	public String getPdfFileName(Long lleId) {
		return customsStorageAdapter.getPdfFileName(lleId);
	}

	@Override
	public List<CustomsData> loadLlesByStatus(LleStatus status) {
		log.info("Loading LLEs with status {}", status);
		return customsStorageAdapter.loadCustomsDataByStatus(status);
	}

	@Override
	public List<CustomsData> loadExpiringLles(int daysUntilExpiry) {
		LocalDate today = LocalDate.now();
		LocalDate futureDate = today.plusDays(daysUntilExpiry);
		log.info("Loading LLEs expiring between {} and {}", today, futureDate);
		return customsStorageAdapter.loadExpiringCustomsData(today, futureDate);
	}

	@Override
	public long countTotal() {
		return customsStorageAdapter.countTotal();
	}

	@Override
	public long countByStatus(LleStatus status) {
		return customsStorageAdapter.countByLleStatus(status);
	}

	@Override
	public CustomsData renewLle(Long lleId) {
		log.info("Renewing LLE {}", lleId);

		// Alte LLE laden
		CustomsData oldLle = customsStorageAdapter.loadCustomsDataById(lleId);
		if (oldLle == null) {
			throw new IllegalArgumentException("LLE with ID " + lleId + " not found");
		}

		// Neue LLE erstellen als Kopie
		CustomsData newLle = new CustomsData();

		// Alle relevanten Felder kopieren
		newLle.setCatenaxId(UUID.randomUUID().toString()); // Neue ID
		newLle.setRequestStatus(oldLle.getRequestStatus());
		newLle.setCustomerPart(oldLle.getCustomerPart());
		newLle.setManufacturerPart(oldLle.getManufacturerPart());
		newLle.setType(oldLle.getType());
		newLle.setContact(oldLle.getContact());
		newLle.setCountry(oldLle.getCountry());
		newLle.setFederalState(oldLle.getFederalState());
		// Neue Liste erstellen um shared references zu vermeiden
		newLle.setOriginInformation(oldLle.getOriginInformation() != null
			? new ArrayList<>(oldLle.getOriginInformation())
			: new ArrayList<>());
		newLle.setPreferredStatus(oldLle.getPreferredStatus());
		newLle.setTracedWorth(oldLle.getTracedWorth());
		newLle.setSupplierBpnl(oldLle.getSupplierBpnl());

		// Neue Gültigkeitsdaten
		newLle.setValidFrom(LocalDate.now());
		newLle.setConfirmationDate(LocalDate.now());
		newLle.setLleStatus(LleStatus.Draft);

		// Speichern mit Predecessor-Verknüpfung
		CustomsData savedLle = customsStorageAdapter.saveCustomsDataWithPredecessor(newLle, lleId);
		log.info("Created renewed LLE {} as successor of {}", savedLle.getId(), lleId);

		return savedLle;
	}

	@Override
	public CustomsData loadLleById(Long lleId) {
		return customsStorageAdapter.loadCustomsDataById(lleId);
	}

}
