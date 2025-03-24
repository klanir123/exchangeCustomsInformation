package com.msg.customs.adapter.out.storage.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Database representation of trade worth data.
 */
@Getter
@Setter
@ToString
@Embeddable
public class TracedWorthEntity {

    @Column(name = "traced_worth_value")
    private String value;

    @Column(name = "traced_worth_currency")
    private String currency;
}
