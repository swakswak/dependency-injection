package toy.swak.annotations;

import java.lang.annotation.*;

/**
 * @author hyoseok choi (hschoi0702@gmail.com)
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
}
