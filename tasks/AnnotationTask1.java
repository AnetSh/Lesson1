package lesson1.tasks;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * User: Anet
 * Date: 31.03.16
 * Time: 20:29
 * To change this template use File | Settings | File Templates.
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface AnnotationTask1 {
    String name();
    String author();
    String date();
}
