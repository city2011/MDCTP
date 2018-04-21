package test.service;

import greedyStrategy.Service.CTP;
import org.junit.jupiter.api.Test;

/**
 * @ProjectName: MDCTP
 * @Author: City
 * @Description:
 * @Date: Created in 下午4:21 2018/4/19
 * @Modified By:city
 */
public class testCTP {
    @Test
    public void testTaskAssignment()
    {
        CTP ctp = new CTP();
        for (int i = 100; i < 1000; i+=100) {
            for (int j = 0; j < 10; j++) {
                ctp.taskAssignment(i,500);
            }
        }
    }
}
