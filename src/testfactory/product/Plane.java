package testfactory.product;

import testfactory.product.Moveable;

/**
 * Created by Administrator on 2016/7/28.
 */
public class Plane implements Moveable {
    @Override
    public void run() {
        System.out.println("plane......");
    }
}
