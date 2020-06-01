package com.bantuin.ticket.util;

public class ErrorDefinition {
    public enum ERROR {
        INTERNAL_SERVER_ERROR("Internal server error"),
        BAD_REQUEST("Bad Request"),
        UNAUTHORIZED("Unauthorized"),
        FORBIDDEN("Forbidden"),
        ERROR_VALIDATION("Error Validation"),
        DATABASE_TIMEOUT("Failed to connect to database"),

        USER_NOT_FOUND("User not found"),
        USER_SUSPENDED("User is suspended"),
        DATA_NOT_FOUND("Data not found"),
        PASSWORD_NOT_VALID("Password not valid"),
        API_KEY_NOT_VALID("API key is not valid"),
        TOKEN_NOT_VALID("Token is not valid"),

        // Excel Error Codes
        EXCEL_REQUIRED("Required"),
        EXCEL_NUMERIC_ONLY("Numeric only"),
        EXCEL_ONE_OF_REQUIRED("Required. Please fill one of the R, S, T, or U cell"),
        EXCEL_ALREADY_SAVED("Failed. Your data is already saved."),
        EXCEL_SUMAS_UPLOADED_FILE_REQUIRED("Failed. Sumas attachment is required"),
        EXCEL_SUMAS_UPLOAD_FILE_TOO_BIG("Failed. Max sumas attachment file is 5MB");

        private String message;

        ERROR(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

    public static String messageOf(String code) {
        try {
            return ERROR.valueOf(code).message;
        } catch (Exception e) {
            return "Unknown Error: " + code;
        }
    }
}
