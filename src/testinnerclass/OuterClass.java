package testinnerclass;

/**
 * Created by Administrator on 2016/7/29.
 */
public class OuterClass {
    public class Inner{}

    public static void main(String[] args){
        OuterClass oc = new OuterClass();
        Inner inner = oc.new Inner();
    }
}
