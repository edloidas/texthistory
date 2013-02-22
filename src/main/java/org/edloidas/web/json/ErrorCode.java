package org.edloidas.web.json;

/**
 * Common application codes for response.
 *
 * @author edloidas@gmail.com
 */
public final class ErrorCode {
    private ErrorCode() {
    }

    public static final String CODE_0 = "Success.";
    public static final String CODE_1 = "Server operation error.";
    public static final String CODE_2 = "Access denied.";
    public static final String CODE_3 = "Successfully updated.";
    public static final String CODE_4 = "Not found.";

    public static String getCode(int code) {
        switch (code) {
            case 0:
                return CODE_0;
            case 1:
                return CODE_1;
            case 2:
                return CODE_2;
            case 3:
                return CODE_3;
            case 4:
                return CODE_4;
            default:
                return "Not such code.";
        }
    }
}
