package input;
 
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An Annotation which identifies a game behavior
 * method and a string name of the method used to
 * reference it in the mapping resource file.
 * @author Aaron Krolik
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InputMethodTarget {
    
    String name();
}
