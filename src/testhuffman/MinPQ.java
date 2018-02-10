package testhuffman;

/**
 * Created by ljzzkkkss on 2018/2/9.
 */
public class MinPQ<Key extends Comparable<Key>> {
    private Key[] pq; //基于堆的完全二叉树
    private int N = 0; //存储于pq[1...N]中，pq[0]没有使用

    public MinPQ(int minN){
        pq = (Key[])new Comparable[minN + 1];
    }

    public boolean isEmpty(){
        return N == 0;
    }

    public int size(){
        return N;
    }

    public void insert(Key k){
        pq[++N] = k;
        swim(N);
    }

    public Key delMin(){
        Key min = pq[1];
        exch(1, N--);
        pq[N+1] = null;//防止越界
        sink(1);
        return min;

    }

    private boolean more(int i, int j){//比较方法
        return pq[i].compareTo(pq[j]) > 0;
    }

    private void exch(int i, int j){//交换方法
        Key t = pq[i];
        pq[i] = pq[j];
        pq[j] = t;
    }

    private void swim(int k){//堆的上浮（由下而上有序化）
        while(k > 1 && more(k / 2, k )){
            exch(k / 2, k);
            k = k / 2;
        }
    }

    private void sink(int k){//堆的下沉（由上而下有序化）
        while(2 * k <= N){
            int j = 2 * k;
            if(j < N && more(j,j+1)){
                j++;
            };
            if(!more(k,j)){
                break;
            }
            exch(k,j);
            k = j;
        }
    }

}
