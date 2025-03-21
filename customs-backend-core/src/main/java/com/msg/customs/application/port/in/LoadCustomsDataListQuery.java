package com.msg.customs.application.port.in;

/**
 * Query load customs data by a filter list.
 */
public class LoadCustomsDataListQuery {
	
	/**
	 * Create a load all query.
	 * 
	 * @return query
	 */
	public static LoadCustomsDataListQuery queryAll() {
		return new LoadCustomsDataListQuery();
	}
	
}