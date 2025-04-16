package com.msg.customs.application.port.in;

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
}
