package testfactory.main;

import testfactory.factory.BroomFactory;
import testfactory.factory.VehicleFactory;
import testfactory.product.Moveable;

/**
 * Created by Administrator on 2016/7/28.
 */
public class FactoryTest {
    public static void main(String[] args) {
        VehicleFactory factory = new BroomFactory();
        Moveable m = factory.create();
        m.run();
    }
}
