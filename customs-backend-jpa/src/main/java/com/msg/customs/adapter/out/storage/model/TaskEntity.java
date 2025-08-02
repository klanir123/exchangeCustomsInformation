package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * Database representation of a Task.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "task")
public class TaskEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "catenax_id")
    private String catenaXId;

    @Column(name = "customer_part_name")
    private String customerPartName;

    @Column(name = "customer_part_number")
    private String customerPartNumber;

    @Column(name = "manufacturer_part_name")
    private String manufacturerPartName;

    @Column(name = "manufacturer_part_number")
    private String manufacturerPartNumber;

    @Column(name = "request_date")
    private Date requestDate;
}
