package com.msg.customs.adapter.in.web;

import java.util.List;

import com.msg.customs.adapter.in.web.model.MasterPartDto;
import com.msg.customs.adapter.in.web.model.TaskDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
		
}
