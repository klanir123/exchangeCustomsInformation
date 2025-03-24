package com.msg.customs.service;

import org.springframework.stereotype.Service;

import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTerritoryEnum;
import com.msg.customs.model.CustomsUnion;
import com.msg.customs.model.CustomsUnionEnum;

@Service
public class UnionMemebershipService {

	public boolean checkUnionMembership(String country) {
		if (country != null) {
			CustomsTerritory customsTerritory = CustomsTerritoryEnum.valueOf(country);
			CustomsUnion customsUnion = customsTerritory.getCustomsUnion();
			if (customsUnion != null) {
				return customsUnion == CustomsUnionEnum.EU;
			}						
		}		
		return false;
	}

}
