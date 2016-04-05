package lesson1.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: Anet
 * Date: 31.03.16
 * Time: 20:50
 * Task 2.
 * Annotation marks method for storing in a file.
 */

@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Saver {
}
