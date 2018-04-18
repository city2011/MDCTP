package greedyStrategy.bean;

public class Worker {
    int id;
    int skill[];
    int skill_efficiency[];

    public Worker(int id, int skill[], int efficiency[]) {
        this.id = id;
        this.skill = skill;
        this.skill_efficiency = efficiency;
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
        String ret = "Id:"+id+"  Skill:";
        for (int x:skill) {
            ret+=""+x+" ";
        }
        ret+=" Eff:";
        for (int y:skill_efficiency
             ) {ret+=""+y+" ";
        }
        return ret;
    }
}
