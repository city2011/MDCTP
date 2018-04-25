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

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public static void insertOneRound(int workerNumber, int taskNumber, int complete_ret, int wait_ret, int complete_num, double avg, double avg_num,long time,String wtypenum) {
        Connection conn = dbFactory.getConn();

        String sql;
        sql = "INSERT INTO OneAllocate (workernum,tasknum,suctnum,taskwait,taskcomplete,avgloadtime,avgloadnum,expiredtime,wtypenum) values (?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setInt(1,workerNumber);
            pstm.setInt(2,taskNumber);
            pstm.setInt(3,complete_num);
            pstm.setInt(4,wait_ret);
            pstm.setInt(5,complete_ret);
            pstm.setDouble(6,avg);
            pstm.setDouble(7,avg_num);
            pstm.setInt(8,(int)time);
            pstm.setString(9,wtypenum);
            pstm.executeUpdate();

            pstm.close();
            conn.close();
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
