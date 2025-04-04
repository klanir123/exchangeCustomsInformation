package com.msg.customs.adapter.out.notifications.api;

import com.msg.customs.adapter.out.notifications.api.model.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * ManagementApi
 * <p>
 * This is a copy of the essential parts of the ManagementApi interface from the EDC project.
 * </p>
 * <p>
 * Unfortunately the EDC project does not provide a proper API for the ManagementApi interface. With this API
 * we want to improve the situation.
 * </p>
 */
public interface ManagementApi {

    @Operation(
            operationId = "requestCatalogV3",
            tags = {"Catalog V3"},
            responses = {@ApiResponse(
                    responseCode = "default",
                    description = "Gets contract offers (=catalog) of a single connector",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = CatalogResponse.class
                            )
                    )}
            )}
    )
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/v3/catalog/request"},
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<CatalogResponse> requestCatalogV3(@Parameter(name = "CatalogRequest", description = "") @RequestBody(required = false) @Valid CatalogRequest request);

    @Operation(
            operationId = "initiateContractNegotiationV3",
            description = "Initiates a contract negotiation for a given offer and with the given counter part. Please note that successfully invoking this endpoint only means that the negotiation was initiated. Clients must poll the /{id}/state endpoint to track the state",
            tags = {"Contract Negotiation V3"},
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "The negotiation was successfully initiated. Returns the contract negotiation ID and created timestamp",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(
                                    implementation = NegotiationResponse.class
                            )
                    )}
            ), @ApiResponse(
                    responseCode = "400",
                    description = "Request body was malformed",
                    content = {@Content(
                            mediaType = "application/json",
                            array = @ArraySchema(
                                    schema = @Schema(
                                            implementation = ApiErrorDetail.class
                                    )
                            )
                    )}
            )}
    )
    @RequestMapping(
            method = {RequestMethod.POST},
            value = {"/v3/contractnegotiations"},
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    ResponseEntity<NegotiationResponse> initiateContractNegotiationV3(@Parameter(name = "ContractRequest",description = "") @RequestBody(required = false) @Valid NegotiationRequest negotiation);

}
