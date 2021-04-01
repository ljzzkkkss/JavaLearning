package testobserver;

public class ObserverTest {
    public static void main(String[] args) {
        JavaStackObservable observable = new JavaStackObservable();

        observable.addObserver(new ReaderObserver("小明"));
        observable.addObserver(new ReaderObserver("小红"));
        observable.addObserver(new ReaderObserver("小王"));

        observable.publish("观察者如何使用");
    }
}
