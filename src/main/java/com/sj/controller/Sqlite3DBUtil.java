package com.sj.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by Administrator on 2016/1/12 0012.
 */
@Controller
public class Sqlite3DBUtil {
    private static final Log log = LogFactory.getLog(Sqlite3DBUtil.class);

    public static Connection connection = null;

    @PostConstruct
    public void init(){
        Properties prop = new Properties();// 属性集合对象
        FileInputStream fis = null;// 属性文件输入流
        try {
            String url = this.getClass().getResource("").getPath().replaceAll("%20", " ");
            String path = url.substring(0, url.indexOf("WEB-INF")) + "WEB-INF/config.properties";
            fis = new FileInputStream(path);
            prop.load(fis);// 将属性文件流装载到Properties对象中
            String dbpath = prop.getProperty("dbpath");
            log.info(dbpath);
            fis.close();// 关闭流

            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+dbpath);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //@PreDestroy
    public void overdestroy(){
        if(connection != null)
            try {
                log.info("sys destroy");
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
    }

    public int sqlupdate(String sql){
        int irs;
        try {
            log.info(sql);
            Statement statement = null;
            statement = connection.createStatement();
            irs = statement.executeUpdate(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            irs = -1;
        }
        return irs;
    }

}
