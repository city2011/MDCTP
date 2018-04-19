package greedyStrategy.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @ProjectName: MDCTP
 * @Author: City
 * @Description:
 * @Date: Created in 下午4:18 2018/4/19
 * @Modified By:city
 */
public class Util {

    public static String timestampToDate(long timestamp){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        res = simpleDateFormat.format(date);
        return res;
    }
}
