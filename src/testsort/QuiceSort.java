package testsort;

import java.util.stream.Stream;

/**
 * Created by ljzzkkkss on 2019/3/3.
 */
public class QuiceSort<T extends Comparable<T>> implements Sort<T>{

    @Override
    public void sort(T[] values) {
        int n = values.length;
        int low = 0;
        int high = n -1;

        sort(values, low, high);
    }

    private void sort(T[] values, int low, int high){
        if(low < high){
            int pIndex = partition(values, low, high);//快速排序取末尾，重新分配之后末尾数对应的新的index
            sort(values, low, pIndex - 1);
            sort(values, pIndex, high);
        }
    }

    int partition(T[] values, int low, int high){
        T pviot = values[high];
        int i = low - 1; //游标
        for(int j = low; j < high; j++){
            if(values[j].compareTo(pviot) <= 0){
                i++;
                T temp = values[i];
                values[i] = values[j];
                values[j] = temp;
            }
        }

        T temp = values[i + 1];
        values[i + 1] = values[high];
        values[high] = temp;

        return i + 1; //滚动游标
    }



    private static <T> T[] of(T...values){
        return values;
    }

    public static void main(String[] args) {
        Integer[] values = of(2,1,5,6,4,3);
        Sort<Integer> sort = new QuiceSort<>();

        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
