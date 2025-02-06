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
public class TradeWorthEntity {

    @Column(name = "trade_worth_value")
    private String value;

    @Column(name = "trade_worth_currency")
    private String currency;
}
