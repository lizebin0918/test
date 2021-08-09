package com.lzb.singleton;

import org.springframework.mail.MailMessage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 枚举单例<br/>
 * Created on : 2021-08-08 23:04
 *
 * @author lizebin
 */
public class EnumSingleton {

    static {
        System.out.println("静态代码块");
    }

    /**
     * 初始化时机：System.out.println(ConnecttionFactory.connectionFactory);
     */
    public enum ConnecttionFactory {
        /**
         * 工厂
         */
        connectionFactory;
        private Connection connection;
        private ConnecttionFactory() {
            System.out.println("ConnectionFactory 实例化");
            try {
                String url = "";
                String username = "";
                String password = "";
                String driverName = "";
                Class.forName("");
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public Connection getConnection() {
            return connection;
        }
    }

    public static Connection getConnection() {
        return ConnecttionFactory.connectionFactory.getConnection();
    }

    public static void main(String[] args) {
        System.out.println("test");
        System.out.println(ConnecttionFactory.connectionFactory);
    }

}
