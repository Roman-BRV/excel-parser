package ua.pp.helperzit.excelparser.rest;

public class RestException extends Exception {

    private static final long serialVersionUID = 1L;

    public RestException() {
        super();
    }

    public RestException(String message) {
        super(message);
    }

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

}
