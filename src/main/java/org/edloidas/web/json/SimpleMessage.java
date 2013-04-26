package org.edloidas.web.json;

/**
 * Session message json response. Contains code, hash and sometimes data.
 */
public class SimpleMessage implements Message {

    /** Message code, as additional information. */
    private short code;

    public SimpleMessage(short code) {
        this.code = code;
    }

    public SimpleMessage() {
        this.code = 0;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return ("{\"code\":" + code + "}");
    }
}
