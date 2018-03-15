package jvm;

/**
 * @author: gnixgnohuh
 * @date : 18-3-12
 * @time : 下午4:25
 * @desc :
 */
public class Main {
    public static int value1 = 5;
    public static int value2 = 6;

    static {
        value2 = 66;
    }

    public static void main(String[] args) {
//        System.out.println(value1);
//        System.out.println(value2);
        A a = new A();
        a.aDisplay();
    }
}
