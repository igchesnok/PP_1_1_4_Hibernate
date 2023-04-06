package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД

    private static final String DB_Driver = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/stydent";
    private static final String DB_USERNAME = "root";
    private static final String DB_PASSWORD = "123";
    public static final String TABLE_USER = "stydents";

    public static Connection getConnection() throws RuntimeException {
        Connection connection;
        try {
            Class.forName(DB_Driver);
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            connection = null;
            throw new RuntimeException(e);
        }
        return connection;
    }

    private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Настройки Hibernate эквивалентны свойствам файла hibernate.cfg.xml.
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, DB_Driver);
                settings.put(Environment.URL, DB_URL+"?useSSL=false");
                settings.put(Environment.USER, DB_USERNAME);
                settings.put(Environment.PASS, DB_PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
                settings.put(Environment.DEFAULT_SCHEMA, TABLE_USER);
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "create");

               // settings.put(Environment.C3P0_MIN_SIZE, "5");
               // settings.put(Environment.C3P0_MAX_SIZE, "200");
               // settings.put(Environment.C3P0_MAX_STATEMENTS, "200");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
