package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBContext {

    // UPDATE THESE CREDENTIALS FOR YOUR LOCAL SQL SERVER
    // Railway/Production environment variables
    private final String serverName = System.getenv("MSSQL_SERVER") != null ? System.getenv("MSSQL_SERVER") : "localhost";
    private final String dbName = System.getenv("MSSQL_DB") != null ? System.getenv("MSSQL_DB") : "TimeBankDB";
    private final String portNumber = System.getenv("MSSQL_TCP_PORT") != null ? System.getenv("MSSQL_TCP_PORT") : "1433";
    private final String userID = System.getenv("MSSQL_USER") != null ? System.getenv("MSSQL_USER") : "sa"; 
    private final String password = System.getenv("MSSQL_SA_PASSWORD") != null ? System.getenv("MSSQL_SA_PASSWORD") : "123";

    public Connection getConnection() throws Exception {
        // Use encrypt=false to avoid SSL issues on some cloud providers unless configured
        String url = "jdbc:sqlserver://" + serverName + ":" + portNumber + ";databaseName=" + dbName + ";encrypt=false;trustServerCertificate=true";
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return DriverManager.getConnection(url, userID, password);
    }
}
