package greedyStrategy.bean;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class CTask {
    int id;
    public int[] sub;
    long[] time;
    long create_time;
    long start_time;
    int reputation;

    public CTask(int id, int type, int reputation) {
        this.id = id;
        this.reputation = reputation;
        switch (type) {
            case 0:
                this.sub = GlobleBean.SUIT;
                this.time = setInitTime(0);
                break;
            case 1:
                this.sub = GlobleBean.JEANS;
                this.time = setInitTime(1);
                break;
            case 2:
                this.sub = GlobleBean.DOWNJACKET;
                this.time = setInitTime(2);
                break;
            default:
        }
    }

    public long[] setInitTime(int type) {
        long start = System.currentTimeMillis();
        create_time = start_time;
        switch (type) {
            case 0:
                time = new long[8];
                time[0] = start;
                time[1] = start + 2000;

                time[2] = start + 2000;
                time[3] = start + 4000;

                time[4] = start + 2000;
                time[5] = start + 3000;

                time[6] = start + 4000;
                time[7] = start + 6000;

                break;
            case 1:
                this.time = new long[6];
                time[0] = start;
                time[1] = start + 2000;

                time[2] = start + 2000;
                time[3] = start + 4000;

                time[4] = start + 4000;
                time[5] = start + 6000;
                break;
            case 2:
                this.time = new long[10];
                time[0] = start;
                time[1] = start + 2000;

                time[2] = start + 2000;
                time[3] = start + 3000;

                time[4] = start + 2000;
                time[5] = start + 4000;

                time[6] = start + 2000;
                time[7] = start + 5000;

                time[8] = start + 5000;
                time[9] = start + 7000;
                break;
            default:
        }
        return time;
    }

    public void updateTime(long upnumber) {
        start_time = upnumber;

        for (int i = 0; i < time.length; i++
                ) {
            time[i]+=upnumber-create_time;
        }
    }

    public String toString()
    {
        String ret = "Id:"+id+"  Sub:";
        for (int x:sub) {
            ret+=""+x+" ";
        }
        ret+=" Time:";
        for (long y:time
                ) {ret+=""+y+" ";
        }
        return ret;
    }

    public long[] getTime() {
        return time;
    }

    public void setTime(long[] time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public int[] getSub() {
        return sub;
    }

    public int getReputation() {
        return reputation;
    }
}
