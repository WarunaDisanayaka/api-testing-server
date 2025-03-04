package com.xenia.server.controller;

import com.xenia.server.DTO.PersonalizationRequest;
import com.xenia.server.service.PersonalizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;



@RestController
@RequestMapping("/api/personalization")
public class PersonalizationController {
    
    @Autowired
    private final PersonalizationService personalizationService;

    public PersonalizationController(PersonalizationService personalizationService) {
        this.personalizationService = personalizationService;
    }

    @PostMapping("/variant")
    public ResponseEntity<Map<String, Object>> getVariant(@RequestBody PersonalizationRequest request) {
        try {
            // Call the service method to get the final content
            return personalizationService.getFinalContent(request);
        } catch (Exception e) {
            // Log the exception for debugging purposes
            Logger logger = LoggerFactory.getLogger(PersonalizationController.class);
            logger.error("Error occurred in getVariant method", e);

            // Build error response
            ErrorResponse errorResponse = buildErrorResponse(e);

            // Wrap the error response in a Map
            Map<String, Object> errorMap = new HashMap<>();
            errorMap.put("error", errorResponse); // Assuming ErrorResponse has meaningful fields

            // Return an internal server error with the errorMap
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMap);
        }
    }


    private ErrorResponse buildErrorResponse(Exception e) {
        return new ErrorResponse(
        ) {
            @Override
            public HttpStatusCode getStatusCode() {
                return null;
            }

            @Override
            public ProblemDetail getBody() {
                return null;
            }
        };
    }


}

