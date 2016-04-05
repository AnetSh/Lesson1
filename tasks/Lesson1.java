package lesson1.tasks;

import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Anet
 * Date: 31.03.16
 * Time: 20:31
 * Task1:
 * Create an annotation which gets parameters of the method. Call the method that is marked by annotation.
 * Send parameters of annotation in this method.
 * Task 2:
 * Write a class Text Container which contains a string. Specify using annotations:
 * 1) file which saves the text;
 * 2) method which executes saving.
 * Write a class Saver which saves field of TextContainer into  the specified file.
 * Task 3:
 * Serialize and deserialize all the fields that are marked with the annotation @Save into / from a file.
 */
public class Lesson1 {

    public static TextContainer textContainer;

    /**
     * Search all methods with annotation AnnotationTask1. Call them with the parameters indicated in the annotation.
     * @throws InvocationTargetException
     * @throws IllegalAccessException If method does not have access to the definition of the specified class
     */
    public static void task1() throws InvocationTargetException, IllegalAccessException {
        ArrayList<Method> methods = findMethod(AnnotationTask1.class, Lesson1.class,1);
        for (Method method: methods){
            AnnotationTask1 annotation = method.getAnnotation(AnnotationTask1.class);
            method.invoke(null, annotation.name(), annotation.author(), annotation.date());
        }
    }

    /**
     * Saves string in the TextContainer. Search all methods with annotation Saver and SaveTo.
     * Call them with the filename indicated in the annotation SaveTo.
     * @param str String that is stored in the TextContainer
     * @throws InvocationTargetException
     * @throws IllegalAccessException If method does not have access to the definition of the specified class
     */
    public static void task2(String str) throws InvocationTargetException, IllegalAccessException {
        textContainer=new TextContainer(str);
        ArrayList<Method> methods  = findMethod(Saver.class, IOMethods.class,2);
        for (Method method : methods){
            SaveTo annotation = method.getAnnotation(SaveTo.class);
            method.invoke(null, annotation.fileName(),textContainer);
        }
        System.out.println("Task 2 done");
    }

    /**
     * Serialize the provided object to string. Saves string in the TextContainer.
     * Write/read object to/from the file.
     * @param obj Object that is to be serialized to file
     * @param fileName  Name of file where object is wrote and read.
     * @return Provides an object deserialized from the file.
     * @throws InstantiationException  If specified class object cannot be instantiated during deserialize
     * @throws IllegalAccessException If object does not have access to the definition of the specified class
     * @throws IOException
     * @throws NoSuchFieldException If class doesn't have a field of a specified name.
     */
    public static Object task3(Object obj,String fileName) throws InstantiationException, IllegalAccessException, IOException, NoSuchFieldException {
        textContainer=new TextContainer(IOMethods.serialize(obj));
        IOMethods.save(fileName, textContainer);
        return IOMethods.deserialize(IOMethods.read(fileName), PersonTask3.class);
    }

    /**
     * Output parameters which indicated in the annotation AnnotationTask1
     * @param str1 Name of the task
     * @param str2 Author
     * @param str3 Date
     */
    @AnnotationTask1(name="Task 1", author = "Sandwich", date = "31.03.2016")
    public static void saySth(String str1,String str2, String str3){
        System.out.println(str1);
        System.out.println("Author: "+str2);
        System.out.println("Date: "+str3);
    }

    /**
     * Search all methods with specified annotation. Save them into ArrayList.
     * @param annotation Annotation marks required methods
     * @param cl Class definition of method
     * @param task Task number
     * @return ArrayList with methods
     */
    public static ArrayList<Method> findMethod( Class<? extends Annotation> annotation, Class<?> cl, int task){
        ArrayList<Method> m=new ArrayList<Method>();
        Method[] methods = cl.getDeclaredMethods();
        for (Method method : methods){
            if (method.isAnnotationPresent(annotation)) {
                if((task==2)&&(!method.isAnnotationPresent(SaveTo.class))){
                    continue;
                }
                m.add(method);
            }
        }
        return m;
    }

}
