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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a MasterPart.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "masterpart")
public class MasterPartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catenax_id", nullable = false, unique = true)
    private String catenaxId;

    @Column(name = "customer_part_name")
    private String customerPartName;

    @Column(name = "customer_part_number")
    private String customerPartNumber;

    @Column(name = "manufacturer_part_name")
    private String manufacturerPartName;

    @Column(name = "manufacturer_part_number")
    private String manufacturerPartNumber;
}
