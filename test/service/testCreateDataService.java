package test.service;

import greedyStrategy.Service.CreateDataService;
import greedyStrategy.bean.CTask;
import greedyStrategy.bean.Worker;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;

public class testCreateDataService {
    @Test
    public void testCreateDateService() {
        CreateDataService cds = new CreateDataService();
        List<Worker> wlist = cds.createWorkers(500);

        Iterator it = wlist.iterator();
        while(it.hasNext())
        {
            Worker worker = (Worker) it.next();
            System.out.println(worker.toString());
        }
    }

    @Test
    public void testCreateTasks()
    {
        CreateDataService cds = new CreateDataService();
        List<CTask> clist = cds.createTasks(30);
        Iterator it = clist.iterator();
        while(it.hasNext())
        {
            CTask cTask = (CTask) it.next();
            System.out.println(cTask.toString());
        }
    }

    @Test
    public void testCreateTypeWorkers()
    {
        CreateDataService cds = new CreateDataService();
        List<Worker> wlist = cds.createTypeWorkers(100,2);

        Iterator it = wlist.iterator();
        while(it.hasNext())
        {
            Worker worker = (Worker) it.next();
            System.out.println(worker.toString());
        }
    }
}
