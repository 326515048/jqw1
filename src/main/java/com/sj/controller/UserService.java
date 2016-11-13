package com.sj.controller;

import com.sj.pojo.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-11-13.
 */
@Controller
public class UserService {
    private static final Log log = LogFactory.getLog(UserService.class);

    @ResponseBody
    @RequestMapping(value = "/test/{taskID}",
            method = RequestMethod.GET)
    public String test(@PathVariable String taskID) {
        log.info(taskID);
        return "test " + taskID;
    }

    @ResponseBody
    @RequestMapping(value = "/user/all",
            method = RequestMethod.GET)
    public List<User> user_all() {
        List<User> userList = new ArrayList<User>();
        Sqlite3DBUtil sq3 = new Sqlite3DBUtil();
        String sql = "select * from _user";
        try {
            Statement stmt = Sqlite3DBUtil.connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                userList.add(new User(
                        rs.getString("UUID"),
                        rs.getString("userID"),
                        rs.getString("userName")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

}