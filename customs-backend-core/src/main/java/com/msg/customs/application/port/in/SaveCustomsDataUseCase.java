package com.msg.customs.application.port.in;

import java.util.List;

/**
 * Use case to save customs data.
 */
public interface SaveCustomsDataUseCase {

	/**
	 * Saves the customs data.
	 * @param commandCustomsData the command to save the data
	 */
	void saveCustomsData(SaveCustomsDataCommand commandCustomsData);

	void saveMasterPartData(SaveCustomsDataCommand masterPartData);

	/**
	 * Deletes masterpart data by catenax ids.
	 * @param catenaxIds the list of catenax ids to delete
	 */
	void deleteMasterParts(List<String> catenaxIds);
}
