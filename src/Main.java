import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.function.Function;

/**
 * Created by LiuJun on 2016/8/25.
 */
public class Main {
    public static void main(String[] args) throws NoSuchFieldException {
        Function<String,Long> stringToLong = Long::valueOf;

        System.out.println(stringToLong.apply("1"));
    }

}
