package com.msg.customs.adapter.out.storage.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

    @Column(name = "catenax_id", nullable = false)
    private String catenaxId;

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

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "confirmation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date confirmationDate;

    @Column(name = "valid_from", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date validFrom;

    @Embedded
    private ContactEntity contact;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "federal_state")
    private String federalState;

    @ElementCollection
    @CollectionTable(name = "customs_origin_information", joinColumns = @JoinColumn(name = "customs_id"))
    @Column(name = "origin_information")
    private List<String> originInformation;

    @Column(name = "preffered_status")
    private String prefferedStatus;

    @Embedded
    private TradeWorthEntity tradeWorth;
}