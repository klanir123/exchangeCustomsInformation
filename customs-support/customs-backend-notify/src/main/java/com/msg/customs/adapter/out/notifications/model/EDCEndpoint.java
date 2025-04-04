package com.msg.customs.adapter.out.notifications.model;

import lombok.Builder;
import lombok.Data;

/**
 * Wrapper for EDC endpoint.
 */
@Data
@Builder
public class EDCEndpoint {

    private String endpoint;
}
