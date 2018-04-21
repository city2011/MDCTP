package greedyStrategy.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午1:56 2018/4/21
 * @Modified By:city
 */
public class Assignment {
    CTask cTask;
    List<Worker> wlist = new ArrayList<>();
    public Assignment(CTask cTask1,List<Worker> wlist1)
    {
        this.cTask=cTask1;
        this.wlist=wlist1;
    }

    @Override
    public String toString() {
        String ret ="";
        ret+="CTask ID:"+cTask.getId()
                +"  WorkerId:";
        for (Worker worker:wlist)
            ret+=worker.getId()+" ";
        return ret;
    }
}
