package testsort;

import java.util.stream.Stream;

/**
 * Created by ljzzkkkss on 2019/3/1.
 */
public class BubbleSort<T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        int size = values.length;
        for(int i = 0; i < size - 1; i++){
            for(int j = 0; j < size - 1 - i; j++){
                T t = values[j];
                if(t.compareTo(values[j + 1]) > 0){
                    values[j] = values[j + 1];
                    values[j + 1] = t;
                }
            }
        }
    }

    private static <T> T[] of(T...values){
        return values;
    }

    public static void main(String[] args) {
        Integer[] values = of(2,1,5,6,4,3);
        Sort<Integer> sort = new BubbleSort<Integer>();

        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
