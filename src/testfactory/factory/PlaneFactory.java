package testfactory.factory;

import testfactory.product.Moveable;
import testfactory.product.Plane;

/**
 * Created by Administrator on 2016/7/28.
 */
public class PlaneFactory extends VehicleFactory{
    public Moveable create(){
        return new Plane();
    }
}
