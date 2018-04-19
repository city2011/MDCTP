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

    public void taskAssignment(int tasknum, int workernum) {
        CreateDataService cds = new CreateDataService();
        List<CTask> clist = cds.createTasks(tasknum);
        List<Worker> wlist = cds.createWorkers(workernum);


        long startProgram = System.currentTimeMillis();
        for (CTask ct : clist) {
            //更新任务的排产时间
            long start = System.currentTimeMillis() + 3000;
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
                ct.setStatus(true);

                //工人任务的实际分配
                for (int i = 0; i < indlist.size(); i++) {
                    Worker worker = wlist.get(indlist.get(i));
                    int skill_index = getIndexBySkill(worker.getSkill(),ct.sub[i]);
                    worker.updateLoad(time[2*i],time[(2*i+1)],skill_index);
                }

                ct.setComplete_time(time[indlist.size()-1]);

            }
        }
        long endProgram = System.currentTimeMillis();
        System.out.println();
        System.out.println("Start in:"+ Util.timestampToDate(startProgram));
        System.out.println("End in:"+Util.timestampToDate(endProgram));
        System.out.println("Program Time expired: "+(endProgram-startProgram)+"ms");
        System.out.println();
        getTaskReport(clist);
        System.out.println();
        getWorkerReport(wlist);
//        System.out.println("Total Number of Tasks"+clist.size());
//        System.out.println("Number of Completed Task "+ getCompletedTaskNumber(clist));
//        System.out.println("Avarage task start - create:"+ getTaskWaitTime(clist));
//        System.out.println("Avarage task complete - start:"+ getTaskWorkTime(clist));
//        System.out.println("Avarage worker load number:"+ getWorkerLoad(wlist));
    }

    private void getWorkerReport(List<Worker> wlist) {
        int sum = 0;
        long max = 0;
        long min = 5000;
        double avg = 0.0;
        double avg_num = 0.0;
        int workingnum = 0;
        long load_time = 0;
        long load_sum = 0;
        for (Worker worker:wlist)
        {
            sum+=worker.getLoad().size();
            if(worker.getLoad().size()>0)
            {
                workingnum++;
                for (int i = 0; i < worker.getLoad().size()/2; i++) {
                    load_time = worker.getLoad().get(i*2+1)-worker.getLoad().get(i*2);
                    load_sum+=load_time;
                    if(load_time>max)
                        max = load_time;
                    if(load_time<min)
                        min = load_time;
                }
            }
        }
        avg = load_sum/workingnum;
        avg_num = (double)(sum/2)/wlist.size();

        System.out.println("sum"+ sum+"size"+wlist.size());

        System.out.println("Total Number of Workers:"+ wlist.size());
        System.out.println("Working Number of Workers:"+ workingnum);
        System.out.println("Avarage Number of Tasks a Worker work on:"+ avg_num);
        System.out.println("MAX worker load MS:"+ max);
        System.out.println("Avarage worker load MS:"+ avg);
        System.out.println("MAX worker load MS:"+ max);
        System.out.println("MIN worker load MS:"+ min);

    }

    private void getTaskReport(List<CTask> clist)
    {
        long wait_sum=0;
        int complete_num=0;
        long complete_sum=0;
        for (CTask cTask: clist) {
            if (cTask.getStatus()==true) {
                wait_sum += (cTask.getStart_time() - cTask.getCreate_time());
                complete_sum += (cTask.getComplete_time()-cTask.getStart_time());
                complete_num++;
            }
        }
        double wait_ret = wait_sum/complete_num;
        double complete_ret = complete_sum/complete_num;

        System.out.println("Total Number of Tasks"+clist.size());
        System.out.println("Number of Completed Task "+ complete_num);
        System.out.println("Avarage task start - create:"+ Util.format2(wait_ret));
        System.out.println("Avarage task complete - start:"+ Util.format2(complete_ret));

    }

    private String getTaskWaitTime(List<CTask> clist) {
        long sum=0;
        for (CTask cTask: clist) {
            if (cTask.getStatus()==true)
                sum+=cTask.getStart_time()-cTask.getCreate_time();
        }
        double ret = sum/clist.size();
        return ret+"";
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
