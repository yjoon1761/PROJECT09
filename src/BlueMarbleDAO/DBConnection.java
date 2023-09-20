package BlueMarbleDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection DBConnect(){
        Connection con = null;

        String user = "SYSTEM";
        String password = "1209";
        String url = "jdbc:oracle:thin:@localhost:1521:xe";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("접속 성공!");
        } catch (ClassNotFoundException e) {
            System.out.println("접속 실패 : 드라이버 로딩 실패!");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            System.out.println("접속 실패 : 접속정보 오류!");
            throw new RuntimeException(e);
        }
        return con;
    }
}