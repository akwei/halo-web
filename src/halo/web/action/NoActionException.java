package halo.web.action;

public class NoActionException extends Exception {

    private static final long serialVersionUID = -8914073991492873936L;

    public NoActionException(String message) {
        super(message);
    }

    public NoActionException() {
        super();
    }

    public NoActionException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoActionException(Throwable cause) {
        super(cause);
    }
}