package toy.swak.exceptions;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class CircularReferenceException extends RuntimeException {
    private final String message = "Some components are circular references.";

    public CircularReferenceException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
