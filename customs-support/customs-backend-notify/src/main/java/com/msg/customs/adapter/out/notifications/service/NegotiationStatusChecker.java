package com.msg.customs.adapter.out.notifications.service;

import java.util.concurrent.*;

import com.msg.customs.adapter.out.notifications.api.json.JsonUtil;
import com.msg.customs.adapter.out.notifications.api.model.AgreementResponse;
import com.msg.customs.adapter.out.notifications.model.ContractAgreement;
import org.springframework.http.ResponseEntity;

public class NegotiationStatusChecker {

    private static final String NEGOTIATION_SUCCESS_STATE = "FINALIZED";

    private final RestService restService;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public NegotiationStatusChecker(RestService restService) {
        this.restService = restService;
    }

    public Future<ContractAgreement> startCheckingNegotiationStatus(String negotiationId) {
        CompletableFuture<ContractAgreement> future = new CompletableFuture<>();
        final Runnable checker = () -> {
            try {
                ContractAgreement agreement = checkNegotiationStatus(negotiationId);
                if (agreement != null) {
                    future.complete(agreement);
                }
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
        };
        scheduler.scheduleAtFixedRate(checker, 0, 30, TimeUnit.SECONDS);
        return future;
    }

    private ContractAgreement checkNegotiationStatus(String negotiationId) {
        ResponseEntity<String> response = restService.checkNegotiationStatus(negotiationId).callEntity();
        if (response.getStatusCode().is2xxSuccessful()) {
            // Process the response to check if the negotiation was successful
            // If successful, you can stop the scheduler
            AgreementResponse agreementResponse = JsonUtil.fromJson(response.getBody(), AgreementResponse.class);
            boolean isSuccessful = processResponse(agreementResponse);
            if (isSuccessful) {
                scheduler.shutdown();
                System.out.println("Negotiation was successful. Stopping the scheduler.");
                return ContractAgreement.builder()
                        .id(agreementResponse.getId())
                        .dataProviderUrl(agreementResponse.getCounterPartyAddress())
                        .agreementId(agreementResponse.getContractAgreementId())
                        .build();
            }
        } else {
            System.err.println("Error while checking negotiation status: " + response.getStatusCode());
        }
        return null; // Placeholder return value
    }

    private boolean processResponse(AgreementResponse agreementResponse) {
        return NEGOTIATION_SUCCESS_STATE.equals(agreementResponse.getState());
    }
}