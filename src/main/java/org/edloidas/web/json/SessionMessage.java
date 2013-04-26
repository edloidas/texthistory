package org.edloidas.web.json;

/**
 * Session message json response. Contains code, hash and sometimes data.
 */
public class SessionMessage implements Message {

    /** Message code, as additional information. */
    private short code;

    /**
     * Session project snapshot as hash, that includes
     *  project id, name and text.
     */
    private String hash;

    /** Data representation. Can be {@code null}. */
    private SessionData data;

    public SessionMessage(short code) {
        this.code = code;
        this.hash = null;
        this.data = null;
    }

    public SessionMessage(String hash) {
        this.code = Message.CODE_SUCCESS;
        this.hash = hash;
        this.data = null;
    }

    public SessionMessage(short code, String hash) {
        this.code = code;
        this.hash = hash;
        this.data = null;
    }

    public SessionMessage(String hash, SessionData data) {
        this.code = Message.CODE_SUCCESS;
        this.hash = hash;
        this.data = data;
    }

    public SessionMessage(short code, String hash, SessionData data) {
        this.code = code;
        this.hash = hash;
        this.data = data;
    }

    public short getCode() {
        return code;
    }

    public void setCode(short code) {
        this.code = code;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public SessionData getData() {
        return data;
    }

    public void setData(SessionData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        StringBuilder msg = new StringBuilder();

        msg.append("{\"code\":").append(code).append(",\"hash\":");
        if (hash == null) {
            msg.append("\"\"");
        } else {
            msg.append(hash);
        }

        msg.append(",\"data\":");
        if (data == null) {
            msg.append("\"\"");
        } else {
            msg.append(data);
        }
        msg.append("}");

        return msg.toString();
    }

}
