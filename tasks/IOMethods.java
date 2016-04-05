package lesson1.tasks;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.StringTokenizer;

/**
 * Created by IntelliJ IDEA.
 * User: Anet
 * Date: 05.04.16
 * Time: 13:44
 * The class read / write file, serialize / deserialize string.
 */
public class IOMethods {

    /**
     * Serialize the provided object to string.
     * @param obj  Object that is to be serialized to file
     * @return  String that is to be write to file
     * @throws IllegalAccessException  Thrown if either provided parameter is null.
     */
    public static String serialize(Object obj) throws IllegalAccessException {
        Class<?> cl = obj.getClass();
        Field[] fields = cl.getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : fields){
            if (field.isAnnotationPresent(Save.class)){
                if(Modifier.isPrivate(field.getModifiers())){
                    field.setAccessible(true);
                }
                stringBuffer.append(field.getType().getSimpleName()+"+"+field.getName()+"+"+field.get(obj)+" ");
            }
        }
        return stringBuffer.toString();
    }

    /**
     * Provides an object deserialized from the string.
     * @param str  String for deserialized
     * @param cl Class definition of object to be deserialized.
     * @param <T> Type of object to be deserialized.
     * @return Object deserialized from string as an instance of the provided class; may be null if something goes wrong with deserialization.
     * @throws IllegalAccessException If new object does not have access to the definition of the specified class
     * @throws InstantiationException If specified class object cannot be instantiated.
     * @throws NoSuchFieldException   If class doesn't have a field of a specified name.
     */
    public static <T> T deserialize(String str, Class<T> cl) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        T clObj = (T)cl.newInstance();
        StringTokenizer fileTok= new StringTokenizer(str);
        for (int i=0;i<=fileTok.countTokens()+1;i++){
            StringTokenizer objTok= new StringTokenizer(fileTok.nextElement().toString(),"+");
            String[] obj = new String[3];
            int k=0;
            while (objTok.hasMoreTokens()){
                obj[k] = objTok.nextToken();
                k++;
            }
            Field field = cl.getDeclaredField(obj[1]);
            if((field.getType().getSimpleName().equals(obj[0]))&&(field.isAnnotationPresent(Save.class))){
                if(Modifier.isPrivate(field.getModifiers())){
                    field.setAccessible(true);
                }
                switch (obj[0]){
                    case "int":
                        field.set(clObj, Integer.parseInt(obj[2]));
                        break;
                    case "long":
                        field.set(clObj, Long.parseLong(obj[2]));
                        break;
                    case "short":
                        field.set(clObj, Short.parseShort(obj[2]));
                        break;
                    case "boolean":
                        field.set(clObj, Boolean.parseBoolean(obj[2]));
                        break;
                    case "double":
                        field.set(clObj, Double.parseDouble(obj[2]));
                        break;
                    default:
                        field.set(clObj, obj[2]);
                }
            }
        }
        System.out.println("Task 3 done");
        return clObj;
    }

    /**
     * Read string from the file of the provided name.
     * @param filename Name of file from which string is to be read.
     * @return String from the file.
     * @throws IOException
     */
    public static String read(String filename) throws IOException {
        File file=new File(filename);
        FileReader reader = new FileReader(file);
        char[] buffer = new char[(int)file.length()];
        reader.read(buffer);
        return new String(buffer);
    }

    /**
     * Write string to the file of the provided name.
     * @param filename Name of file to which string is to be read.
     * @param obj Object of TextContainer which is contained the string
     * @throws IOException
     */
    @Saver
    @SaveTo(fileName = "Task2.txt")
    public static void save(String filename, TextContainer obj) throws IOException {
        FileWriter writer = new FileWriter(filename);
        writer.write(obj.getText());
        writer.flush();
    }
}
