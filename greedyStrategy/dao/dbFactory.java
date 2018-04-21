package greedyStrategy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ProjectName: MACCTP
 * @Author: City
 * @Description:
 * @Date: Created in 上午1:06 2018/4/20
 * @Modified By:city
 */
public class dbFactory {
    // JDBC 驱动名及数据库 URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/MDCTP";

    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "root";
    static final String PASS = "mysql123";

    private static Connection conn = null;

    public static Connection getConn(){
        // 注册 JDBC 驱动
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException se) {
            // 处理 JDBC 错误
            se.printStackTrace();
        } catch (Exception e) {
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
        return conn;
    }


}
