package greedyStrategy.bean;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 下午8:24 2018/4/24
 * @Modified By:city
 */
public class Graph {

    int vertexmax = 101000;
    final int EM = 500100;
    final int INF = 0x3f3f3f3f;


    class Edge {
        int to, nxt;
        int cap;
    }

    Edge edge[];

    int n, m, k, cnt, head[], map[][];
    int dep[];
    int gap[];
    int cur[];
    int aug[];
    int pre[];

    void addedge(int cu, int cv, int cw) {
        edge[cnt].to = cv;
        edge[cnt].cap = cw;
        edge[cnt].nxt = head[cu];
        head[cu] = cnt++;
        edge[cnt].to = cu;
        edge[cnt].cap = 0;
        edge[cnt].nxt = head[cv];
        head[cv] = cnt++;
    }

    int src, des;

    int SAP(int n){
        int max_flow=0;
        int u=src;
        int v;
        int id,mindep;
        aug[src]=INF;
        pre[src]=-1;
        gap[0]=n;

        for(int i=0;i<=n;i++)
            cur[i]=head[i]; // 初始化当前弧为第一条弧

        while(dep[src]<n){
            int flag=0;

            if(u==des){
                max_flow+=aug[des];
                for(v=pre[des];v!=-1;v=pre[v]){// 路径回溯更新残留网络
                    id=cur[v];
                    edge[id].cap-=aug[des];
                    edge[id^1].cap+=aug[des];
                    aug[v]-=aug[des];   // 修改可增广量，以后会用到
                    if(edge[id].cap==0) // 不回退到源点，仅回退到容量为0的弧的弧尾
                        u=v;
                }
            }

            for(int i=cur[u];i!=-1;i=edge[i].nxt){
                v=edge[i].to;    // 从当前弧开始查找允许弧
                if(edge[i].cap>0 && dep[u]==dep[v]+1){  // 找到允许弧
                    flag=1;
                    pre[v]=u;
                    cur[u]=i;
                    aug[v]=Math.min(aug[u],edge[i].cap);
                    u=v;
                    break;
                }
            }

            if(flag==0){
                if(--gap[dep[u]]==0)    //gap优化，层次树出现断层则结束算法
                    break;
                mindep=n;
                cur[u]=head[u];
                for(int i=head[u];i!=-1;i=edge[i].nxt){
                    v=edge[i].to;
                    if(edge[i].cap>0 && dep[v]<mindep){
                        mindep=dep[v];
                        cur[u]=i;   // 修改标号的同时修改当前弧
                    }
                }
                dep[u]=mindep+1;
                gap[dep[u]]++;
                if(u!=src)  // 回溯继续寻找允许弧
                    u=pre[u];
            }
        }
        return max_flow;
    }


    /*public static void main(String[] args) {
        //freopen("input.txt","r",stdin);
        while(~scanf("%d%d%d",&n,&m,&k)){
            cnt=0;
            src=0; des=n*m+1;
            int sum=0;
            for(int i=1;i<=n;i++)
                for(int j=1;j<=m;j++){
                    scanf("%d",&map[i][j]);
                    sum+=map[i][j];
                }
            int x,y;
            while(k--){
                scanf("%d%d",&x,&y);
                if((x+y)%2==0)
                    addedge(src,(x-1)*m+y,INF);
                else
                    addedge((x-1)*m+y,des,INF);
            }
            for(int i=1;i<=n;i++)
                for(int j=1;j<=m;j++){
                    int tmp=(i-1)*m+j;
                    if((i+j)%2==0)
                        addedge(src,tmp,map[i][j]);
                    else
                        addedge(tmp,des,map[i][j]);
                }
            for(int i=1;i<=n;i++)
                for(int j=1;j<=m;j++)
                    if((i+j)%2==0){
                        int tmp=(i-1)*m+j;
                        if(i>1)    addedge(tmp,tmp-m,2*(map[i][j]&map[i-1][j]));
                        if(i<n)    addedge(tmp,tmp+m,2*(map[i][j]&map[i+1][j]));
                        if(j>1)    addedge(tmp,tmp-1,2*(map[i][j]&map[i][j-1]));
                        if(j<m)    addedge(tmp,tmp+1,2*(map[i][j]&map[i][j+1]));
                    }
            printf("%d\n",sum-SAP(des+1));
        }
        return 0;
    }*/
}

