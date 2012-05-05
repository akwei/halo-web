package halo.util.image;

public class ImageException extends Exception {

    private static final long serialVersionUID = 984806475766148149L;

    public ImageException() {
        super();
    }

    public ImageException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageException(String message) {
        super(message);
    }

    public ImageException(Throwable cause) {
        super(cause);
    }
}