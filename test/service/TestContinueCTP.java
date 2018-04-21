package test.service;

import greedyStrategy.Service.ContinueCTP;
import greedyStrategy.Service.CreateDataService;
import greedyStrategy.bean.Assignment;
import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 下午5:09 2018/4/20
 * @Modified By:city
 */
public class TestContinueCTP {
    @Test
    public void testTaskAssignment() {
        ContinueCTP ctp = new ContinueCTP();
//            for (int i = 100; i < 1000; i+=100) {
//                for (int j = 0; j < 10; j++) {
        ctp.taskAssignment(50, 3000);
//                }
//            }
    }

    @Test
    public void testContinueAssignment() {
        ContinueCTP ctp = new ContinueCTP();
        ctp.continueAssignment(100,5,3);
    }

    @Test
    public  void testOneAssignment()
    {
        ContinueCTP ctp = new ContinueCTP();
        CreateDataService cds = new CreateDataService();
        int workernum = 100;
        int sumWorkerNum = workernum * 5;
        List<Worker> wlist0 = cds.createTypeWorkers(workernum, 0);
        List<Worker> wlist1 = cds.createTypeWorkers(workernum, 1);
        List<Worker> wlist2 = cds.createTypeWorkers(workernum, 2);
        List<Worker> wlist3 = cds.createTypeWorkers(workernum, 3);
        List<Worker> wlist4 = cds.createTypeWorkers(workernum, 4);
        List<Worker>[] workerSet = new List[5];
        workerSet[0] = wlist0;
        workerSet[1] = wlist1;
        workerSet[2] = wlist2;
        workerSet[3] = wlist3;
        workerSet[4] = wlist4;

        CopyOnWriteArrayList<CTask> alist = new CopyOnWriteArrayList<>();
        List<CTask> sumClist = new ArrayList<>();

        List<Assignment> assignmentSet = new ArrayList<>();

        List<CTask> clist = cds.createTasks(300);
        for (CTask cTask:clist)
        {
            alist.add(cTask);
            sumClist.add(cTask);
        }
        long start = System.currentTimeMillis();
        ctp.oneAssignment(workerSet,alist,assignmentSet);
        long end = System.currentTimeMillis();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ctp.oneAssignment(workerSet,alist,assignmentSet);

        ctp.getReport(workerSet,clist,end-start,workernum,assignmentSet,alist);
    }
}
