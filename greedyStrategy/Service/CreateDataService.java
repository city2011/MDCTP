package greedyStrategy.Service;

import greedyStrategy.bean.CTask;
import greedyStrategy.bean.GlobleBean;
import greedyStrategy.bean.Worker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CreateDataService {

    public List<Worker> createWorkers(int workernum) {
        Random rd = new Random();
        int[][] skill_type = {GlobleBean.DABAN, GlobleBean.CAIBU, GlobleBean.FULIAO, GlobleBean.FENGZHI, GlobleBean.YURONG};
        int[][] skill_eff = {GlobleBean.eff1, GlobleBean.eff1, GlobleBean.eff2, GlobleBean.eff1, GlobleBean.eff3};
        int[] workerTypeNum = new int[5];
        List<Worker> workerList = new ArrayList();
        for (int i = 0; i < workernum; i++) {
            int id = i;
            int skill_index = rd.nextInt(5);
            workerTypeNum[skill_index]++;
            int[] skill = skill_type[skill_index];
            int[] eff = new int[skill_eff[skill_index].length];
            for (int j = 0; j < eff.length; j++) {
                eff[j] = (int) (Math.sqrt(400) * rd.nextGaussian() + skill_eff[skill_index][j] - 400);
            }
            int reputation = (int) (Math.sqrt(5) * rd.nextGaussian() + 85);
            Worker w = new Worker(i, reputation, skill, eff,skill_index);
            workerList.add(w);
        }
        for (int i = 0; i < workerTypeNum.length; i++) {
            System.out.println(i+"类型工人数量"+workerTypeNum[i]);
        }
        return workerList;
    }

    public List<CTask> createTasks(int tasknum
    ) {
        List<CTask> tasklist = new ArrayList();
        Random rd = new Random();
        int id = 0;
        while (id < tasknum) {
            int reputation = (int) (Math.sqrt(10) * rd.nextGaussian() + 80);
            CTask ct = new CTask(id,rd.nextInt(3),reputation);
            id++;
            tasklist.add(ct);
        }
        return tasklist;
    }

    public List<Worker> createTypeWorkers(int workernum,int type) {
        Random rd = new Random();
        int[][] skill_type = {GlobleBean.DABAN, GlobleBean.CAIBU, GlobleBean.FULIAO, GlobleBean.FENGZHI, GlobleBean.YURONG};
        int[][] skill_eff = {GlobleBean.eff1, GlobleBean.eff1, GlobleBean.eff2, GlobleBean.eff1, GlobleBean.eff3};
        List<Worker> workerList = new ArrayList();
        for (int i = 0; i < workernum; i++) {
            int id = i;
            int skill_index = type;
            int[] skill = skill_type[skill_index];
            int[] eff = new int[skill_eff[skill_index].length];
            for (int j = 0; j < skill_eff[skill_index].length; j++) {
                eff[j] = (int) (Math.sqrt(400) * rd.nextGaussian() + skill_eff[skill_index][j] - 400);
            }
            int reputation = (int) (Math.sqrt(10) * rd.nextGaussian() + 85);
            Worker w = new Worker(i,reputation, skill, eff,type);
            workerList.add(w);
        }
        return workerList;
    }

    public List<Worker>[] createTypeWorkerSet(int base, int workernum)
    {
        Random rd = new Random();
        int w1num = base+rd.nextInt(workernum);
        int w2num = base+rd.nextInt(workernum);
        int w3num = base+rd.nextInt(workernum);
        int w4num = base+rd.nextInt(workernum);
        int w5num = base+rd.nextInt(workernum);
        String wtypenum = " "+w1num+" "+" "+w2num+" "+w3num+" "+w4num+" "+w5num;

        List<Worker> wlist0 = createTypeWorkers(w1num, 0);
        List<Worker> wlist1 = createTypeWorkers(w2num, 1);
        List<Worker> wlist2 = createTypeWorkers(w3num, 2);
        List<Worker> wlist3 = createTypeWorkers(w4num, 3);
        List<Worker> wlist4 = createTypeWorkers(w5num, 4);
        List<Worker>[] workerSet = new List[5];
        workerSet[0] = wlist0;
        workerSet[1] = wlist1;
        workerSet[2] = wlist2;
        workerSet[3] = wlist3;
        workerSet[4] = wlist4;

        return workerSet;
    }

    public List<CTask> createCompareTasks(int tasknum
    ) {
        List<CTask> tasklist = new ArrayList();
        Random rd = new Random();
        int id = 0;
        while (id < tasknum) {
            int reputation = (int) (Math.sqrt(10) * rd.nextGaussian() + 80);
            CTask ct = new CTask(id,rd.nextInt(3),reputation);
            id++;
            tasklist.add(ct);
        }
        return tasklist;
    }
}
