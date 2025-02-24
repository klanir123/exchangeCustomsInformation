package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.TradeWorthEntity;
import com.msg.customs.application.domain.model.TradeWorth;

/**
 * Mapper for trade worth domain model to entity.
 */
public class TradeWorthEntityMapper {
	
	/**
	 * Maps trade worth domain model to entity.
	 * @param tradeWorth the trade worth domain model
	 * @return the trade worth entity
	 */
	public static TradeWorthEntity toEntity(TradeWorth tradeWorth) {
		TradeWorthEntity tradeWorthEntity = new TradeWorthEntity();
		tradeWorthEntity.setCurrency(tradeWorth.getCurrency());
		tradeWorthEntity.setValue(tradeWorth.getValue());
		return tradeWorthEntity;
	}

	/**
	 * Maps trade worth entity to domain model.
	 * @param entity the trade worth entity
	 * @return the trade worth domain model
	 */
	public static TradeWorth toDomain(TradeWorthEntity entity) {
		TradeWorth tradeWorth = new TradeWorth();
		if (entity != null) {
			tradeWorth.setCurrency(entity.getCurrency());
			tradeWorth.setValue(entity.getValue());			
		}
		return tradeWorth;
	}
	
}
