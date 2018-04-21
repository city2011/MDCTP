package greedyStrategy.bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Worker {
    int id;
    int type;
    int skill[];
    int skill_efficiency[];
    int reputation;
    ArrayList<Long> load = new ArrayList<Long>();

    public Worker(int id, int reputation, int skill[], int efficiency[],int type) {
        this.id = id;
        this.reputation = reputation;
        this.skill = skill;
        this.skill_efficiency = efficiency;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getStatus() {
        return skill;
    }

    public void setStatus(int[] status) {
        this.skill = status;
    }

    public int[] getSkill_efficiency() {
        return skill_efficiency;
    }

    public void setSkill_efficiency(int[] skill_efficiency) {
        this.skill_efficiency = skill_efficiency;
    }

    public String toString()
    {
        String ret = "Id:"+id+" type:"+type+" reputaion:"+reputation+
                "  Skill:";
        for (int x:skill) {
            ret+=""+x+" ";
        }
        ret+=" Eff:";
        for (int y:skill_efficiency
             ) {ret+=""+y+" ";
        }
        return ret;
    }

    public void addTask(long start, long end )
    {
        this.load.add(start);
        this.load.add(end);
    }

    public long deltaScore(long start, long end, int index)
    {
        long delta = end-start-skill_efficiency[index];
        if(delta<0)
            return 0;
        if(checkLoad(start,end,index))
            return delta;
        return 0;
    }

    public boolean checkLoad(long start, long end, int index)
    {
        long check = end-start-skill_efficiency[index];
        if(check<0)
            return false;

        ArrayList<Long> checkload=new ArrayList<>();
        for(long element:load)
            checkload.add(element);

        checkload.add(start);
        checkload.add(end);

        checkload.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                // TODO Auto-generated method stub
                if ((long) o1 > (long) o2)
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //      else return 0; //无效
                else return -1;
            }
        });
//        int size = checkload.size();
//        for (int i = 0;i<size;i++)
//        {
//            System.out.print(checkload.get(i)+"  ");
//        }

        int nstart = checkload.indexOf(start);
        int newend = checkload.indexOf(end);
//        System.out.print(" index: "+nstart+" "+newend+" ");

        if(newend - nstart >4)
            return false;

        if(nstart%2==1)
        {
            if(newend - nstart==1)
                return false;
            if(newend-nstart==2)
                return(checkload.get(newend)-checkload.get(newend-1)>skill_efficiency[index]);
            if(newend -nstart==3)
                return (checkload.get(newend-1)-checkload.get(nstart+1)>skill_efficiency[index]);
        }
        else
        {
            if(newend - nstart==1)
                return true;
            //如果efficiency比任务需求大，delta那里过不去。
            if(newend-nstart==2)
                return(checkload.get(newend-1)-checkload.get(nstart)>skill_efficiency[index]);
        }
        return false;
    }

    public void updateLoad(long start, long end, int index) {
        ArrayList<Long> checkload=new ArrayList<>();
        for(long element:load)
            checkload.add(element);

        checkload.add(start);
        checkload.add(end);

        checkload.sort(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                // TODO Auto-generated method stub
                if ((long) o1 > (long) o2)
                    return 1;
                    //注意！！返回值必须是一对相反数，否则无效。jdk1.7以后就是这样。
                    //      else return 0; //无效
                else return -1;
            }
        });
        int size = checkload.size();
        for (int i = 0;i<size;i++)
        {
            System.out.print(checkload.get(i)+"  ");
        }

        int nstart = checkload.indexOf(start);
        int newend = checkload.indexOf(end);
//        System.out.print(" index: "+nstart+" "+newend+" ");

        if(nstart%2==1)
        {
//            if(newend-nstart==2)
//                addTask(checkload.get(newend-1),checkload.get(newend-1)+skill_efficiency[index]);
//            if(newend -nstart==3)
                addTask(checkload.get(nstart+1),checkload.get(nstart+1)+skill_efficiency[index]);
        }
        else
        {
//            if(newend - nstart==1)
                addTask(checkload.get(nstart),checkload.get(nstart)+skill_efficiency[index]);
//            //如果efficiency比任务需求大，delta那里过不去。
//            if(newend-nstart==2)
//                addTask(checkload.get(nstart),checkload.get(nstart)+skill_efficiency[index]);
        }
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int[] getSkill() {
        return skill;
    }

    public void setSkill(int[] skill) {
        this.skill = skill;
    }

    public ArrayList<Long> getLoad() {
        return load;
    }

    public void setLoad(ArrayList<Long> load) {
        this.load = load;
    }

    public int getReputation() {
        return reputation;
    }

    public void setReputation(int reputation) {
        this.reputation = reputation;
    }
}
