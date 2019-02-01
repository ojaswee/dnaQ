package dnaQ.Models;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;

import javax.swing.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Uploads {

    private String userid;
    private String testName;
    private String testType;
    private String filePath;
    private String testid;
    private String clientfileName;
    private String serverfileName;
    private String run;


    public Uploads(String userid, String testName, String testType, String filePath,String clientfileName) throws Exception {
        this.userid = userid;
        this.testName = testName;
        this.testType = testType;
        this.filePath = filePath;
        this.clientfileName = clientfileName;

        getTestidAndRun();
        createFileName();
        transerFileAndQueue();
    }
    
    private void getTestidAndRun() throws SQLException {

        testid = DatabaseConnections.getTestid(testName, testType);

        run = DatabaseConnections.getRun(userid,testid);
    }

    private void createFileName() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String d = dateFormat.format(date);

        String newClientfilename = clientfileName.replaceAll("\\s+","_");

        serverfileName = userid + "_" + testid + "_" +run + "_"+ d +"_"+newClientfilename;
    }

    public void transerFileAndQueue() throws Exception {
        SSHConnection.transferSampleFromLocalToServer(filePath,clientfileName,serverfileName);

        DatabaseConnections.insertInQueue(userid,testid);

        JOptionPane.showMessageDialog(null, filePath);
        }
}
