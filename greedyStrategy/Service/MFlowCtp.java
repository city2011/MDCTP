package greedyStrategy.Service;

import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午2:01 2018/4/24
 * @Modified By:city
 */
public class MFlowCtp {
    /*
    First Step
    1 构造合适的实验数据 工人和任务 简化实验过程
      以所有任务的第一个任务为优化目标，假定剩余子任务都能成功分配，工人数足够。
      以信誉为限制条件。 也可以以效率是否满足任务为条件。
    2 对所有任务构造图 定义数据结构
    3 求出一次任务分配成功 分配的数量
     */

    //最大流算法，三个阶段，1 类型为1、5、8同时做，选中的1的复杂任务继续 2 类型为2，3，6，9，11，同理最大流，，，以后4，7，12

    public void maxNumAssignment(List<CTask> clist, List<Worker> wlist)
    {
        //
        for (CTask cTask:clist)
        {

        }
    }


}
