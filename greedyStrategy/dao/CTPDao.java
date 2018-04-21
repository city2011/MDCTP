package greedyStrategy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午2:59 2018/4/20
 * @Modified By:city
 */
public class CTPDao {

    public static void insertReport(int wn, int tn, double anil, int wnw, double awlt, long et)
    {
        Connection conn = dbFactory.getConn();

        String sql;
        sql = "INSERT INTO workload (workernum,tasknum,avgnumberinload,workingnumofworker,avgworkloadtime,expiredtime) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1,wn);
            pstm.setInt(2,tn);
            pstm.setDouble(3,anil);
            pstm.setInt(4,wnw);
            pstm.setDouble(5,awlt);
            pstm.setLong(6,et);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

//    // 执行查询
//            System.out.println(" 实例化Statement对象...");
//    stmt = conn.createStatement();
//    String sql;
//    sql = "SELECT id, name, url FROM websites";
//    ResultSet rs = stmt.executeQuery(sql);
//
//    // 展开结果集数据库
//            while (rs.next()) {
//        // 通过字段检索
//        int id = rs.getInt("id");
//        String name = rs.getString("name");
//        String url = rs.getString("url");
//
//        // 输出数据
//        System.out.print("ID: " + id);
//        System.out.print(", 站点名称: " + name);
//        System.out.print(", 站点 URL: " + url);
//        System.out.print("\n");
//    }
//    // 完成后关闭
//            rs.close();
//            stmt.close();
//            conn.close();
}
