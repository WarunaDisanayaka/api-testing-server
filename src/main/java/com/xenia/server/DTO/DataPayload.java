package com.xenia.server.DTO;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class DataPayload {

    private PersonalizationRequest.User user;
    private PersonalizationRequest.UserData user_data;
    private PersonalizationRequest.Category category;
    private PersonalizationRequest.Supplier supplier;
    private PersonalizationRequest.SessionData sessionData;

    @JsonCreator
    public DataPayload(
            @JsonProperty("user") PersonalizationRequest.User user,
            @JsonProperty("user_data") PersonalizationRequest.UserData user_data,
            @JsonProperty("category") PersonalizationRequest.Category category,
            @JsonProperty("supplier") PersonalizationRequest.Supplier supplier,
            @JsonProperty("sessionData") PersonalizationRequest.SessionData sessionData) {

        this.user = user;
        this.user_data = user_data;
        this.category = category;
        this.supplier = supplier;
        this.sessionData = sessionData;
    }
}
