package com.sj.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by fan.shijun on 2016-01-12.
 */
//@Controller
public class NstDownController {
    private static final Log log = LogFactory.getLog(NstDownController.class);

    @ResponseBody
    @RequestMapping(value="/addxml",
            method = RequestMethod.GET)
    public ResponseResult addxml(
            @RequestParam String xmlURL) {
        log.info("add xml task=" + xmlURL);
        ResponseResult r = new ResponseResult();
        r.setState(0);
        r.setMemo("OK");
        SimpleDateFormat stimefmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date now = new Date();
        String addTime = stimefmt.format(now);
        String uuid = UUID.randomUUID().toString();
        String strsql = "insert into _xmltask (id,xmlPath,addTime,state) "
                + "values('" + uuid + "','" + xmlURL + "','" + addTime + "',0)";
        Sqlite3DBUtil sq3 = new Sqlite3DBUtil();
        sq3.sqlupdate(strsql);
        return r;
    }

    @ResponseBody
    @RequestMapping(value="/update/{taskID}/{processGuid}/{prgName}/{channelCode}/{priority}",
            method = RequestMethod.GET)
    public ResponseResult update(
            @PathVariable String taskID,
            @PathVariable String processGuid,
            @PathVariable String prgName,
            @RequestParam String ftpURL,
            @PathVariable String channelCode,
            @PathVariable int priority) {
        log.info("task update taskID=" + taskID
                + ";processGuid=" + processGuid
                + ";prgName=" + prgName
                + ";ftpURL=" + ftpURL
                + ";channelCode=" + channelCode
                + ";priority=" + priority);
        ResponseResult r = new ResponseResult();
        r.setState(0);
        r.setMemo("OK");
        String strsql = "select status from _ftptask where prgName='"
                + prgName + "' and channelCode='" + channelCode + "'";
        try {
            Sqlite3DBUtil sq3 = new Sqlite3DBUtil();
            Statement stmt = Sqlite3DBUtil.connection.createStatement();
            ResultSet rs = stmt.executeQuery(strsql);
            SimpleDateFormat stimefmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = new Date();
            String addTime = stimefmt.format(now);
            if (rs.next()) {
                if (rs.getInt("status") == 2) {
                    r.setState(-1);
                    r.setMemo("downloading");
                } else {
                    strsql = "update _ftptask set donum=0, taskid= '"+taskID+"',processGuid='"+processGuid
                            +"',addTime = '" + addTime + "', ftpURL = '" + ftpURL + "',priority = "
                            + priority + ",status=1 where prgName='" + prgName + "' and channelCode = '" + channelCode + "'";
                    sq3.sqlupdate(strsql);
                }
            } else {
                String uuid = UUID.randomUUID().toString();
                strsql = "insert into _ftptask (id,taskid,processGuid,prgName,ftpURL,channelCode,priority,donePercent,status,donum,addTime) "
                        + "values('" + uuid + "','" + taskID + "','" + processGuid + "','" + prgName + "','"
                        + ftpURL + "','" + channelCode + "'," + priority + ",0,1,0,'" + addTime + "')";
                sq3.sqlupdate(strsql);
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            r.setMemo(e.toString());
            r.setState(-1);
        }
        return r;
    }

    @ResponseBody
    @RequestMapping(value="/update/getServiceStatus/{status}",
            method = RequestMethod.GET)
    public String update_getServiceStatus(
            @PathVariable String status) {
        return status;
    }

    @ResponseBody
    @RequestMapping(value="/test/{taskID}",
            method = RequestMethod.GET)
    public String test(@PathVariable String taskID) {
        log.info(taskID);
        return "test "+ taskID;
    }

    @ResponseBody
    @RequestMapping(value="/{id1}/{id2}/{id3}",
            method = RequestMethod.POST)
    public String test2(
            @PathVariable String id1,
            @PathVariable String id2,
            @PathVariable String id3,
            @RequestParam String ftpURL) {
        log.info(ftpURL);
        return "test "+ ftpURL;
    }

    @ResponseBody
    @RequestMapping(value="/errorlog/{num}/{videoID}/{errlog}",
            method = RequestMethod.POST)
    public String errorlog(
            @PathVariable String num,
            @PathVariable String videoID,
            @PathVariable String errlog) {
        log.info(num+videoID+errlog);
        return "error: "+ num+videoID+errlog;
    }

    @ResponseBody
    @RequestMapping(value="/cancel/{taskID}",
            method = RequestMethod.GET)
    public ResponseResult cancel(@PathVariable String taskID){
        ResponseResult r = new ResponseResult();
        r.setState(0);
        r.setMemo("OK");
        Sqlite3DBUtil sq3 = new Sqlite3DBUtil();
        String delsql = "delete from _ftptask where taskid = '"+taskID+"'";
        if (sq3.sqlupdate(delsql)==-1){
            r.setState(-1);
            r.setMemo("sqlite3 excute error");
        }
        return r;
    }

    @ResponseBody
    @RequestMapping(value="/taskProcessQuery/query",
            method = RequestMethod.POST)
    public TaskProcessQueryResponse taskProcessQuery(
            @RequestBody String processGuid) {
        TaskProcessQueryResponse tpqr = new TaskProcessQueryResponse();
        TaskState ts = new TaskState();
        Sqlite3DBUtil sq3 = new Sqlite3DBUtil();
        processGuid = processGuid.substring(0,processGuid.length()-1);
        log.info("processGuid="+processGuid);
        String findsql = "select * from _ftptask where processGuid = '"+processGuid+"'";
        try {
            Statement stmt = Sqlite3DBUtil.connection.createStatement();
            ResultSet rs = stmt.executeQuery(findsql);
            if (rs.next()){
                ts.setProcessGuid(rs.getString("processGuid"));
                ts.setTaskID(rs.getString("taskid"));
                ts.setStatus(rs.getString("status"));
                ts.setPercent(rs.getString("donePercent")+"%");
                tpqr.setTaskProcess(ts);
                tpqr.setDescription(rs.getString("memolog"));
            }else{
                ts.setProcessGuid(processGuid);
                ts.setTaskID("not find");
                ts.setStatus("-1");
                ts.setPercent("-1");
                tpqr.setTaskProcess(ts);
                tpqr.setDescription("not find");
            }
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            ts.setProcessGuid(processGuid);
            ts.setTaskID("FTPDownload");
            ts.setStatus("-1");
            ts.setPercent("-1");
            tpqr.setTaskProcess(ts);
            tpqr.setDescription("sqlite3 error");
        }
        return tpqr;
    }

}
