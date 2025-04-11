package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of a MasterPart.
 */
@Getter
@Setter
@ToString
@Embeddable
public class MasterPartEntity {

    @Column(name = "catenaXId")
    private String catenaXId;

    @Column(name = "customer_part_name")
    private String customerPartName;

    @Column(name = "customer_part_number")
    private String customerPartNumber;

    @Column(name = "manufacturer_part_name")
    private String manufacturerPartName;

    @Column(name = "manufacturer_part_number")
    private String manufacturerPartNumber;
}
