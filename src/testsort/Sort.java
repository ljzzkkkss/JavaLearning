package testsort;

/**
 * Created by ljzzkkkss on 2019/3/1.
 * 原地（在地）排序 In-Place
 */
public interface Sort<T extends Comparable<T>> {
    void sort(T[] values);
}
