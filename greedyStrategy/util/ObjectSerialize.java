package greedyStrategy.util;

import java.io.*;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午1:50 2018/4/24
 * @Modified By:city
 */
public class ObjectSerialize {
    /**
     * 序列化
     */
    private void serializeObj(Object obj) throws IOException {
        // ObjectOutputStream 对象输出流，将 flyPig 对象存储到E盘的 flyPig.txt 文件中，完成对 flyPig 对象的序列化操作
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("/Users/city/Desktop/Java/MACCTP/resource/workerset.txt")));
        oos.writeObject(obj);
        System.out.println("对象序列化成功！");
        oos.close();
    }

    /**
     * 反序列化
     */
    private Object deserializeObj(String filename) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("/Users/city/Desktop/Java/MACCTP/resource/" + filename)));
        Object ret = ois.readObject();
        System.out.println("FlyPig 对象反序列化成功！");
        return ret;
    }
}
