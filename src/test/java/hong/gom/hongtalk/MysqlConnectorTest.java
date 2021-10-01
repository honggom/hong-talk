package hong.gom.hongtalk;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.jayway.jsonpath.internal.path.PathCompiler.fail;

public class MysqlConnectorTest {

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void mysql_커넥션_테스트() {

        try(Connection con =
                    DriverManager.getConnection(
                            "jdbc:mysql://localhost:3306/hong_talk?serverTimezone=Asia/Seoul",
                            "root",
                            "1234")){
            System.out.println(con);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
