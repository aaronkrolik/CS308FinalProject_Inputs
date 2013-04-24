package input;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * An Annotation which identifies a class as containing methods for input to call.
 * @author Aaron Krolik
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface InputClassTarget {

}
