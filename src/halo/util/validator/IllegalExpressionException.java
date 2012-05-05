package halo.util.validator;

public class IllegalExpressionException extends RuntimeException {

    private static final long serialVersionUID = -1714329890734836200L;

    public IllegalExpressionException(String arg0) {
        super(arg0);
    }

    public IllegalExpressionException() {
        super();
    }

    public IllegalExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalExpressionException(Throwable cause) {
        super(cause);
    }
}
