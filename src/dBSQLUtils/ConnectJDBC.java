package dBSQLUtils;



import java.sql.*;



/**
 * Created by simin on 2017/10/16.
 */
public class ConnectJDBC {
    public static Connection connectDB() {
        //声明Connection对象
        Connection con=null;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://10.0.0.83:3306/jumei";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "sneakerhead";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("Succeeded connecting to the Database!");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return  con;
    }


}

