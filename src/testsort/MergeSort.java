package testsort;

import java.util.stream.Stream;

/**
 * Created by ljzzkkkss on 2019/3/3.
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T>{


    @Override
    public void sort(T[] values) {
        int low = 0;
        int high = values.length - 1;

        sort(values, low, high);
    }

    private void merge(Comparable[] values, int mid, int low, int high){
        //非In-Place实现（创建新的数组）
        int n1 = mid - low + 1;
        int n2 = high - mid;
        Comparable[] a1 = new Comparable[n1];
        Comparable[] a2 = new Comparable[n2];

        System.arraycopy(values, low, a1, 0, n1);
        System.arraycopy(values, mid + 1, a2, 0, n2);

        int k = low;
        int i = 0,j = 0;
        for(; i < n1 && j < n2;){
            if(a1[i].compareTo(a2[j]) < 1){
                values[k] = a1[i];
                i++;
            }else{
                values[k] = a2[j];
                j++;
            }
            k++;
        }

        while(i < n1){
            values[k] = a1[i];
            i++;
            k++;
        }

        while(j < n2){
            values[k] = a2[j];
            j++;
            k++;
        }
    }

    private void sort(T[] values, int low, int high){
        if(low < high){
            int mid = (low + high) / 2;
            sort(values,low, mid);
            sort(values,mid + 1, high);

            merge(values, mid, low, high);

        }
    }

    private static <T> T[] of(T...values){
        return values;
    }

    public static void main(String[] args) {
        Integer[] values = of(2,1,5,6,4,3);
        Sort<Integer> sort = new MergeSort<>();

        sort.sort(values);
        Stream.of(values).forEach(System.out::println);
    }
}
