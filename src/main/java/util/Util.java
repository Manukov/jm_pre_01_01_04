package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Util {

    // настройка соеденения с БД (Hibernate)
    private static final SessionFactory sessionFactory = buildSesionFactory();

    private static SessionFactory buildSesionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    public static SessionFactory getSesionFactory() {
        return sessionFactory;
    }

    public static void closeSessionFactory() {
        sessionFactory.close();
    }



    // настройка соеденения с БД (JDBC)
    private final String URL="jdbc:mysql://localhost:3306/test";
    private final String USERNAME="root";
    private final String PASSWORD="root";

    Driver driver;
    Connection connection=null;

    public Util() {
        driver=getDriver();
        regisrtyDriver(driver);
    }

    private Driver getDriver() {
        Driver drive;
        try {
            driver = new com.mysql.cj.jdbc.Driver();;
        } catch (SQLException e) {
            System.out.println("Driver not found");
        }
        return driver;
    }

    private void regisrtyDriver(Driver driver) {
        try {
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            System.out.println("Драйвер не зарегистрирован");
        }
    }

    public Connection getConnection() {
        try {
            connection=DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Cant create Connection");
        }
        return connection;
    }
}
