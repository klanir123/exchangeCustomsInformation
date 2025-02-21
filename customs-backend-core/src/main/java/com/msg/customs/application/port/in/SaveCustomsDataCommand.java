package com.msg.customs.application.port.in;

import com.msg.customs.application.domain.model.CustomsData;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Builder
@Getter
public class SaveCustomsDataCommand {
	private final CustomsData customsData;
}