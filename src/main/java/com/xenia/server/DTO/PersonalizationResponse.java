package com.xenia.server.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
public class PersonalizationResponse {

    @JsonProperty("data")
    private List<DataObject> data;

    @JsonProperty("message")
    private String message;

    @JsonProperty("code")
    private String code;

    @JsonProperty("httpStatus")
    private String httpStatus;

    @JsonProperty("description")
    private String description;

    public List<DataObject> getData() {
        return data;
    }



    @Data
    public static class DataObject {
        @JsonProperty("entryId")
        private String entryId;

        @JsonProperty("variationId")
        private String variationId;

        @JsonProperty("caching")
        private boolean caching;

        @JsonProperty("userCount")
        private double userCount;


        public String getEntryId() {
            return entryId;
        }

        // Explicit getter for variationId
        public String getVariationId() {
            return variationId;
        }
    }




}
