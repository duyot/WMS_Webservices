package com.wms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Context;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by duyot on 8/23/2016.
 */
@Component
public class ConnectionManager {
//    private static HikariPool hikariPool;
    public static Context initContext;
    public static Context envContext;
//    public static DataSource ds;

    @Autowired
    public DataSource dataSource;

    public Connection getConnectionInSpring(){
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void initDataSource(){
//        try {
//            initContext = new InitialContext();
//            envContext = (Context) initContext.lookup("java:comp/env");
//            ds = (DataSource) envContext.lookup("jdbc/ConnectionPool");
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }

//    public static Connection getConnection(){
//        if(ds == null){
//            initDataSource();
//        }
//        int retry = 0;
//        try {
//            while (retry < 2){
//                try {
////                    Connection conn = null;
//                    Connection conn = ds.getConnection();
//                    if(conn != null){
//                        return conn;
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//                retry ++;
//                System.out.println("Get connection fail:"+ retry+" , retry in 5s...");
//                Thread.sleep(5000);
//            }
//
//        }catch (Exception e){
//            System.out.println("Get connection fail:" + e.toString());
//        }
//        return null;
//    }


//    public static Connection getConnection(){
//        if(hikariPool ==null){
//            initHikariPool();
//        }
//        //
//        try {
//            return hikariPool.getConnection();
//        } catch (SQLException e) {
//            System.out.println("Could not get connection caused: ");
//            e.printStackTrace();
//            return null;
//        }
//    }

    private static void initHikariPool(){
//        if(hikariPool != null){
//            return;
//        }
//        //
//        HikariConfig config = null;
//        try {
//            config = new HikariConfig("hikari.properties");
//            hikariPool = new HikariPool(config);
//        } catch (Exception e) {
//            System.out.println("Could not init pool caused: ");
//            e.printStackTrace();
//        }
    }


//    public static void main(String[] args) {
//        for(int i = 1;i<30;i++){
//            Connection connection = ConnectionManager.getConnection();
//            System.out.println("Init connection number: "+ i);
//        }
//        System.out.println("Init connection ok");
//    }
}
