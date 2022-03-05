package toy.swak.exceptions;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
public class DependencyInjectionException extends RuntimeException{
    private final String message = "@Component annotated class must have only one constructor.";

    public DependencyInjectionException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
