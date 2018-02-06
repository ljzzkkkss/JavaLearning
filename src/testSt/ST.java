package testSt;

/**
 * Created by ljzzkkkss on 2017/10/12.
 */
public class ST<Key extends Comparable<Key>, Value> {
    private Key[] keys;
    private Value[] vals;
    private int N;

    public ST(){
        keys = (Key[]) new Comparable[10];
        vals = (Value[]) new Object[10];
    }

    public int size(){
        return N;
    }

    private void resize(int size){
        Key[] tempkeys = (Key[]) new Comparable[size];
        Value[] tempvals = (Value[]) new Object[size];
        for(int i = 0;i < N; i++){
            tempkeys[i] = keys[i];
            tempvals[i] = vals[i];
        }
        keys = tempkeys;
        vals = tempvals;
    }

    private int rank(Key key){
        int lo = 0; int hi = N - 1;
        while(lo <= hi){
            int mid = lo + (hi - lo)/2;
            int cmp = key.compareTo(keys[mid]);
            if(cmp < 0){
                hi = mid - 1;
            }else if(cmp > 0){
                lo = mid + 1;
            }else {
                return mid;
            }
        }
        return lo;
    }

    public void put(Key key, Value val){
        int i = rank(key);
        if(N == keys.length){
            resize(2 * N);
        }
        if (i < N && keys[i].compareTo(key) == 0){
            vals[i] = val;
            return;
        }
        for(int j = N; j > i; j--){
            keys[j] = keys[j - 1];
            vals[j] = vals[j - 1];
        }
        keys[i] = key;
        vals[i] = val;
        N++;
    }

    public Value get(Key key){
        int i = rank(key);
        if (i < N && keys[i].compareTo(key) == 0){
            return vals[i];
        }else{
            return null;
        }
    }
}
