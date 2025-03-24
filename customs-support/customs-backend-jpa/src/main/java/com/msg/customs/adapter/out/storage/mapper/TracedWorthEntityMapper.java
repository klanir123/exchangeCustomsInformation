package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.TracedWorthEntity;
import com.msg.customs.application.domain.model.TracedWorth;

/**
 * Mapper for trade worth domain model to entity.
 */
public class TracedWorthEntityMapper {
	
	/**
	 * Maps trade worth domain model to entity.
	 * @param tracedWorth the trade worth domain model
	 * @return the trade worth entity
	 */
	public static TracedWorthEntity toEntity(TracedWorth tracedWorth) {
		TracedWorthEntity tracedWorthEntity = new TracedWorthEntity();
		tracedWorthEntity.setCurrency(tracedWorth.getCurrency());
		tracedWorthEntity.setValue(tracedWorth.getValue());
		return tracedWorthEntity;
	}

	/**
	 * Maps trade worth entity to domain model.
	 * @param entity the trade worth entity
	 * @return the trade worth domain model
	 */
	public static TracedWorth toDomain(TracedWorthEntity entity) {
		TracedWorth tracedWorth = new TracedWorth();
		if (entity != null) {
			tracedWorth.setCurrency(entity.getCurrency());
			tracedWorth.setValue(entity.getValue());
		}
		return tracedWorth;
	}
	
}
