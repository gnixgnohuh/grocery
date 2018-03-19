package SyncUtil;

import java.util.concurrent.TimeUnit;

/**
 * @author: gnixgnohuh
 * @date : 18-3-19
 * @time : 下午2:56
 * @desc :
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        MockCountDownLatch latch = new MockCountDownLatch(100);

        for(int i = 0;i < 100;i++){
            new Thread(()->{
                System.out.println("");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }).start();
        }



        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("end");

//        try {
//            latch.await();
//            System.out.println("end");
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
