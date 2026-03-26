package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    // UPDATE THESE CREDENTIALS FOR YOUR LOCAL SQL SERVER
    private final String serverName = "localhost";
    private final String dbName = "TimeBankDB";
    private final String portNumber = "1433";
    private final String instance = ""; // Leave blank if not using named instance
    private final String userID = "sa"; 
    private final String password = "123";

    public Connection getConnection() throws Exception {
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + "\\" + instance + ";databaseName=" + dbName;
        if (instance == null || instance.trim().isEmpty()) {
            url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=false";
        }
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
}
