package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Embeddable
public class PartEntity {

    @Column(name = "part_number")
    private String number;

    @Column(name = "part_name")
    private String name;

}
