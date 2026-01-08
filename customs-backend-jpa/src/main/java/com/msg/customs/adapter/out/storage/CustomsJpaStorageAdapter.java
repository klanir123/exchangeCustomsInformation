package com.msg.customs.adapter.out.storage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.msg.customs.adapter.out.storage.mapper.CustomsDataEntityMapper;
import com.msg.customs.adapter.out.storage.mapper.MasterPartEntityMapper;
import com.msg.customs.adapter.out.storage.mapper.TaskEntityMapper;
import com.msg.customs.adapter.out.storage.model.CustomsEntity;
import com.msg.customs.adapter.out.storage.model.MasterPartEntity;
import com.msg.customs.adapter.out.storage.model.TaskEntity;
import com.msg.customs.adapter.out.storage.repository.CustomsRepository;
import com.msg.customs.adapter.out.storage.repository.MasterPartRepository;
import com.msg.customs.adapter.out.storage.repository.TaskRepository;
import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;
import com.msg.customs.common.config.Adapter;
import com.msg.customs.model.LleStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * JPA implementation of the CustomsDataStorageAdapter.
 */
@Slf4j
@RequiredArgsConstructor
@Adapter
public class CustomsJpaStorageAdapter implements CustomsDataStorageAdapter {

	private final CustomsRepository customsRepository;
	private final MasterPartRepository partRepository;
	private final TaskRepository taskRepository;
    private MasterPart masterPartData;

    @Override
	public void saveCustomsData(CustomsData customsData) {
		CustomsEntity customsEntity = CustomsDataEntityMapper.toEntity(customsData);
		
		log.info(customsEntity.toString());
		
        customsRepository.save(customsEntity);		
	}

	@Override
    public void saveMasterPartData(MasterPart masterPartData) {
        this.masterPartData = masterPartData;
        MasterPartEntity masterPartEntity = MasterPartEntityMapper.toEntity(masterPartData);

		log.info(masterPartEntity.toString());

		partRepository.save(masterPartEntity);
	}

	@Override
	public List<CustomsData> loadCustomsData() {
		List<CustomsEntity> allCustoms = customsRepository.findAll();
		return allCustoms.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}

	@Override
	public CustomsData loadCustomsDataByCatenaxId(String catenaxId) {
		return CustomsDataEntityMapper.toDomain(customsRepository.findByCatenaxId(catenaxId));
	}

	@Override
	public MasterPart loadMasterPartDataByCatenaxId(String catenaXId) {
		return MasterPartEntityMapper.toDomain(partRepository.findByCatenaXId(catenaXId));
	}

	@Override
	public List<CustomsData> loadCustomsDataByCatenaxIds(List<String> catenaxIds) {
		List<CustomsEntity> data = customsRepository.findByCatenaxIdIn(catenaxIds);
		return data.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}

	@Override
	public List<MasterPart> loadMasterPartData() {
		List<MasterPartEntity> allCustoms = partRepository.findAll();
		return allCustoms.stream().map(MasterPartEntityMapper::toDomain).toList();
	}

	@Override
	public List<Task> loadTaskData() {
		List<TaskEntity> allTasks = taskRepository.findAll();
		return allTasks.stream().map(TaskEntityMapper::toDomain).toList();
	}

	@Override
	@Transactional
	public void deleteMasterPartByCatenaxId(String catenaxId) {
		partRepository.deleteByCatenaXId(catenaxId);
	}

	// ==================== Phase 3: LLE-Erweiterungen ====================

	@Override
	public CustomsData loadCustomsDataById(Long id) {
		return customsRepository.findById(id)
			.map(CustomsDataEntityMapper::toDomain)
			.orElse(null);
	}

	@Override
	public List<CustomsData> loadCustomsDataByStatus(LleStatus status) {
		List<CustomsEntity> entities = customsRepository.findByLleStatus(status);
		return entities.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}

	@Override
	public List<CustomsData> loadExpiringCustomsData(LocalDate today, LocalDate futureDate) {
		List<CustomsEntity> entities = customsRepository.findExpiringBetween(today, futureDate);
		return entities.stream().map(CustomsDataEntityMapper::toDomain).toList();
	}

	@Override
	public long countByLleStatus(LleStatus status) {
		return customsRepository.countByLleStatus(status);
	}

	@Override
	public long countTotal() {
		return customsRepository.count();
	}

	@Override
	@Transactional
	public void savePdf(Long id, byte[] pdfContent, String fileName) {
		customsRepository.findById(id).ifPresent(entity -> {
			entity.setPdfDocument(pdfContent);
			entity.setPdfFileName(fileName);
			entity.setPdfUploadedAt(LocalDateTime.now());
			customsRepository.save(entity);
			log.info("PDF '{}' für LLE {} gespeichert", fileName, id);
		});
	}

	@Override
	public byte[] loadPdf(Long id) {
		return customsRepository.findById(id)
			.map(CustomsEntity::getPdfDocument)
			.orElse(null);
	}

	@Override
	@Transactional
	public void deletePdf(Long id) {
		customsRepository.findById(id).ifPresent(entity -> {
			entity.setPdfDocument(null);
			entity.setPdfFileName(null);
			entity.setPdfUploadedAt(null);
			customsRepository.save(entity);
			log.info("PDF für LLE {} gelöscht", id);
		});
	}

	@Override
	public String getPdfFileName(Long id) {
		return customsRepository.findById(id)
			.map(CustomsEntity::getPdfFileName)
			.orElse(null);
	}

	@Override
	@Transactional
	public CustomsData saveCustomsDataWithPredecessor(CustomsData customsData, Long predecessorId) {
		CustomsEntity entity = CustomsDataEntityMapper.toEntity(customsData);

		// Predecessor setzen wenn vorhanden
		if (predecessorId != null) {
			customsRepository.findById(predecessorId)
				.ifPresent(entity::setPredecessor);
		}

		CustomsEntity savedEntity = customsRepository.save(entity);
		log.info("Neue LLE {} mit Predecessor {} erstellt", savedEntity.getId(), predecessorId);

		return CustomsDataEntityMapper.toDomain(savedEntity);
	}

}
