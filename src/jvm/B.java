package jvm;

/**
 * @author: gnixgnohuh
 * @date : 18-3-12
 * @time : 下午5:12
 * @desc :
 */
public class B {
    static int i = 1;
    static{
        System.out.println("Class B1:static blocks" + i);
    }

    int j = 1;
    static {
        i++;
        System.out.println("Class B2:static blocks" + i);
    }

    public B(){
        i++;
        j++;
        System.out.println("constructor B: " + "i=" + i + "j=" + j);
    }

    {
        i++;
        j++;
        System.out.println("Class B:common blocks" + "i=" + i + "j=" + j);
    }

    public void bDisplay(){
        i++;
        System.out.println("Class B:static void bDisplay():" + "i=" + i + "j=" + j);
    }

    public static void bTest(){
        i++;
        System.out.println("Class B:static void bTest():" + "i=" + i);
        return;
    }
}
