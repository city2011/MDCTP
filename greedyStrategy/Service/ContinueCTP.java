package greedyStrategy.Service;

import greedyStrategy.bean.Assignment;
import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;
import greedyStrategy.dao.CTPDao;
import greedyStrategy.util.Util;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 下午3:39 2018/4/20
 * @Modified By:city
 */
public class ContinueCTP {

    public void continueAssignment(int workernum, int roundTasknum, int roundNum) {
        CreateDataService cds = new CreateDataService();
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

        long startProgram = System.currentTimeMillis();
        for (int roundtime = 0; roundtime < roundNum; roundtime++) {
            List<CTask> clist = cds.createTasks(roundTasknum);
            for (CTask cTask : clist) {
                alist.add(cTask);
                sumClist.add(cTask);
            }
            long rounds = System.currentTimeMillis();
            System.out.println("round " + roundtime + " Start in:" + Util.timestampToDate(System.currentTimeMillis()));
            oneAssignment(workerSet, alist, assignmentSet);
            System.out.println();
            System.out.println("round " + roundtime + "End in:" + Util.timestampToDate(System.currentTimeMillis())
            +"    expired "+(System.currentTimeMillis()-rounds));
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        oneAssignment(workerSet, alist, assignmentSet);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        oneAssignment(workerSet, alist, assignmentSet);

        long endProgram = System.currentTimeMillis();
        long expiredTime = (endProgram - startProgram);

        System.out.println();
        System.out.println("Start in:" + Util.timestampToDate(startProgram));
        System.out.println("End in:" + Util.timestampToDate(endProgram));
        System.out.println("Program Time expired: " + expiredTime + "ms");
        System.out.println();

        getReport(workerSet, sumClist, endProgram, sumWorkerNum,assignmentSet,alist);
    }

    // 参数  工人集合  待分配任务集合  分配集合
    public void oneAssignment(List<Worker>[] workerSet, List<CTask> alist, List<Assignment> assignmentSet) {
        for (CTask ct : alist) {
            //更新任务的排产时间
            long start = System.currentTimeMillis() + 3000;
            ct.updateTime(start);
            //这个任务的时间数组不会影响原任务
            long[] time = ct.getTime();

            //每一个子任务的分配
            boolean sub_result = true;
//            List<Long> mlist = new ArrayList();
            List<Integer> indlist = new ArrayList();
            List<Integer> wtypelist = new ArrayList();
            w:
            for (int i = 0; i < ct.sub.length; i++) {
                int wtype = getTypeByTask(ct.sub[i]);
                long max = 0;
                int ind = -1;
                for (Worker worker : workerSet[wtype]) {
                    //check type
                    // 工人预分类对应类型

                    //check reputation
                    if (worker.getReputation() < ct.getReputation())
                        continue;
                    //check Load
                    int skill_index = getIndexBySkill(worker.getSkill(), ct.sub[i]);
                    if (skill_index == -1)
                        continue;
                    if (worker.checkLoad(time[2 * i], time[(2 * i + 1)], skill_index)) {
                        long delta = worker.deltaScore(time[2 * i], time[(2 * i + 1)], skill_index);
                        if (delta > max) {
                            max = delta;
                            ind = workerSet[wtype].indexOf(worker);
                        }
                    } else
                        continue;
                }
                if (max == 0) {
                    sub_result = false;
                    break w;
                } else {
//                  mlist.add(max);
                    indlist.add(ind);
                    wtypelist.add(wtype);
                    int skill_index = getIndexBySkill(workerSet[wtype].get(ind).getSkill(), ct.sub[i]);

                    switch (ct.getType())
                    {
                        case 0:
                            if(i==0) {
                                time[2] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                time[4] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            }
                            else{
                               if (i==1)
                                   time[3] = time[2] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                               if (i==2) {
                                   time[5] = time[4] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                   time[6] = (time[3]<time[4]?time[3]:time[4]);
                               }
                               if(i==3)
                                   time[7]= time[6]+workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            }
                            break;
                        case 1:
                            if(i==0)
                               time[2] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            if(i==1)
                                time[4] = time[2] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            if(i==2)
                                time[5] = time[4] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            break;
                        case 2:
                            if(i==0) {
                                time[2] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                time[4] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                time[6] = time[0] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            }
                            else{
                                if (i==1)
                                    time[3] = time[2] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                if (i==2)
                                    time[5] = time[4] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                if(i==3) {
                                    time[7] = time[6] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                                    time[8] = time[7];
                                }
                                else
                                    time[9] = time[8] + workerSet[wtype].get(ind).getSkill_efficiency()[skill_index];
                            }
                            break;
                    }

                }
            }
            if (sub_result == true) {
                ct.setStatus(true);
                //工人任务的实际分配
                List<Worker> asnWlist = new ArrayList<>();
                for (int i = 0; i < indlist.size(); i++) {
                    Worker worker = workerSet[wtypelist.get(i)].get(indlist.get(i));
                    asnWlist.add(worker);
                    int skill_index = getIndexBySkill(worker.getSkill(), ct.sub[i]);
                    worker.updateLoad(time[2 * i], time[(2 * i + 1)], skill_index);
                }
                //分配集合增加asn
                Assignment asn = new Assignment(ct, asnWlist);
                assignmentSet.add(asn);
                ct.setComplete_time(time[time.length - 1]);

                //待分配集合去掉分配成功的任务
                alist.remove(ct);
//                Iterator<CTask> iterator = alist.iterator();
//                while(iterator.hasNext()){
//                    CTask cTask = iterator.next();
//                    if(cTask.equals(ct))
//                        iterator.remove();
//                }
            }
        }
        System.out.println();
        System.out.println("ASS SIZE: "+assignmentSet.size());
    }

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
            boolean sub_result = true;
//            List<Long> mlist = new ArrayList();
            List<Integer> indlist = new ArrayList();
            w:
            for (int i = 0; i < ct.sub.length; i++) {
                int wtype = getTypeByTask(ct.sub[i]);
                long max = 0;
                int ind = -1;
                for (Worker worker : wlist) {
                    //check type
                    if (wtype != worker.getType())
                        continue;
                    //check reputation
                    if (worker.getReputation() < ct.getReputation())
                        continue;
                    //check Load
                    int skill_index = getIndexBySkill(worker.getSkill(), ct.sub[i]);
                    if (skill_index == -1)
                        continue;
                    if (worker.checkLoad(time[2 * i], time[(2 * i + 1)], skill_index)) {
                        long delta = worker.deltaScore(time[2 * i], time[(2 * i + 1)], skill_index);
                        if (delta > max) {
                            max = delta;
                            ind = wlist.indexOf(worker);
                        }
                    } else
                        continue;

                }

                if (max == 0 && ind == -1) {
                    sub_result = false;
                    break w;
                } else {
//                  mlist.add(max);
                    indlist.add(ind);
                    if (i == ct.sub.length - 1)
                        time[2 * i + 1] = time[i * 2] + wlist.get(ind).getSkill_efficiency()[i];
                    else
                        time[(i + 1) * 2] = time[i * 2] + wlist.get(ind).getSkill_efficiency()[i];
                }
            }
            if (sub_result == true) {
                ct.setStatus(true);

                //工人任务的实际分配
                for (int i = 0; i < indlist.size(); i++) {
                    Worker worker = wlist.get(indlist.get(i));
                    int skill_index = getIndexBySkill(worker.getSkill(), ct.sub[i]);
                    worker.updateLoad(time[2 * i], time[(2 * i + 1)], skill_index);
                }
                System.out.print(" " + time.toString() + " ");
                ct.setComplete_time(time[time.length - 1]);
            }
        }
        long endProgram = System.currentTimeMillis();
        long expiredTime = (endProgram - startProgram);

        System.out.println();
        System.out.println("Start in:" + Util.timestampToDate(startProgram));
        System.out.println("End in:" + Util.timestampToDate(endProgram));
        System.out.println("Program Time expired: " + expiredTime + "ms");
        System.out.println();

//        getReport(wlist,clist,expiredTime);

//        getTaskReport(clist);
//        System.out.println();
//        getWorkerReport(wlist);

//        System.out.println("Total Number of Tasks"+clist.size());
//        System.out.println("Number of Completed Task "+ getCompletedTaskNumber(clist));
//        System.out.println("Avarage task start - create:"+ getTaskWaitTime(clist));
//        System.out.println("Avarage task complete - start:"+ getTaskWorkTime(clist));
//        System.out.println("Avarage worker load number:"+ getWorkerLoad(wlist));
    }

    public void getReport(List<Worker>[] wSet, List<CTask> clist, long time, int workernum, List<Assignment>assignments, List<CTask> alist) {
        int sum = 0;
        long max = 0;
        long min = 5000;
        double avg = 0.0;
        double avg_num = 0.0;
        int workingnum = 0;
        long load_time = 0;
        long load_sum = 0;
        for (List<Worker> wlist : wSet) {
            for (Worker worker : wlist) {
                sum += worker.getLoad().size();
                if (worker.getLoad().size() > 0) {
                    workingnum++;
                    for (int i = 0; i < worker.getLoad().size() / 2; i++) {
                        load_time = worker.getLoad().get(i * 2 + 1) - worker.getLoad().get(i * 2);
                        load_sum += load_time;
                        if (load_time > max)
                            max = load_time;
                        if (load_time < min)
                            min = load_time;
                    }
                }
            }
        }
        avg = load_sum / workingnum;
        avg_num = (double) (sum / 2) / workernum;

        long wait_sum = 0;
        int complete_num = 0;
        long complete_sum = 0;
        for (CTask cTask : clist) {
            if (cTask.getStatus() == true) {
                wait_sum += (cTask.getStart_time() - cTask.getCreate_time());
                complete_sum += (cTask.getComplete_time() - cTask.getStart_time());
                complete_num++;
            }
        }
        double wait_ret = wait_sum / complete_num;
        double complete_ret = complete_sum / complete_num;
        int workerNumber = workernum;
        int taskNumber = clist.size();

        System.out.println("Program Time expired: " + time + "ms");
        System.out.println();
        System.out.println("Total Number of Workers:" + workerNumber);
        System.out.println("Working Number of Workers:" + workingnum);
        System.out.println("Avarage Number of Tasks a Worker work on:" + avg_num);
        System.out.println("MAX worker load MS:" + max);
        System.out.println("Avarage worker load MS:" + avg);
        System.out.println("MAX worker load MS:" + max);
        System.out.println("MIN worker load MS:" + min);
        System.out.println();
        System.out.println("Total Number of Tasks" + taskNumber);
        System.out.println("Number of Completed Task " + complete_num);
        System.out.println("Avarage task start - create:" + Util.format2(wait_ret));
        System.out.println("Avarage task complete - start:" + Util.format2(complete_ret));

for (Assignment ass:assignments)
    System.out.print(ass.toString()+" ");

for(CTask cTask: alist)
    System.out.println(cTask.toString());

//        CTPDao.insertReport(workerNumber, taskNumber, avg_num, workingnum, avg, time);
    }

    private void getWorkerReport(List<Worker>[] workerSet, int sumWorkerNum) {
        int sum = 0;
        long max = 0;
        long min = 5000;
        double avg = 0.0;
        double avg_num = 0.0;
        int workingnum = 0;
        long load_time = 0;
        long load_sum = 0;
        for (List<Worker> wlist : workerSet) {
            for (Worker worker : wlist) {
                sum += worker.getLoad().size();
                if (worker.getLoad().size() > 0) {
                    workingnum++;
                    for (int i = 0; i < worker.getLoad().size() / 2; i++) {
                        load_time = worker.getLoad().get(i * 2 + 1) - worker.getLoad().get(i * 2);
                        load_sum += load_time;
                        if (load_time > max)
                            max = load_time;
                        if (load_time < min)
                            min = load_time;
                    }
                }
            }
        }

        avg = load_sum / workingnum;
        avg_num = (double) (sum / 2) / sumWorkerNum;

        System.out.println("sum" + sum + "size" + sumWorkerNum);
        System.out.println("Total Number of Workers:" + sumWorkerNum);
        System.out.println("Working Number of Workers:" + workingnum);
        System.out.println("Avarage Number of Tasks a Worker work on:" + avg_num);
        System.out.println("MAX worker load MS:" + max);
        System.out.println("Avarage worker load MS:" + avg);
        System.out.println("MAX worker load MS:" + max);
        System.out.println("MIN worker load MS:" + min);

    }

    private void getTaskReport(List<CTask> clist) {
        long wait_sum = 0;
        int complete_num = 0;
        long complete_sum = 0;
        for (CTask cTask : clist) {
            if (cTask.getStatus() == true) {
                wait_sum += (cTask.getStart_time() - cTask.getCreate_time());
                complete_sum += (cTask.getComplete_time() - cTask.getStart_time());
                complete_num++;
            }
        }
        double wait_ret = wait_sum / complete_num;
        double complete_ret = complete_sum / complete_num;

        System.out.println("Total Number of Tasks" + clist.size());
        System.out.println("Number of Completed Task " + complete_num);
        System.out.println("Avarage task start - create:" + Util.format2(wait_ret));
        System.out.println("Avarage task complete - start:" + Util.format2(complete_ret));

    }

    private String getTaskWaitTime(List<CTask> clist) {
        long sum = 0;
        for (CTask cTask : clist) {
            if (cTask.getStatus() == true)
                sum += cTask.getStart_time() - cTask.getCreate_time();
        }
        double ret = sum / clist.size();
        return ret + "";
    }

    private int getIndexBySkill(int[] skill, int i) {
        for (int j = 0; j < skill.length; j++) {
            if (skill[j] == i)
                return j;
        }
        //寻找失败
        System.out.println();
        System.out.println("skill" + i);
        for (int s : skill)
            System.out.print(" " + s);
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
