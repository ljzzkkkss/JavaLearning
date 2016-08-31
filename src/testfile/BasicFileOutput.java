package testfile;

import java.io.*;

/**
 * Created by 刘骏 on 2016/8/31.
 */
public class BasicFileOutput {
    static String file = "BasicFileOutput.out";
    public static void main(String[] args) throws IOException{
        BufferedReader in = new BufferedReader(new StringReader((BufferedInputFile.read("D:\\InteliJ Workspace\\JavaLearning\\src\\testfile\\BasicFileOutput.java"))));
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        int lineCount = 1;
        String s;
        while((s = in.readLine()) != null){
            out.println(lineCount++ + ":" + s);
        }
        out.close();
        System.out.println(BufferedInputFile.read(file));

    }
}
