package com.msg.customs.application.port.in;

import java.util.List;

import com.msg.customs.application.domain.model.CustomsData;

/**
 * UseCase to load a list of customs data.
 */
public interface LoadCustomsDataListUseCase {
	
	/**
	 * Load a list of customs data.
	 * 
	 * @param queryCustomsData the query used to filter the data
	 * @return the list of customs data filtered by the query.
	 */
	List<CustomsData> loadCustomsData(LoadCustomsDataListQuery queryCustomsData);
	
	/**
	 * 	Get all customs data (no filtering).
	 * @return list of all customs data
	 */
	default LoadCustomsDataListQuery queryAll() {
		return new LoadCustomsDataListQuery();
	}

}
