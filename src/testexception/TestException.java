package testexception;

/**
 * Created by Administrator on 2016/8/18.
 */
public class TestException {
    public void Exception(String str) throws IllegalArgumentException {
        if(str == null)
            throw new NullPointerException("This is a Exception!");
    }
    public void test(String str) {
        Exception(str);
    }
}
