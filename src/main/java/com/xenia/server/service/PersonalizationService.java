package com.xenia.server.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xenia.server.DTO.ContentfulResponse;
import com.xenia.server.DTO.PersonalizationRequest;
import com.xenia.server.DTO.PersonalizationResponse;
import com.xenia.server.controller.PersonalizationController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class PersonalizationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${external.api.baseUrl}")
    private String baseUrl;

    @Value("${external.api.contentfulUrl}")
    private String contentfulUrl;

    @Value("${external.api.accessToken}")
    private String accessToken;

    public PersonalizationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public ResponseEntity<Map<String, Object>> getFinalContent(PersonalizationRequest request) {
        Logger logger = LoggerFactory.getLogger(PersonalizationController.class);

        // 1. Call the first API
        logger.info("Calling first personalization API with request: {}", request);
        ResponseEntity<PersonalizationResponse> response = callPersonalizationAPI(request);

        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null) {
            logger.error("First API call failed. Status: {} | Body: {}", response.getStatusCode(), response.getBody());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyMap()); // Return empty or error response as Map
        }
        logger.info("First API call succeeded. Response body: {}", response.getBody());

        // 2. Extract variationId or entryId
        List<PersonalizationResponse.DataObject> dataList = response.getBody().getData();
        if (dataList == null || dataList.isEmpty()) {
            logger.warn("No data found in first API response.");
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(Collections.emptyMap()); // Return empty response
        }

        PersonalizationResponse.DataObject firstEntry = dataList.get(0);
        String idToUse = (firstEntry.getVariationId() == null || "null".equals(firstEntry.getVariationId()))
                ? firstEntry.getEntryId()
                : firstEntry.getVariationId();

        logger.info("Using id: {} for the second API call", idToUse);

        // 3. Call the second API using the extracted id
        String finalUrl = String.format("%s?access_token=%s&content_type=complexBanner&sys.id[in]=%s&include=10",
                contentfulUrl, accessToken, idToUse);

        logger.info("Calling second API with URL: {}", finalUrl);
        ResponseEntity<Map<String, Object>> finalResponse = restTemplate.exchange(
                finalUrl,
                HttpMethod.GET,
                null,  // No request body needed for GET
                new ParameterizedTypeReference<Map<String, Object>>() {} // Use ParameterizedTypeReference for Map
        );

        // Check if the final response was successful
        if (finalResponse.getStatusCode() != HttpStatus.OK || finalResponse.getBody() == null) {
            logger.error("Second API call failed. Status: {} | Body: {}", finalResponse.getStatusCode(), finalResponse.getBody());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.emptyMap()); // Return empty or error response
        }
        logger.info("Second API call succeeded. Response body: {}", finalResponse.getBody());

        // Return the full Map (JSON)
        return ResponseEntity.ok(finalResponse.getBody());

    }


    public ResponseEntity<PersonalizationResponse> callPersonalizationAPI(PersonalizationRequest request) {
        String url = baseUrl + "/p13n/d6n/variant";

        try {
            ResponseEntity<PersonalizationResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    new HttpEntity<>(request),
                    PersonalizationResponse.class
            );

            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Log or handle error response properly
        }
    }
}
