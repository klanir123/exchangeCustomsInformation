package com.msg.customs.adapter.out.storage.mapper;

import com.msg.customs.adapter.out.storage.model.TradeWorthEntity;
import com.msg.customs.application.domain.model.TradeWorth;

public class TradeWorthEntityMapper {
	
	public static TradeWorthEntity toEntity(TradeWorth tradeWorth) {
		TradeWorthEntity tradeWorthEntity = new TradeWorthEntity();
		tradeWorthEntity.setCurrency(tradeWorth.getCurrency());
		tradeWorthEntity.setValue(tradeWorth.getValue());
		return tradeWorthEntity;
	}

	public static TradeWorth toDomain(TradeWorthEntity entity) {
		TradeWorth tradeWorth = new TradeWorth();
		if (entity != null) {
			tradeWorth.setCurrency(entity.getCurrency());
			tradeWorth.setValue(entity.getValue());			
		}
		return tradeWorth;
	}
	
}
