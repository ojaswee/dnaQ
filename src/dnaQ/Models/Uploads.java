package dnaQ.Models;

import dnaQ.Connections.DatabaseConnections;
import dnaQ.Connections.SSHConnection;

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
    private String userCreationDate;


    public Uploads(String userid, String testName, String testType, String filePath,String clientfileName) throws Exception {
        this.userid = userid;
        this.testName = testName;
        this.testType = testType;
        this.filePath = filePath;
        this.clientfileName = clientfileName;

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        userCreationDate = dateFormat.format(date);

        getTestidAndRun();
        createFileName();
        transferFileAndQueue();
    }
    
    private void getTestidAndRun() throws SQLException {

        testid = DatabaseConnections.getTestid(testName, testType);

        run = DatabaseConnections.getRun(userid,testid);

//        JOptionPane.showMessageDialog(null, testName + testType+testid+ run);
    }

    private void createFileName() {
        serverfileName = userid + "_" + testid + "_RUN" +run ;
    }

    public void transferFileAndQueue() throws Exception {

        SSHConnection.createUserDir(userCreationDate,userid,testid,run);

        SSHConnection.transferSampleFromLocalToServer(filePath,clientfileName,serverfileName);

        DatabaseConnections.insertInQueue(userid,testid,run);

//        JOptionPane.showMessageDialog(null, filePath);
        }
}
