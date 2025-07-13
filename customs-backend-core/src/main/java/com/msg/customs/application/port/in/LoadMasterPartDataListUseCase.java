package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.MasterPart;

import java.util.List;

/**
 * UseCase to load a list of customs data.
 */
public interface LoadMasterPartDataListUseCase {


	List<MasterPart> loadMasterPartData(LoadMasterPartListQuery queryMasterPartData);

	/**
	 * 	Get all customs data (no filtering).
	 * @return list of all customs data
	 */
    //default LoadMasterPartListQuery queryAll() { return new LoadMasterPartListQuery(); }
}
