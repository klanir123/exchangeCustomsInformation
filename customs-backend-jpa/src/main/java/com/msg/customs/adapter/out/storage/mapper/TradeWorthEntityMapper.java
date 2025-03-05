package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.TradeWorthEntity;
import com.msg.customs.application.domain.model.TracedWorth;

/**
 * Mapper for trade worth domain model to entity.
 */
public class TradeWorthEntityMapper {
	
	/**
	 * Maps trade worth domain model to entity.
	 * @param tracedWorth the trade worth domain model
	 * @return the trade worth entity
	 */
	public static TradeWorthEntity toEntity(TracedWorth tracedWorth) {
		TradeWorthEntity tradeWorthEntity = new TradeWorthEntity();
		tradeWorthEntity.setCurrency(tracedWorth.getCurrency());
		tradeWorthEntity.setValue(tracedWorth.getValue());
		return tradeWorthEntity;
	}

	/**
	 * Maps trade worth entity to domain model.
	 * @param entity the trade worth entity
	 * @return the trade worth domain model
	 */
	public static TracedWorth toDomain(TradeWorthEntity entity) {
		TracedWorth tracedWorth = new TracedWorth();
		if (entity != null) {
			tracedWorth.setCurrency(entity.getCurrency());
			tracedWorth.setValue(entity.getValue());
		}
		return tracedWorth;
	}
	
}
