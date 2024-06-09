package com.addrsharingtool.addrsharingtool.constant;

public class APIPath {

    private APIPath() {}

    public static final String HEALTH_CHECK = "/health";

    public static class Address {

        private Address() {}

        public static final String ADD_ADDRESS = "/api/address/add/v1";
        public static final String UPDATE_ADDRESS = "/api/address/update/v1";
        public static final String DELETE_ADDRESS = "/api/address/delete/v1";
        public static final String FETCH_ADDRESS = "/api/address/fetch/v1";
        
    }

    public static class NotificationHandler {

        private NotificationHandler() {}

        public static final String ACCEPT_NOTIFICATION = "/api/notification/accept/v1/{encrypted_text}";
        public static final String DECLINE_NOTIFICATION = "/api/notification/decline/v1/{encrypted_text}";
        public static final String CHECK_NOTIFICATION_STATUS = "/api/notification/check/status/v1";

    }
    
}