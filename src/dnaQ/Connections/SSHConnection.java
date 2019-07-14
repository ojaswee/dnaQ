/*
SSHConnection.java
-connects to remove server
-using stfp channel transfer files from local to remote server
- executes bash
    - bash runs python file
    - python files make a txt files, charts and convert to pdf
- using stfp channel transfer pdf from server to local computer
- ends

*/
package dnaQ.Connections;

import com.jcraft.jsch.*;
import dnaQ.GUI.GUICommonTools;
import dnaQ.Models.Report;

import java.io.InputStream;
import java.util.ArrayList;


public class SSHConnection {

    private static Session sshSession;
    private static String userLocalReportHome = "/home/" + GUICommonTools.getUsernameFromOS()+ "/dnaq/report_upload/";
    private static String userServerHome = "/home/ojaswee/dnaq/analysis/";
    private static String reportCreator = "/home/ojaswee/github/dnaQ/report_generator/01_create_report.py";


    private SSHConnection() {
    }

    public static void connect() throws Exception {
        sshSession = connectToSSH();
    }

    private static Session connectToSSH()throws Exception{
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession("ojaswee", "192.168.1.7",22);
        sshSession.setPassword("main");
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(config);
        sshSession.connect();
        return sshSession;
    }

    private static CommandResponse executeCommandAndGetOutput(String command) throws Exception{
        connect();
        StringBuilder result = new StringBuilder();
        ChannelExec exechannel = null;

        try{
            //A channel connected to a remotely executing program uses "exec"
            exechannel = (ChannelExec) sshSession.openChannel("exec");
            exechannel.setCommand(command);
            exechannel.setInputStream(null);
            InputStream stdout = exechannel.getInputStream();
            exechannel.connect();


            //Read output line by line
            byte[] tmp = new byte[1024];
            while (true){
                while (stdout.available() > 0){
                    int i = stdout.read(tmp, 0, 1024);
                    if (i < 0)
                        break;
                    String thisLine = new String(tmp, 0, i);
                    result.append(thisLine);
                }
                if (exechannel.isClosed()){
                    break;
                }
            }

            ArrayList<String> responseLines = new ArrayList<String>();
            for(String s : result.toString().split("\\r?\\n")) {
                responseLines.add(s);
            }
            return new CommandResponse(responseLines, exechannel.getExitStatus());
        }finally {
            if (exechannel != null) {
                exechannel.disconnect();
            }
        }
    }

    //generate user directory in server
    public static void createUserDir (String date,String userid, String testid, String run)throws Exception{

        String command = String.format("bash /home/ojaswee/github/dnaQ/pipeline_scripts/01_user_dir_creator.sh -d'%s' -u'%s' -t'%s' -r'%s'" ,date,userid, testid, run);

        CommandResponse rs = executeCommandAndGetOutput(command);

        if(rs.exitStatus != 0) {
            throw new Exception("Error creating file on server.");
        }
        System.out.println(rs.responseLines);

    }

    public static void createReportDir (String dirPath)throws Exception{

        String command = String.format("mkdir %s" ,dirPath);

        CommandResponse rs = executeCommandAndGetOutput(command);

        if(rs.exitStatus != 0) {
            throw new Exception("Error creating directory on server.");
        }
        System.out.println(rs.responseLines);

    }

    //user uploads test file
    public static void transferSampleFromLocalToServer(String fileLocation,String oldname, String newname) throws Exception {
        connect();
        ChannelSftp sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
        sftpChannel.connect();

        String source_path = fileLocation;
        String destination_path = userServerHome + newname;

        sftpChannel.put(source_path, destination_path);
        String currentfolder = userServerHome +newname+"/";

        sftpChannel.rename(currentfolder+oldname,currentfolder+newname+"_UPLOAD_PARSED");
        sftpChannel.exit();
    }


    public static void transferReportFileLocaltoServer(Report report) throws Exception {

        connect();

        ChannelSftp sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
        sftpChannel.connect();

        String source_path = userLocalReportHome+report.getReportOriginator()+"_"+report.getReportIdentifier();
        String destination_path = userServerHome + report.getReportOriginator()+"/Report/"+report.getReportIdentifier()+"/";

        createReportDir(destination_path);

        sftpChannel.put(source_path, destination_path);

        sftpChannel.exit();

    }

    public static void generateReport(Report report) throws Exception {

        transferReportFileLocaltoServer(report);

        String command = String.format("/opt/python3/bin/python3.4 " +
                "'%s' '%s' '%s' '%s' ", reportCreator,
                report.getReportOriginator()+"_"+report.getReportIdentifier(),
                userServerHome + report.getReportOriginator()+"/Report/"+report.getReportIdentifier()+"/"+report.getReportOriginator()+"_"+report.getReportIdentifier(),
                userServerHome + report.getReportOriginator()+"/Report/"+report.getReportIdentifier()+"/");

        executeCommandAndGetOutput(command);

//        if(rs.exitStatus != 0) {
//            System.out.println("Error generating report on server.");
//            throw new Exception("Error generating report on server.");
//
//        }
    }
    
    //user report is transfered from server to client
    public static void transferReportFromServer( String filelocation) throws Exception {
        connect();
        ChannelSftp sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
        sftpChannel.connect();

        String source_path = userServerHome +filelocation;
        String destination_path = GUICommonTools.userDownloadFolder();

        sftpChannel.get(source_path, destination_path);

        sftpChannel.exit();
    }

}
