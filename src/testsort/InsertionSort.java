package testsort;

import java.util.stream.Stream;

/**
 * Created by ljzzkkkss on 2019/3/1.
 */
public class InsertionSort <T extends Comparable<T>> implements Sort<T> {

    @Override
    public void sort(T[] values) {
        int size = values.length;
        for(int i = 1; i < size; i++){
            T t = values[i];
            for(int j = i - 1; j >= 0; j--){
                if(t.compareTo(values[j]) < 1){
                    values[j + 1] = values[j];
                    values[j] = t;
                }
            }
        }
    }

    private static <T> T[] of(T...values){
        return values;
    }

    public static void main(String[] args) {
        Integer[] values = of(2,1,5,6,4,3);
        Sort<Integer> sort = new InsertionSort<>();

        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
