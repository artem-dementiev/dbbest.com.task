package connect;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBConnection is class which create connections to DB
 * @author Artem Dementiev
 */
public class DBConnection {
    private static final Logger LOGGER = Logger.getLogger(DBConnection.class);
    public static final String dbDriver= "org.h2.Driver";
    public static String dbURL = "jdbc:h2:~/test";
    public static final String dbUsername = "scott";
    public static final String dbPassword = "scott";

    /**
     * Get connection to DB
     * @return connection to DB
     */
    public static Connection initializeDatabase() {
        Connection connection = null;

        try {
            Class.forName(dbDriver);
            connection = DriverManager.getConnection(dbURL,dbUsername,dbPassword);
            LOGGER.info("Connection ON.");
        } catch (ClassNotFoundException | SQLException e) {
            LOGGER.error(e);
            LOGGER.info("Connection ERROR.");
        }
        return connection;
    }
} 