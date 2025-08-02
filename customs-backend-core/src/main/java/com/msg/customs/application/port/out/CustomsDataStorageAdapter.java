package com.msg.customs.application.port.out;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.domain.model.MasterPart;
import com.msg.customs.application.domain.model.Task;

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
}
