import testfile.MakeDirectories;
import testhuffman.Huffman;

/**
 * Created by LiuJun on 2016/8/25.
 */
public class Main {
    public static void main(String[] args) {
        Huffman huffman = new Huffman();
        String huffmanStr = huffman.compress();
        String str = huffman.expand(huffmanStr);
    }

}
