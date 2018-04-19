package test.service;

import greedyStrategy.bean.Worker;
import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 * @ProjectName: MDCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午12:03 2018/4/19
 * @Modified By:city
 */
public class testWorker {
    @Test
    public void testCheckLoad()
    {
        int DABAN[] = {1,5,8};
        int eff1[] = {2000,999,2000};
        Random rd = new Random();
        int reputation = (int) (Math.sqrt(5) * rd.nextGaussian() + 85);
        Worker worker = new Worker(1,reputation,DABAN,eff1,1);

//        long start = System.currentTimeMillis();
        long[] time = new long[4];
        long start = 0;
        time[0] = start;
        time[1] = start + 2000;

        time[2] = start + 4300;
        time[3] = start + 7000;

        worker.addTask(time[0],time[1]);
        worker.addTask(time[2],time[3]);

        System.out.println((worker.checkLoad(2001,5000,0)+""));
        System.out.println((worker.checkLoad(2001,4000,0)+""));
        System.out.println((worker.checkLoad(1500,3000,1)+""));
        System.out.println((worker.checkLoad(1000,1900,0)+""));


    }
}
