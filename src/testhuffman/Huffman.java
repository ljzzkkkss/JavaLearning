package testhuffman;

/**
 * Created by ljzzkkkss on 2018/2/9.
 */
public class Huffman {
    private static int R = 256;
    private String[] buildCode(Node root){//使用单词查找数构造编译表
        String[] st = new String[R];
        buildCode(st, root, "");
        return st;
    }

    private void buildCode(String[] st, Node x, String s){//使用单词查找数构造编译表(递归)
        if(x.isLeaf()){
            st[x.ch] = s;
            return;
        }
        buildCode(st,x.left,s + '0');
        buildCode(st,x.right,s + '1');

    }

    private Node buildTrie(int[] freq){//构造单词查找树
        MinPQ<Node> pq = new MinPQ<Node>(R);
        for(char c = 0; c < R; c++){
            pq.insert(new Node(c, freq[c], null, null));
        }

        while(pq.size() > 1){
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0',x.freq + y.freq, x, y);
            pq.insert(parent);
        }

        return pq.delMin();
    }

    private void writeTrie(Node x){//输出单词查找树的比特流
        if(x.isLeaf()){
            StdOut.print(0);
            StdOut.print(x.ch);
            return;
        }
        StdOut.print(1);
        writeTrie(x.left);
        writeTrie(x.right);
    }


    private class Node implements Comparable<Node> {
        private char ch;//节点代表的字符
        private int freq;//字符出现的频率
        private final Node left,right;//左右子节点

        Node(char ch, int freq, Node left, Node right){
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

        public boolean isLeaf(){
            return (left == null && right == null);
        }
        @Override
        public int compareTo(Node that) {
            return this.freq - that.freq;
        }
    }

    private String toAscIIString(char c){//转为AscII的八位输出
        String ascii = Integer.toBinaryString(c);
        if(ascii.length() < 8){
            int length = ascii.length();//记录原先编码的长度
            for(int i = 0; i < 8 - length; i++){
                ascii = "0" + ascii;
            }
        }
        return ascii;
    }

    public void compress(){
        //读取输入
        String s = StdIn.readString();
        char[] input = s.toCharArray();

        //统计频率
        int[] freq = new int[R];
        for(int i = 0;i < input.length; i++){
            freq[input[i]]++;
        }

        //构造霍夫曼树
        Node root = buildTrie(freq);

        //(递归的)构造编译表
        String st[] = buildCode(root);

        //(递归的)打印解码用的单词查找树
        writeTrie(root);

        //打印字符总数
        StdOut.println("\n" + input.length);

        //原编码
        String original = "";//原来编码
        for(int i = 0; i < input.length; i++){
            original += toAscIIString(input[i]);
        }
        StdOut.println("original length is :" + original.length());
        StdOut.println(original);

        //用霍夫曼树处理
        String huffman = "";//霍夫曼编码
        for(int i = 0; i < input.length; i++){
            String code = st[input[i]];
            for(int j = 0;j < code.length(); j++){
                if(code.charAt(j) == '1'){
                    huffman += "1";
                }else{
                    huffman += "0";
                }
            }
        }
        StdOut.println("huffman length is :" + huffman.length());
        StdOut.println(huffman);
        StdOut.close();
    }
}
