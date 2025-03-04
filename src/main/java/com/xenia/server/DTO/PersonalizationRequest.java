package com.xenia.server.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PersonalizationRequest {

    @JsonProperty("guestId")
    private String guestId;

    @JsonProperty("channel")
    private String channel;

    @JsonProperty("organization")
    private String organization;

    @JsonProperty("environmentId")
    private String environmentId;

    @JsonProperty("spaceId")
    private String spaceId;

    @JsonProperty("entryTypeObjects")
    private List<EntryTypeObject> entryTypeObjects;

    @JsonProperty("data")
    private DataPayload data;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)  // Optional: ensures that null fields are excluded from JSON
    public static class DataPayload {

        private User user;
        private UserData user_data;
        private Category category;
        private Supplier supplier;
        private SessionData sessionData;

        // You can also define a constructor if needed, especially when using @JsonCreator for explicit deserialization
    }

    @Data
    public static class EntryTypeObject {
        @JsonProperty("entryId")
        private String entryId;

        @JsonProperty("type")
        private String type;
    }

    // Define your other classes like User, UserData, Category, Supplier, and SessionData below as necessary
    // Example:
    @Data
    public static class User {
        private String name;
        private String city;
        private int age;
    }

    @Data
    public static class UserData {
        private String name;
        private String city;
        private int age;
    }

    @Data
    public static class Category {
        private String name;
    }

    @Data
    public static class Supplier {
        private int deliveryCount;
        private long deliveryDateTime;
        private boolean deliveryStatus;
        private List<Object> deliveryProductList;
    }

    @Data
    public static class SessionData {
        private String emailUser;
        private String nameUser;
    }
}
