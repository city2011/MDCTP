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
        int[][] skill_type = {GlobleBean.DABAN, GlobleBean.CAIBU, GlobleBean.FULIAO, GlobleBean.CAIBU, GlobleBean.YURONG};
        int[][] skill_eff = {GlobleBean.eff1, GlobleBean.eff1, GlobleBean.eff2, GlobleBean.eff1, GlobleBean.eff3};
        List<Worker> workerList = new ArrayList();
        for (int i = 0; i < workernum; i++) {
            int id = i;
            int skill_index = rd.nextInt(5);
            int[] skill = skill_type[skill_index];
            int[] eff = new int[skill_eff[skill_index].length];
            for (int j = 0; j < skill_eff[skill_index].length; j++) {
                eff[j] = (int) (Math.sqrt(200) * rd.nextGaussian() + skill_eff[skill_index][j] - 400);
            }
            Worker w = new Worker(i, skill, eff);
            workerList.add(w);
        }
        return workerList;
    }

    public List<CTask> createTasks(int tasknum
    ) {
        List<CTask> tasklist = new ArrayList();
        Random rd = new Random();
        int id = 0;
        while (id < tasknum) {
            CTask ct = new CTask(id, rd.nextInt(3));
            tasknum--;
            tasklist.add(ct);
        }
        return tasklist;
    }


}
