package testobserver;

import java.util.Date;

public class ObserverTest {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000;i++){
            new Date().before(new Date());
        }
        System.out.println(System.currentTimeMillis() - start);
    }
}
