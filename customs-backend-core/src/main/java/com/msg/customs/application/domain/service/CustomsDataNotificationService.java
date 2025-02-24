package com.msg.customs.application.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.msg.customs.application.domain.model.CustomsData;
import com.msg.customs.application.port.in.NotifyCustomsDataChangedUseCase;
import com.msg.customs.application.port.out.CustomsDataNotificationsAdapter;
import com.msg.customs.application.port.out.CustomsDataStorageAdapter;

import lombok.RequiredArgsConstructor;

/**
 * Service implementing the UseCases to notify about customs data changes.
 */
@RequiredArgsConstructor
@Service
public class CustomsDataNotificationService implements NotifyCustomsDataChangedUseCase {

	private final CustomsDataNotificationsAdapter customsDataNotificationsAdapter;
	
	private final CustomsDataStorageAdapter customsDataStorageAdapter;

	@Override
	public void notifyCustomsDataChanged(List<String> catenaxIds) {
		List<CustomsData> dataByCatenaxIds = customsDataStorageAdapter.loadCustomsDataByCatenaxIds(catenaxIds);
		customsDataNotificationsAdapter.notifyCustomsDataChanged(dataByCatenaxIds);		
	}	
	
}
