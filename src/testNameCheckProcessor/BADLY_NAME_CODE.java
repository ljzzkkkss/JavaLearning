package testNameCheckProcessor;

/**
 * Created by ljzzkkkss on 2018/4/23.
 */
public class BADLY_NAME_CODE {
    enum colors{
        red,blud,green;
    }

    static final int _FORTY_TWO = 42;

    public static int NOT_A_CONSTANT = _FORTY_TWO;

    protected void BADLY_NAMED_CODE() {
        return;
    }

    public void NOTcamelCASEmethodNAME(){
        return;
    }
}
