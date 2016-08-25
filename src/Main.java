import testfile.MakeDirectories;

/**
 * Created by LiuJun on 2016/8/25.
 */
public class Main {
    public static void main(String[] args) {
        MakeDirectories md = new MakeDirectories();
        String[] arguments = new String[]{"-d","D:\\hello"};
        md.main(arguments);
    }

}
