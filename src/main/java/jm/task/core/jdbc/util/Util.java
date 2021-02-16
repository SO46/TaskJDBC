package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;

import org.hibernate.*;
import org.hibernate.cfg.*;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.*;
import java.util.Properties;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/db_1?useSSL=false";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException ignored){
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {

        Properties settings = new Properties();
        settings.put(Environment.DRIVER, DRIVER);
        settings.put(Environment.URL, URL);
        settings.put(Environment.USER, USERNAME);
        settings.put(Environment.PASS, PASSWORD);
        settings.put(Environment.HBM2DDL_AUTO, "create-drop");

        try {
            return new Configuration().setProperties(settings).addAnnotatedClass(User.class)
                    .buildSessionFactory(new StandardServiceRegistryBuilder().applySettings(settings).build());
        } catch (Throwable  e){
            System.err.println("Initial SessionFactory creation failed." + e);
            throw new ExceptionInInitializerError(e);
        }
    }

}
