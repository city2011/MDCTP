package test.service;

import greedyStrategy.Service.ContinueCTP;
import greedyStrategy.Service.CreateDataService;
import greedyStrategy.bean.Assignment;
import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;
import greedyStrategy.util.Util;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
        ctp.continueAssignment(100, 5, 3);
    }

    @Test
    public void testOneAssignment() {
        ContinueCTP ctp = new ContinueCTP();
        CreateDataService cds = new CreateDataService();
        int workernumbase = 200;
        int workernum = 100;
        int sumWorkerNum = 0;
        int roundNum = 5;
        int roundTasknum = 300;
        List<Worker>[] workerSet = cds.createTypeWorkerSet(workernumbase, workernum);
        for (List<Worker> wlist : workerSet)
            sumWorkerNum += wlist.size();

        CopyOnWriteArrayList<CTask> alist = new CopyOnWriteArrayList<>();
        List<CTask> sumClist = new ArrayList<>();
        List<Assignment> assignmentSet = new ArrayList<>();

        long startProgram = System.currentTimeMillis();
        for (int roundtime = 0; roundtime < roundNum; roundtime++) {
            List<CTask> clist = cds.createTasks(roundTasknum);
            for (CTask cTask : clist) {
                alist.add(cTask);
                sumClist.add(cTask);
            }
            long rounds = System.currentTimeMillis();
            System.out.println("round " + roundtime + " Start in:" + Util.timestampToDate(System.currentTimeMillis()));
            ctp.oneAssignment(workerSet, alist, assignmentSet);
            System.out.println("round " + roundtime + "End in:" + Util.timestampToDate(System.currentTimeMillis())
                    + "    expired " + (System.currentTimeMillis() - rounds));
            try {
                Thread.sleep(1200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long startRest = System.currentTimeMillis();
        System.out.println("Rest TaskNum " + alist.size());
        System.out.println("Rest Start in " + Util.timestampToDate(System.currentTimeMillis()));
        while (!alist.isEmpty()) {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ctp.oneAssignment(workerSet, alist, assignmentSet);
            System.out.println(alist.size());
        }
        System.out.println("Rest End in " + Util.timestampToDate(System.currentTimeMillis()));

        System.out.println(System.currentTimeMillis()-startRest+"ms");

//        ctp.getReport(workerSet, clist, end - start, workernum, assignmentSet, alist, "both" + workernum);
    }

    @Test
    public void testOneRoundAssignment() {
        ContinueCTP ctp = new ContinueCTP();
        CreateDataService cds = new CreateDataService();
        int workernum = 1000;
        int sumWorkerNum = 0;

        CopyOnWriteArrayList<CTask> alist = new CopyOnWriteArrayList<>();
        List<CTask> sumClist = new ArrayList<>();

        List<Assignment> assignmentSet = new ArrayList<>();

        Random rd = new Random();
        for (int i = 400; i <= 1000; i += 200) {
            for (int j = 0; j < 5; j++) {
                int w1num = 100 + rd.nextInt(workernum);
                int w2num = 100 + rd.nextInt(workernum);
                int w3num = 100 + rd.nextInt(workernum);
                int w4num = 100 + rd.nextInt(workernum);
                int w5num = 100 + rd.nextInt(workernum);
                String wtypenum = " " + w1num + " " + " " + w2num + " " + w3num + " " + w4num + " " + w5num;
                sumWorkerNum = w1num + w2num + w3num + w4num + w5num;

                List<Worker> wlist0 = cds.createTypeWorkers(w1num, 0);
                List<Worker> wlist1 = cds.createTypeWorkers(w2num, 1);
                List<Worker> wlist2 = cds.createTypeWorkers(w3num, 2);
                List<Worker> wlist3 = cds.createTypeWorkers(w4num, 3);
                List<Worker> wlist4 = cds.createTypeWorkers(w5num, 4);
                List<Worker>[] workerSet = new List[5];
                workerSet[0] = wlist0;
                workerSet[1] = wlist1;
                workerSet[2] = wlist2;
                workerSet[3] = wlist3;
                workerSet[4] = wlist4;

                List<CTask> clist = cds.createTasks(i);
                alist = new CopyOnWriteArrayList<>();
                for (CTask cTask : clist) {
                    alist.add(cTask);
                    sumClist.add(cTask);
                }
                long start = System.currentTimeMillis();
                ctp.oneAssignment(workerSet, alist, assignmentSet);
                long end = System.currentTimeMillis();

                ctp.getReport(workerSet, clist, end - start, sumWorkerNum, assignmentSet, alist, wtypenum);
            }
        }

    }


}
