package com.msg.customs.application.port.in;

import java.util.List;

public interface NotifyCustomsDataChangedUseCase {

	void notifyCustomsDataChanged(List<String> catenaxId);
}
