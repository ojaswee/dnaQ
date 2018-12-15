package dnaQ.Connections;

import com.jcraft.jsch.*;

import java.io.InputStream;
import java.util.ArrayList;

public class SSHConnection {

    private static Session sshSession;

    private SSHConnection() {
    }

    public static void connect() throws Exception {
        sshSession = connectToSSH();
    }

    private static Session connectToSSH()throws Exception{
        JSch jsch = new JSch();
        Session sshSession = jsch.getSession("root", "127.0.0.1",22);
        sshSession.setPassword("main");
        sshSession.setConfig("StrictHostKeyChecking", "no");
        sshSession.connect();
        return sshSession;
    }

    private static CommandResponse executeCommandAndGetOutput(String command) throws Exception{
        StringBuilder result = new StringBuilder();
        ChannelExec channel = null;

        try{
//            A channel connected to a remotely executing program uses "exec"
            channel = (ChannelExec) sshSession.openChannel("exec");
            channel.setCommand(command);
            channel.setInputStream(null);
            InputStream stdout = channel.getInputStream();
            channel.connect();

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
                if (channel.isClosed()){
                    break;
                }
            }

            ArrayList<String> responseLines = new ArrayList<String>();
            for(String s : result.toString().split("\\r?\\n")) {
                responseLines.add(s);
            }
            return new CommandResponse(responseLines, channel.getExitStatus());
        }finally {
            if (channel != null) {
                channel.disconnect();
            }
        }
    }

    public static void testRun() throws Exception {
        String command = "cat /home/ojaswee/test.txt";
        CommandResponse rs = executeCommandAndGetOutput(command);

        if(rs.exitStatus != 0) {
            throw new Exception("Error creating local BAM file on server.");
        }
        System.out.println(rs.responseLines);

    }
}
