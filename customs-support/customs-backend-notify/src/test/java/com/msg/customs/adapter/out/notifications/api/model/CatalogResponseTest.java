package com.msg.customs.adapter.out.notifications.api.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CatalogResponseTest {

    private static final String POLICY_TYPE = "odrl:Offer";
    private static final String RESPONSE_TYPE = "dcat:Catalog";
    private static final String ACCESS_SERVICE_DESCRIPTION = "dspace:connector";
    private static final String DISTRIBUTION_FORMAT = "HttpData-PUSH";

    private static final String RESPONSE_ID = "fbd5bd18-3756-4dfe-8d05-033a6ffd4f1b";

    // Asset 1
    private static final String ASSET_ID = "assetId";
    private static final String POLICY_ID = "MQ==:YXNzZXRJZA==:YzdkYWQwYWItNDJjYy00YWNjLWFiNjYtMDVkZmFiYTI4YjNj";
    private static final String ACCESS_SERVICE_ENDPOINT = "http://localhost:19194/protocol";
    // Asset 2
    private static final String ASSET_ID_2 = "asset_2";
    private static final String POLICY_ID_2 = "MQ==:YXNzZXRfMg==:MWQ0MjZkOWQtMWNlNC00ZjY2LTgyNjAtM2I3ODM4YWZhNWIw";
    private static final String ACCESS_SERVICE_ENDPOINT_2 = "http://localhost:19194/protocol";

    // This JSON response was created with the examples provided with Tractus-X EDC

    private static final String CATALOG_RESPONSE = """
            {
                "@id": "%s",
                "@type": "dcat:Catalog",
                "dcat:dataset": {
                    "@id": "%s",
                    "@type": "dcat:Dataset",
                    "odrl:hasPolicy": {
                        "@id": "%s",
                        "@type": "odrl:Offer",
                        "odrl:permission": [],
                        "odrl:prohibition": [],
                        "odrl:obligation": []
                    },
                    "dcat:distribution": {
                        "@type": "dcat:Distribution",
                        "dct:format": {
                            "@id": "HttpData-PUSH"
                        },
                        "dcat:accessService": {
                            "@id": "ecea4f00-2a77-4974-be35-77a3571cce98",
                            "@type": "dcat:DataService",
                            "dcat:endpointDescription": "dspace:connector",
                            "dcat:endpointUrl": "%s",
                            "dcat:endpointURL": "http://localhost:19194/protocol"
                        }
                    },
                    "name": "product description",
                    "id": "assetId",
                    "contenttype": "application/json"
                },
                "dcat:catalog": [],
                "dcat:distribution": [],
                "dcat:service": {
                    "@id": "ecea4f00-2a77-4974-be35-77a3571cce98",
                    "@type": "dcat:DataService",
                    "dcat:endpointDescription": "dspace:connector",
                    "dcat:endpointUrl": "http://localhost:19194/protocol",
                    "dcat:endpointURL": "http://localhost:19194/protocol"
                },
                "dspace:participantId": "provider",
                "@context": {
                    "dcat": "http://www.w3.org/ns/dcat#",
                    "dct": "http://purl.org/dc/terms/",
                    "odrl": "http://www.w3.org/ns/odrl/2/",
                    "dspace": "https://w3id.org/dspace/v0.8/",
                    "@vocab": "https://w3id.org/edc/v0.0.1/ns/",
                    "edc": "https://w3id.org/edc/v0.0.1/ns/"
                }
            }
          """.formatted(RESPONSE_ID, ASSET_ID, POLICY_ID, ACCESS_SERVICE_ENDPOINT);

    // For the second response the Asset and Contract was duplicated and renamed.
    // It looks like that creating a second contract automatically generates a 'hasPolicy' sections with two items.

    private static final String CATALOG_RESPONSE_2 = """
            {
                "@id": "%s",
                "@type": "dcat:Catalog",
                "dcat:dataset": [
                    {
                        "@id": "%s",
                        "@type": "dcat:Dataset",
                        "odrl:hasPolicy": [
                            {
                                "@id": "%s",
                                "@type": "odrl:Offer",
                                "odrl:permission": [],
                                "odrl:prohibition": [],
                                "odrl:obligation": []
                            },
                            {
                                "@id": "MQ==:YXNzZXRJZA==:ZWU3ZjQ1MTAtYWU2NC00MjRmLWFkNjMtNzdhYjE1MWYwZDJk",
                                "@type": "odrl:Offer",
                                "odrl:permission": [],
                                "odrl:prohibition": [],
                                "odrl:obligation": []
                            }
                        ],
                        "dcat:distribution": {
                            "@type": "dcat:Distribution",
                            "dct:format": {
                                "@id": "HttpData-PUSH"
                            },
                            "dcat:accessService": {
                                "@id": "4ec6acae-7feb-4934-8c29-3e2b58bac16d",
                                "@type": "dcat:DataService",
                                "dcat:endpointDescription": "dspace:connector",
                                "dcat:endpointUrl": "%s",
                                "dcat:endpointURL": "http://localhost:19194/protocol"
                            }
                        },
                        "name": "product description",
                        "id": "assetId",
                        "contenttype": "application/json"
                    },
                    {
                        "@id": "%s",
                        "@type": "dcat:Dataset",
                        "odrl:hasPolicy": [
                            {
                                "@id": "%s",
                                "@type": "odrl:Offer",
                                "odrl:permission": [],
                                "odrl:prohibition": [],
                                "odrl:obligation": []
                            },
                            {
                                "@id": "Mg==:YXNzZXRfMg==:YTkzMDYyMjEtMGU5ZC00OWMwLThhZGEtOWYwYjQ2ZjUxOGI0",
                                "@type": "odrl:Offer",
                                "odrl:permission": [],
                                "odrl:prohibition": [],
                                "odrl:obligation": []
                            }
                        ],
                        "dcat:distribution": {
                            "@type": "dcat:Distribution",
                            "dct:format": {
                                "@id": "HttpData-PUSH"
                            },
                            "dcat:accessService": {
                                "@id": "4ec6acae-7feb-4934-8c29-3e2b58bac16d",
                                "@type": "dcat:DataService",
                                "dcat:endpointDescription": "dspace:connector",
                                "dcat:endpointUrl": "%s",
                                "dcat:endpointURL": "http://localhost:19194/protocol"
                            }
                        },
                        "name": "product_2 description",
                        "id": "asset_2",
                        "contenttype": "application/json"
                    }
                ],
                "dcat:catalog": [],
                "dcat:distribution": [],
                "dcat:service": {
                    "@id": "4ec6acae-7feb-4934-8c29-3e2b58bac16d",
                    "@type": "dcat:DataService",
                    "dcat:endpointDescription": "dspace:connector",
                    "dcat:endpointUrl": "http://localhost:19194/protocol",
                    "dcat:endpointURL": "http://localhost:19194/protocol"
                },
                "dspace:participantId": "provider",
                "@context": {
                    "dcat": "http://www.w3.org/ns/dcat#",
                    "dct": "http://purl.org/dc/terms/",
                    "odrl": "http://www.w3.org/ns/odrl/2/",
                    "dspace": "https://w3id.org/dspace/v0.8/",
                    "@vocab": "https://w3id.org/edc/v0.0.1/ns/",
                    "edc": "https://w3id.org/edc/v0.0.1/ns/"
                }
            }
            """.formatted(RESPONSE_ID, ASSET_ID, POLICY_ID, ACCESS_SERVICE_ENDPOINT, ASSET_ID_2, POLICY_ID_2, ACCESS_SERVICE_ENDPOINT_2);

    @Test
    void testReadResponse() throws JsonProcessingException {
        CatalogResponse response = JsonTestUtil.fromJson(CATALOG_RESPONSE, CatalogResponse.class);

        assertNotNull(response);
        assertEquals(RESPONSE_ID, response.getId());
        assertEquals(RESPONSE_TYPE, response.getType());
        assertEquals(1, response.getDataSets().size());

        DataSet dataSet = response.getDataSets().get(0);
        assertEquals(1, dataSet.getPolicies().size());
        Policy policy = dataSet.getPolicies().get(0);
        assertEquals(POLICY_ID, policy.getId());
        assertEquals(POLICY_TYPE, policy.getType());

        assertEquals(1, dataSet.getDistributions().size());
        Distribution distribution = dataSet.getDistributions().get(0);
        assertEquals(ACCESS_SERVICE_DESCRIPTION, distribution.getAccessService().getDescription());
        assertEquals(ACCESS_SERVICE_ENDPOINT, distribution.getAccessService().getEndpoint());
        assertEquals(DISTRIBUTION_FORMAT, distribution.getFormat().getId());
    }

    @Test
    void testReadResponse2() throws JsonProcessingException {
        CatalogResponse response = JsonTestUtil.fromJson(CATALOG_RESPONSE_2, CatalogResponse.class);

        assertNotNull(response);
        assertEquals(RESPONSE_ID, response.getId());
        assertEquals(RESPONSE_TYPE, response.getType());
        assertEquals(2, response.getDataSets().size());

        DataSet dataSetForAssetId = getDataSetForAssetId(ASSET_ID, response.getDataSets());
        assertNotNull(dataSetForAssetId);
        assertEquals(2, dataSetForAssetId.getPolicies().size());
        Policy policyForPolicyId = getPolicyForAssetPolicyId(POLICY_ID, dataSetForAssetId.getPolicies());
        assertNotNull(policyForPolicyId);
        assertEquals(POLICY_TYPE, policyForPolicyId.getType());
        assertEquals(1, dataSetForAssetId.getDistributions().size());
        Distribution distribution = dataSetForAssetId.getDistributions().get(0);
        assertEquals(ACCESS_SERVICE_DESCRIPTION, distribution.getAccessService().getDescription());
        assertEquals(ACCESS_SERVICE_ENDPOINT, distribution.getAccessService().getEndpoint());
        assertEquals(DISTRIBUTION_FORMAT, distribution.getFormat().getId());

        dataSetForAssetId = getDataSetForAssetId(ASSET_ID_2, response.getDataSets());
        assertNotNull(dataSetForAssetId);
        assertEquals(2, dataSetForAssetId.getPolicies().size());
        policyForPolicyId = getPolicyForAssetPolicyId(POLICY_ID_2, dataSetForAssetId.getPolicies());
        assertNotNull(policyForPolicyId);
        assertEquals(POLICY_TYPE, policyForPolicyId.getType());
        assertEquals(1, dataSetForAssetId.getDistributions().size());
        distribution = dataSetForAssetId.getDistributions().get(0);
        assertEquals(ACCESS_SERVICE_DESCRIPTION, distribution.getAccessService().getDescription());
        assertEquals(ACCESS_SERVICE_ENDPOINT, distribution.getAccessService().getEndpoint());
        assertEquals(DISTRIBUTION_FORMAT, distribution.getFormat().getId());
    }

    private DataSet getDataSetForAssetId(String assetId, List<DataSet> dataSets) {
        for (DataSet dataSet : dataSets) {
            if (dataSet.getAssetId().equals(assetId)) {
                return dataSet;
            }
        }
        return null;
    }

    private Policy getPolicyForAssetPolicyId(String policyId, List<Policy> policies) {
        for (Policy policy : policies) {
            if (policy.getId().equals(policyId)) {
                return policy;
            }
        }
        return null;
    }

}