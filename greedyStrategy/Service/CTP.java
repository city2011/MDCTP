package greedyStrategy.Service;

import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;
import greedyStrategy.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: MDCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午1:28 2018/4/19
 * @Modified By:city
 */
public class CTP {

    public void taskAssignment() {
        CreateDataService cds = new CreateDataService();
        List<CTask> clist = cds.createTasks(100);
        List<Worker> wlist = cds.createWorkers(200);


        long startProgram = System.currentTimeMillis();
        for (CTask ct : clist) {
            //更新任务的排产时间
            long start = System.currentTimeMillis() + 300;
            ct.updateTime(start);
            long[] time = ct.getTime();

            //每一个子任务的分配
            boolean sub_result=true;
//            List<Long> mlist = new ArrayList();
            List<Integer> indlist = new ArrayList();
            w:for (int i = 0; i < ct.sub.length; i++) {
                int wtype = getTypeByTask(ct.sub[i]);
                long max = 0;
                int ind = -1;
                for (Worker worker : wlist) {
                    //check type
                    if(wtype!=worker.getType())
                        continue;
                    //check reputation
                    if (worker.getReputation()<ct.getReputation())
                        continue;
                    //check Load
                    int skill_index = getIndexBySkill(worker.getSkill(),ct.sub[i]);
                    if(skill_index==-1)
                        continue;
                    if(worker.checkLoad(time[2*i],time[(2*i+1)],skill_index))
                    {
                        long delta = worker.deltaScore(time[2*i],time[(2*i+1)],skill_index);
                        if(delta>max) {
                            max = delta;
                            ind = wlist.indexOf(worker);
                        }
                    }
                    else
                        continue;

                }
                if (max==0&&ind==-1)
                {
                    sub_result=false;
                    break w;
                }
                else
                {
//                    mlist.add(max);
                    indlist.add(ind);
                }
            }
            if (sub_result==true)
            {
                //工人任务的实际分配
                for (int i = 0; i < indlist.size(); i++) {
                    Worker worker = wlist.get(indlist.get(i));
                    int skill_index = getIndexBySkill(worker.getSkill(),ct.sub[i]);
                    worker.updateLoad(time[2*i],time[(2*i+1)],skill_index);
                }

            }
        }
        long endProgram = System.currentTimeMillis();
        System.out.println();
        System.out.println("Start in:"+ Util.timestampToDate(startProgram));
        System.out.println("End in:"+Util.timestampToDate(endProgram));
        System.out.println("Program Time expired: "+(endProgram-startProgram)+"ms");
    }

    private int getIndexBySkill(int[] skill, int i) {
        for (int j = 0; j < skill.length; j++) {
            if (skill[j] == i)
                return j;
        }
        System.out.println();
        System.out.println("skill"+i);
        for (int s:skill)
            System.out.print(" "+s);
        System.out.println();
        return -1;
    }

    private int getTypeByTask(int i) {
        if (i == 1 || i == 5 || i == 8)
            return 0;
        if (i == 3 || i == 6 || i == 10)
            return 1;
        if (i == 2 || i == 9)
            return 2;
        if (i == 4 || i == 7 || i == 12)
            return 3;
        if (i == 11)
            return 4;
        return 3;
    }



}
