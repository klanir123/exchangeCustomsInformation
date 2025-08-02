package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.Task;

import java.util.List;

/**
 * UseCase to load a list of customs data.
 */
public interface LoadTaskDataListUseCase {


	List<Task> loadTaskData(LoadTaskListQuery queryTaskData);

	/**
	 * 	Get all customs data (no filtering).
	 * @return list of all customs data
	 */
    //default LoadTaskListQuery queryAll() { return new LoadTaskListQuery(); }
}
