package testfactory.factory;

import testfactory.product.Broom;
import testfactory.product.Moveable;

/**
 * Created by Administrator on 2016/7/28.
 */
public class BroomFactory extends VehicleFactory{
    public Moveable create(){
        return new Broom();
    }
}
