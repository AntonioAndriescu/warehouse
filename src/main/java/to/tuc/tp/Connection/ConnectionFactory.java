package to.tuc.tp.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Efectueaza conexiunea cu baza de date a programului
 */
public class ConnectionFactory {
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DBURL = "jdbc:mysql://localhost:3306/warehouse";
    private static final String USER = "root";
    private static final String PASS = "mysqlpassword";

    /**
     * Obiect de tipul Singleton
     */
    public static ConnectionFactory singleInstance = new ConnectionFactory();

    /**
     * Creaza un obiect instanta a clasei ConnectionFactory
     */
    public ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creaza conexiunea cu baza de date
     * @return Obiect din calsa Connection
     */
    public java.sql.Connection createConnection() {
        java.sql.Connection connection = null;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occured while trying to connect to the database");
            e.printStackTrace();
        }
        return connection;
    }

    /**
     * Returneaza conexiune creata cu baza de date
     * @return Obiect din clasa Connection
     */
    public java.sql.Connection getConnection() {
        return singleInstance.createConnection();
    }
}
