package com.msg.customs.adapter.in.web.controller;


import java.io.IOException;
import java.util.List;

import com.msg.customs.adapter.in.web.mapper.MasterPartValueMapper;
import com.msg.customs.adapter.in.web.mapper.TaskValueMapper;
import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.adapter.in.web.model.TaskDto;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.application.port.in.*;
import com.msg.customs.model.LleStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.msg.customs.adapter.in.web.CustomsAPI;
import com.msg.customs.adapter.in.web.mapper.CustomsDataValueMapper;
import com.msg.customs.adapter.in.web.model.CustomsDataDto;
import com.msg.customs.application.domain.model.CustomsData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller for customs data.
 * <p>
 * A controller used the UseCases to call the business logic (DDD).
 * Erweitert für Phase 3: LLE-Management.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class CustomsAPIController implements CustomsAPI {

	private final LoadCustomsDataListUseCase loadCustomsDataUseCase;
	private final LoadMasterPartDataListUseCase loadMasterPartDataListUseCase;
	private final SaveCustomsDataUseCase saveCustomsDataUseCase;
	private final LoadCustomsDataByCatenaxIdUseCase loadCustomsDataByCatenaxIdUseCase;
	private final NotifyCustomsDataChangedUseCase notifyCustomsDataChangedUseCase;
	private final LoadTaskDataListUseCase loadTaskDataListUseCase;
	private final ManageLleUseCase manageLleUseCase;
	
	@Override
	public ResponseEntity<List<CustomsDataDto>> getCustomsData() {
		List<CustomsData> customsData = loadCustomsDataUseCase.loadCustomsData(LoadCustomsDataListQuery.queryAll());
		return ResponseEntity.ok(customsData.stream().map(CustomsDataValueMapper::toDto).toList());
	}



	@Override
	public ResponseEntity<List<MasterPartDto>> getMasterPartsData() {
		List<MasterPart> masterParts = loadMasterPartDataListUseCase.loadMasterPartData(LoadMasterPartListQuery.queryAll());
		return ResponseEntity.ok(masterParts.stream().map(MasterPartValueMapper::toDto).toList());
	}

	@GetMapping("/tasks")
	@Override
	public ResponseEntity<List<TaskDto>> getTasksData() {
		List<Task> tasks = loadTaskDataListUseCase.loadTaskData(LoadTaskListQuery.queryAll());
		return ResponseEntity.ok(tasks.stream().map(TaskValueMapper::toDto).toList());
	}

	@GetMapping("/{id}")
	@Override
	public ResponseEntity<CustomsDataDto> getCustomsData(String id) {
		CustomsData customsData = loadCustomsDataByCatenaxIdUseCase.loadCustomsData(id);
		return ResponseEntity.ok(CustomsDataValueMapper.toDto(customsData));
	}

	@GetMapping("/part/{id}")
	@Override
	public ResponseEntity<MasterPartDto> getMasterPartData(String id) {
		MasterPart masterPart = loadCustomsDataByCatenaxIdUseCase.loadMasterPart(id);
		return ResponseEntity.ok(MasterPartValueMapper.toDto(masterPart));
	}

	@Override
	public ResponseEntity<CustomsDataDto> deleteCustomsData(String catenaxId) {
		// TODO Auto-generated method stub
		return null;
	}



    @Override
	public ResponseEntity<Void> saveCustomsData(CustomsDataDto customsDataValue) {
		log.info(customsDataValue.toString());
		CustomsData customsData = CustomsDataValueMapper.toDomain(customsDataValue);
		saveCustomsDataUseCase.saveCustomsData(SaveCustomsDataCommand.builder().customsData(customsData).build());
		return ResponseEntity.ok().build();
	}

    @Override
    public ResponseEntity<Void> saveMasterPart(MasterPartDto masterPartData) {
		log.info(masterPartData.toString());
		MasterPart masterPart = MasterPartValueMapper.toDomain(masterPartData);

		saveCustomsDataUseCase.saveMasterPartData(SaveCustomsDataCommand.builder().masterPart(masterPart).build());
		return ResponseEntity.ok().build();

    }

    @Override
	public ResponseEntity<Void> notifyCustomsDataUpdated(List<String> catenaxIds) {
		notifyCustomsDataChangedUseCase.notifyCustomsDataChanged(getTargetBPN(), catenaxIds);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<Void> deleteMasterParts(List<String> catenaxIds) {
		log.info("Deleting master parts with catenaxIds: {}", catenaxIds);
		saveCustomsDataUseCase.deleteMasterParts(catenaxIds);
		return ResponseEntity.ok().build();
	}

	/**
	 * Returns a (fixed) target business partner number. For producion purpose this needs to be done mor flexible.
	 * @return a target business partner number
	 */
	private String getTargetBPN() {
		return "ALICE-987-654-321";
	}

	// ==================== Phase 3: LLE-Erweiterungen ====================

	@Override
	public ResponseEntity<Void> uploadPdf(Long id, MultipartFile file) {
		log.info("Uploading PDF '{}' for LLE {}", file.getOriginalFilename(), id);
		try {
			manageLleUseCase.uploadPdf(id, file.getBytes(), file.getOriginalFilename());
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			log.error("Error reading PDF file", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadPdf(Long id) {
		log.info("Downloading PDF for LLE {}", id);
		byte[] pdfContent = manageLleUseCase.downloadPdf(id);
		if (pdfContent == null) {
			return ResponseEntity.notFound().build();
		}

		String fileName = manageLleUseCase.getPdfFileName(id);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("attachment", fileName != null ? fileName : "document.pdf");

		return ResponseEntity.ok()
			.headers(headers)
			.body(pdfContent);
	}

	@Override
	public ResponseEntity<Void> deletePdf(Long id) {
		log.info("Deleting PDF for LLE {}", id);
		manageLleUseCase.deletePdf(id);
		return ResponseEntity.ok().build();
	}

	@Override
	public ResponseEntity<CustomsDataDto> renewLle(Long id) {
		log.info("Renewing LLE {}", id);
		CustomsData renewedLle = manageLleUseCase.renewLle(id);
		return ResponseEntity.ok(CustomsDataValueMapper.toDto(renewedLle));
	}

	@Override
	public ResponseEntity<List<CustomsDataDto>> getLlesByStatus(LleStatus status) {
		log.info("Loading LLEs with status {}", status);
		List<CustomsData> lles = manageLleUseCase.loadLlesByStatus(status);
		return ResponseEntity.ok(lles.stream().map(CustomsDataValueMapper::toDto).toList());
	}

	@Override
	public ResponseEntity<List<CustomsDataDto>> getExpiringLles(int daysUntilExpiry) {
		log.info("Loading LLEs expiring within {} days", daysUntilExpiry);
		List<CustomsData> lles = manageLleUseCase.loadExpiringLles(daysUntilExpiry);
		return ResponseEntity.ok(lles.stream().map(CustomsDataValueMapper::toDto).toList());
	}

	@Override
	public ResponseEntity<DashboardStatsDto> getDashboardStats() {
		log.info("Loading dashboard statistics");
		long total = manageLleUseCase.countTotal();
		long draft = manageLleUseCase.countByStatus(LleStatus.Draft);
		long active = manageLleUseCase.countByStatus(LleStatus.Active);
		long expiringSoon = manageLleUseCase.countByStatus(LleStatus.ExpiringSoon);
		long critical = manageLleUseCase.countByStatus(LleStatus.Critical);
		long expired = manageLleUseCase.countByStatus(LleStatus.Expired);

		return ResponseEntity.ok(new DashboardStatsDto(total, draft, active, expiringSoon, critical, expired));
	}
}
