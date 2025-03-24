package com.msg.customs.adapter.out.notifications.model;

import lombok.Getter;

/**
 * Wrapper for business partner number.
 */
@Getter
public class BPN {

    private String id;

    public static BPN of(String id) {
        BPN bpn = new BPN();
        bpn.id = id;
        return bpn;
    }
}
