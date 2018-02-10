package testhuffman;

/**
 * Created by ljzzkkkss on 2018/2/9.
 */
public class Huffman {
    private static int R = 256;
    private static String tempHuffmanCode = "";//临时存储输入的编码
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
        MinPQ<Node> pq = new MinPQ(R);
        for(char c = 0; c < R; c++){
            if(freq[c] > 0) {
                pq.insert(new Node(c, freq[c], null, null));
            }
        }

        while(pq.size() > 1){
            Node x = pq.delMin();
            Node y = pq.delMin();
            Node parent = new Node('\0',x.freq + y.freq, x, y);
            pq.insert(parent);
        }

        return pq.delMin();
    }

    private String writeTrie(Node x,String trie){//前序遍历输出单词查找树的比特流
        if(x.isLeaf()){
            trie += "1";
            trie += toASCIIString(x.ch);
            return trie;
        }
        trie += "0";
        trie = writeTrie(x.left,trie);
        trie = writeTrie(x.right,trie);
        return trie;
    }

    private Node readTrie(){//根据比特流重新建立单词查找树，会自动根据前缀读取，读到跟节点构造完毕为止，此时剩余的编码就是长度和huffman编码，因为前缀码刚好是到叶子节点为止，后面的编码已经无法影响树的生成了
        if(tempHuffmanCode.charAt(0) == '1'){
            char ch = toChar(tempHuffmanCode.substring(1,9));//根据生成规则，如果遇到1，后八位一定是字符的ASCII编码,转换为char
            tempHuffmanCode = tempHuffmanCode.substring(9,tempHuffmanCode.length());
            return new Node(ch,0,null,null);
        }
        tempHuffmanCode = tempHuffmanCode.substring(1,tempHuffmanCode.length());
        return new Node('\0',0,readTrie(),readTrie());
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

    private String toASCIIString(char c){//转为AscII的八位输出
        String ascii = Integer.toBinaryString(c);
        ascii = completionZero(ascii,8);
        return ascii;
    }

    private char toChar(String ascii){//转为AscII的八位输出
        return (char)Integer.valueOf(ascii,2).intValue();
    }

    private String getCharLength(int length) throws Exception {//获取字符总数的32位编码
        String charLength = Integer.toBinaryString(length);
        if(charLength.length() > 32){
            throw new Exception("input to large!");
        }
        charLength = completionZero(charLength,32);
        return charLength;
    }

    private String completionZero(String s,int digit){//根据位数用0补全字符串
        if(s.length() < digit){
            int length = s.length();//记录原先编码的长度
            for(int i = 0; i < digit - length; i++){
                s = "0" + s;
            }
        }
        return s;
    }

    private String extend(Node root, String code, int length) throws Exception {//根据字符长度，霍夫曼树和编码解压字符串
        String result = "";
        Node x = root;//临时树,初始化为root;
        int index = 0;
        while(result.length() < length && index != code.length()){//防止死循环
            if(code.charAt(index) == '0'){
                if(x.left.isLeaf()) {
                    result += x.left.ch;
                    x = root;
                }else{
                    x = x.left;
                }
                index++;
            }else{
                if(x.right.isLeaf()) {
                    result += x.right.ch;
                    x = root;
                }else{
                    x = x.right;
                }
                index++;
            }
        }
        if(index == code.length() && result.length() != length){//遍历完了字符长度对不上说明编码有误
            throw new Exception("wrong huffman code!");
        }
        return result;
    }

    public String compress(){
        try {
            //读取输入
            String s = StdIn.readString();
            char[] input = s.toCharArray();

            //统计频率
            int[] freq = new int[R];
            for (int i = 0; i < input.length; i++) {
                freq[input[i]]++;
            }

            //构造霍夫曼树
            Node root = buildTrie(freq);

            //(递归的)构造编译表
            String st[] = buildCode(root);

            //(递归的)打印解码用的单词查找树
            String trie = writeTrie(root, "");
            StdOut.println(trie);

            //打印字符总数占32位
            String inputLength = getCharLength(input.length);
            StdOut.println(inputLength);

            //原编码
            String original = "";//原来编码
            for (int i = 0; i < input.length; i++) {
                original += toASCIIString(input[i]);
            }
            StdOut.println("original length is :" + original.length());
            StdOut.println(original);

            //用霍夫曼树处理
            String huffman = trie + inputLength;//霍夫曼编码
            for (int i = 0; i < input.length; i++) {
                String code = st[input[i]];
                for (int j = 0; j < code.length(); j++) {
                    if (code.charAt(j) == '1') {
                        huffman += "1";
                    } else {
                        huffman += "0";
                    }
                }
            }
            int huffmanLength = huffman.length();
            for (int i = 0; i < 8 - huffmanLength % 8; i++) {
                huffman += "0";//编码位数不是8的倍数补0
            }
            StdOut.println("huffman length is :" + huffman.length());
            StdOut.println(huffman);
            return huffman;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

    public String expand(String huffman){
        try {
            tempHuffmanCode = huffman;
            Node root = readTrie();
            int length = Integer.valueOf(tempHuffmanCode.substring(0, 32), 2);
            String result = extend(root, tempHuffmanCode.substring(32, tempHuffmanCode.length()), length);
            StdOut.println("original string is:" + result);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
