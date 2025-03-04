package com.xenia.server.DTO;

import java.util.List;

public class ContentfulResponse {
    private Sys sys;
    private String errorResponse;
    private List<ContentfulItem> items;
    private int total;
    private int skip;
    private int limit;

    // Getters and Setters
    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(String errorResponse) {
        this.errorResponse = errorResponse;
    }

    public List<ContentfulItem> getItems() {
        return items;
    }

    public void setItems(List<ContentfulItem> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    // Inner class ContentfulItem
    public static class ContentfulItem {
        private String field1;
        private String field2;

        // Getters and Setters
        public String getField1() {
            return field1;
        }

        public void setField1(String field1) {
            this.field1 = field1;
        }

        public String getField2() {
            return field2;
        }

        public void setField2(String field2) {
            this.field2 = field2;
        }
    }

    // Sys class
    public static class Sys {
        private String type;
        private String id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
