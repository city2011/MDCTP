package greedyStrategy.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午1:45 2018/4/25
 * @Modified By:city
 */
public class MapAss {
    HashMap<Integer,List> hashMap = new HashMap();
    public void addAss(int key, int value)
    {
        if(hashMap.get(key)==null) {
            List newList = new ArrayList();
            newList.add(value);
            hashMap.put(key, newList);
        }
        else {
            for (Map.Entry<Integer, List> entry : hashMap.entrySet()) {
               if(entry.getKey() == key) {
                   List list = entry.getValue();
                   list.add(value);
               }
            }
        }
    }

    public void delete(int key)
    {
        if(hashMap.get(key)==null)
            return;
        else
            hashMap.remove(key);
    }

    public String toString()
    {
        return hashMap.toString();
    }
}
