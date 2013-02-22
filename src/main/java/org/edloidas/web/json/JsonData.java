package org.edloidas.web.json;

import java.io.Serializable;

/**
 * Common JSON data for response.
 *
 * @author edloidas@gmail.com
 */
public class JsonData implements Serializable {
    /**
     * Application response (request) code.
     *
     * @see org.edloidas.web.json.ErrorCode;
     */
    private int code;

    /** Server/client message. Can holds some info, or code explanation. */
    private String message;

    /** Data of application. */
    private String data;

    public JsonData(int code, String data) {
        this.code = code;
        this.message = ErrorCode.getCode(code);
        this.data = data;
    }

    public JsonData(int code, String message, String data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "{\"code\":" + code + "," +
                "\"msg\":\"" + message + "\"" + "," +
                "\"data\":\"" + data + "\"}";
    }
}
