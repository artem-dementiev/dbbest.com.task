import connect.DBConnection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;

public class DBConnectionTest {

    Connection connection;

    @Before
    public void before() {
        connection = DBConnection.initializeDatabase();
    }
    @After
    public void after() throws SQLException {
        connection.close();
    }
    @Test
    public void connectionValueShouldNotBeNull(){
        assertNotNull(DBConnection.initializeDatabase());
    }

}