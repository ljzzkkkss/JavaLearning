package testinnerclass;

/**
 * Created by Administrator on 2016/8/1.
 */

class Destination {}
public class TestAnonymousInnerClass {
    // Argument must be final to use inside
    // anonymous inner class:
    //java8 has effectively final so here final can be left out
    public Destination destination(final String dest) {
        return new Destination() {
            private String label = dest;
            public String readLabel() { return label; }
        };
    }
    public static void main(String[] args) {
        TestAnonymousInnerClass p = new TestAnonymousInnerClass();
        Destination d = p.destination("Tasmania");
    }

}
