package com.msg.customs.adapter.out.storage.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters.LocalDateConverter;

import com.msg.customs.adapter.out.storage.converter.CustomsTerritoryConverter;
import com.msg.customs.adapter.out.storage.converter.FederalStateConverter;
import com.msg.customs.model.CertifiedEnum;
import com.msg.customs.model.CustomsTerritory;
import com.msg.customs.model.CustomsTypeEnum;
import com.msg.customs.model.FederalState;
import com.msg.customs.model.RequestStatusEnum;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "customs")
public class CustomsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catenax_id", nullable = false, unique = true)
    private String catenaxId;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "request_status", nullable = false)
    private RequestStatusEnum requestStatus;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "customer_part_number")),
        @AttributeOverride(name = "name", column = @Column(name = "customer_part_name"))
    })
    private PartEntity customerPart;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name = "number", column = @Column(name = "manufacturer_part_number")),
        @AttributeOverride(name = "name", column = @Column(name = "manufacturer_part_name"))
    })
    private PartEntity manufacturePart;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CustomsTypeEnum type;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "confirmation_date")
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate confirmationDate;

    @Convert(converter = LocalDateConverter.class)
    @Column(name = "valid_from", nullable = false)
//    @Temporal(TemporalType.TIMESTAMP)
    private LocalDate validFrom;

    @Embedded
    private ContactEntity contact;

    @Convert(converter = CustomsTerritoryConverter.class)
    @Column(name = "country", nullable = false)
    private CustomsTerritory country;

    @Convert(converter = FederalStateConverter.class)
    @Column(name = "federal_state")
    private FederalState federalState;

    @ElementCollection
    @CollectionTable(name = "customs_origin_information", joinColumns = @JoinColumn(name = "customs_id"))
    @Column(name = "origin_information")
    private List<String> originInformation;

    @Enumerated(EnumType.STRING)
    @Column(name = "preffered_status")
    private CertifiedEnum prefferedStatus;

    @Embedded
    private TradeWorthEntity tradeWorth;
}