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

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class SSHConnection {

    private static Session sshSession;
    private static String userUploads = "/home/ojaswee/dnaq/analysis/";
    private static String reportCreator = "/home/ojaswee/github/dnaQ/report_generator/01_create_report.py";

    private SSHConnection() {
    }

    public static void connect() throws Exception {
        sshSession = connectToSSH();
    }

    private static Session connectToSSH()throws Exception{
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession("ojaswee", "127.0.0.1",22);
        sshSession.setPassword("main");
        sshSession.setConfig("StrictHostKeyChecking", "no");
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
    public static void createUserDir (String d,String userid, String testid, String run)throws Exception{

        String command = String.format("bash /home/ojaswee/github/dnaQ/pipeline_scripts/01_user_dir_creator.sh -d'%s' -u'%s' -t'%s' -r'%s'" ,d,userid, testid, run);

        CommandResponse rs = executeCommandAndGetOutput(command);

        if(rs.exitStatus != 0) {
            throw new Exception("Error creating file on server.");
        }
        System.out.println(rs.responseLines);

    }

    //user uploads test file
    public static void transferSampleFromLocalToServer(String fileLocation,String oldname, String newname) throws Exception {
        connect();
        ChannelSftp sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
        sftpChannel.connect();

        String source_path = fileLocation;
        String destination_path = userUploads + newname;

        sftpChannel.put(source_path, destination_path);
        String currentfolder = userUploads+newname+"/";

        sftpChannel.rename(currentfolder+oldname,currentfolder+newname+"_UPLOAD");
        sftpChannel.exit();
    }

    public static void createTop2ListForReport(String reportFolder, String []disease, String [] gene){
        String conditionFile = userUploads+reportFolder+"condition_gene.csv";

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(conditionFile));

            bw.write("condition,");
            bw.write(disease[0]);
            bw.write("\n");

            bw.write("condition,");
            bw.write(disease[1]);
            bw.write("\n");

            bw.write("gene,");
            bw.write(gene[0]);
            bw.write("\n");

            bw.write("gene,");
            bw.write(gene[1]);
            bw.write("\n");

            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void generateReport(String reportname,String inputFile, String outDir) throws Exception {

        String command = String.format("/opt/python3/bin/python3.4 " +
                "'%s' '%s' '%s' '%s' ", reportCreator,reportname, userUploads+inputFile, userUploads+outDir);

        CommandResponse rs = executeCommandAndGetOutput(command);

        if(rs.exitStatus != 0) {
            throw new Exception("Error creating file on server.");
        }
    }
    
    //user report is transfered from server to client
    public static void transferReportFromServer(String name, String filelocation) throws JSchException, SftpException {

        ChannelSftp sftpChannel = (ChannelSftp) sshSession.openChannel("sftp");
        sftpChannel.connect();

        String source_path = userUploads+filelocation;
        String destination_path = GUICommonTools.userDownloadFolder();

        sftpChannel.put(source_path, destination_path);

        sftpChannel.exit();
    }
}
