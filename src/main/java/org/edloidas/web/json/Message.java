package org.edloidas.web.json;

/**
 * Common interface for json response message.
 * Contains special request/response codes for communication.
 */
public interface Message {

    public static final short CODE_SUCCESS        = 0;
    public static final short CODE_NOT_LOGGED     = 1;
    public static final short CODE_NO_PROJECT     = 2;
    public static final short CODE_SERVER_ERROR   = 3;
    public static final short CODE_UPDATED        = 4;
    public static final short CODE_NO_RIGHTS      = 5;

    @Override
    public abstract String toString();
}
