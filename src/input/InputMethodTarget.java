package input;
 
import java.lang.annotation.Retention.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InputMethodTarget {
	
	String name();
	//SupportedInput param();
	
	//TODO: make the param() a enum with all supported inputs:
	//public enum SupportInput {foo, bar, etc} so its easier to write
}
