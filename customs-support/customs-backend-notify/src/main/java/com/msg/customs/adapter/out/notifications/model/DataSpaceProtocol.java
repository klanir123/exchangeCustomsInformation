package com.msg.customs.adapter.out.notifications.model;

public enum DataSpaceProtocol {

    HTTP("dataspace-protocol-http"),

    ;

    private String protocol;

    private DataSpaceProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return protocol;
    }

    public static DataSpaceProtocol fromString(String protocol) {
        for (DataSpaceProtocol p : DataSpaceProtocol.values()) {
            if (p.getProtocol().equalsIgnoreCase(protocol)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown protocol: " + protocol);
    }

    @Override
    public String toString() {
        return protocol;
    }
}
