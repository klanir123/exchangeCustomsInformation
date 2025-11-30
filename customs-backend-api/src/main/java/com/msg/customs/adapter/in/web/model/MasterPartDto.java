package com.msg.customs.adapter.in.web.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.RequestStatusEnum;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Simple class to exchange customs data with ui.
 */
@Data
public class MasterPartDto {

    private Long id;

    @JsonProperty("catenaxId")
    private String catenaxId;

    private String customerPartNumber;
    private String customerPartName;

    private String manufacturerPartNumber;
    private String manufacturerPartName;
}
