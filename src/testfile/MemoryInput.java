package testfile;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by 刘骏 on 2016/8/31.
 */
public class MemoryInput {
    public static void main(String[] args)throws IOException {
        StringReader in = new StringReader(BufferedInputFile.read("D:\\InteliJ Workspace\\JavaLearning\\src\\testfile\\MemoryInput.java"));
        int c;
        while((c = in.read()) != -1)
            System.out.print((char)c);
    }
}
