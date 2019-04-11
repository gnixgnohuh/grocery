package jvm;

/**
 * @author: gnixgnohuh
 * @date : 18-3-12
 * @time : 下午5:17
 * @desc :
 */
public class A extends B {
    static int i = 1;
    static{
        System.out.println("Class A1:static blocks" + i);
    }

    int j = 1;
    static {
        i++;
        System.out.println("Class A2:static blocks" + i);
    }

    public A(){
        super();
        i++;
        j++;
        System.out.println("constructor A: " + "i=" + i + "j=" + j);
    }

    {
        i++;
        j++;
        System.out.println("Class A:common blocks" + "i=" + i + "j=" + j);
    }

    public void aDisplay(){
        i++;
        System.out.println("Class A:static void bDisplay():" + "i=" + i + "j=" + j);
    }

    public static void aTest(){
        i++;
        System.out.println("Class A:static void bTest():" + "i=" + i);
        return;
    }
}
