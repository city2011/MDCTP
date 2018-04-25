package greedyStrategy.bean;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午4:27 2018/4/25
 * @Modified By:city
 */
public class BGraph {

    int tasknum;//left point
    int matchnum;//right point
    int cx[];
    int cy[];//顶标
    int w[][];//权重
    int inf=(1<<20)-1;
    boolean usex[];
    boolean usey[];//是否搜索过
    int link[];//link[i]=x代表：在y图中的i与x相连

    //匈牙利过程 EK
    boolean dfs(int u){
        usex[u]=true;
        for(int i=1;i<=matchnum;i++)
            if(!usey[i]&&cx[u]+cy[i]==w[u][i]){
                usey[i]=true;
                if(link[i]==-1||dfs(link[i])){
                    link[i]=u;
                    return true;
                }
            }
        return false;
    }

    public int KM()
    {
        for (int i = 0; i < tasknum; i++) {
            for (int j = 0; j < matchnum; j++) {
                cx[i] = Math.max(cx[i],w[i][j]);
            }
        }
        for (int i = 0; i < tasknum; i++) {
            while(true)
            {
                int d = -1;
                if (dfs(i))
                    break;
                for (int j = 0; j < tasknum; j++) {
                    if(usex[i])
                    {
                        for (int k = 0; k < tasknum; k++) {
                            if(!usey[j])
                                d = Math.min(d,cx[i]+cy[k]-w[i][k]);
                        }
                    }
                }
                if (d==-1) 
                    return  -1;
                for (int j = 0; j < tasknum; j++) {
                    if (usex[j])
                        cx[j] -= d;
                }
                for (int k = 0; k < matchnum; k++) {
                        if(usey[k])
                            cy[k]+=d;
                }
            }
        }
        int ans = 0;
        for (int i = 0; i < matchnum; i++) {
            if(link[i]>=0)
                ans+=w[link[i]][i];
        }
        return ans;
    }
}


