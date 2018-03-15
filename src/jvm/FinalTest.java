package jvm;

/**
 * @author: gnixgnohuh
 * @date : 18-3-15
 * @time : 上午9:35
 * @desc :
 */
public class FinalTest {

    /**
     * 跟着对象
     */
    private int a = 1;

    /**
     * 方法区
     */
    private static int b = 2;

    public static void main(String[] args) {
        System.out.println(FinalTest.class.getClassLoader());
    }
}
