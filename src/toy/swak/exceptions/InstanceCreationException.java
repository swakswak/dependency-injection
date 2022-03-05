package toy.swak.exceptions;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class InstanceCreationException extends RuntimeException{
    private final String message;

    public InstanceCreationException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
