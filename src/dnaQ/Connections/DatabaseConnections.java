package dnaQ.Connections;

import java.sql.*;
import java.util.ArrayList;

import dnaQ.Models.Sample;
import dnaQ.Models.User;
import dnaQ.Models.UserQueue;


public class DatabaseConnections {

	private static Connection databaseConnection = null;

	public static void connect() throws Exception {

		String driver = "com.mysql.cj.jdbc.Driver";
		String dbuser = "root";
		String dbpasswd = "main";
		String db = "dnaq";
		String url = "jdbc:mysql://localhost/" + db + "?useSSL=false";

		try {
			databaseConnection = DriverManager.getConnection(url, dbuser, dbpasswd);
		} catch (Exception e) {
			throw new Exception("mysql connection error " + e.getMessage());
		}
	}

	public static User connectLogin(String email, String passwd) throws Exception {
		connect();
		User user = null;

		try {
			String query = String.format("SELECT * FROM user where email ='%s' AND password ='%s';", email, passwd);

			PreparedStatement pstm = databaseConnection.prepareStatement(query);

			ResultSet rs = pstm.executeQuery();

			if (rs.next()) {
				user = new User(rs.getString(1).toString(), rs.getString(2).toString(),
						rs.getString(3).toString(), email, passwd);
				pstm.close();
			}
		} catch (Exception e) {
			throw new Exception("mysql connection error " + e.getMessage());
		}
		return user;
	}

	public static UserQueue getUserQueue (ResultSet row) throws SQLException{
		UserQueue userqueue = new UserQueue(
				getValueOREmpty(row.getString("queueID")),
				getValueOREmpty(row.getString ("usertestID")),
				getValueOREmpty(row.getString ("status")),
				getValueOREmpty(row.getString ("createdon")));
		return userqueue;
	}

	public static ArrayList<UserQueue> getAllUserQueue(String userid) throws Exception {

		String query = String.format("SELECT * from queue where usertestid IN ( " +
				"SELECT usertestid FROM usertest where userid = '%s';" ,userid);
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<UserQueue> userqueue = new ArrayList<UserQueue>();

		while(rs.next()){
			UserQueue q = getUserQueue(rs);
			userqueue.add(q);
		}
		preparedStatement.close();

		return userqueue;
	}

	private static Sample getSample(ResultSet row) throws SQLException{
		Sample sample = new Sample(
				row.getInt("id"),
                getValueOREmpty(row.getString("chr")),
                row.getInt("pos"),
                getValueOREmpty(row.getString("ref")),
                getValueOREmpty(row.getString("alt")),
				getValueOREmpty(row.getString("cosmicid")),
                getValueOREmpty(row.getString("cds")),
                getValueOREmpty(row.getString("aa")),
                getValueOREmpty(row.getString("count")),
                getValueOREmpty(row.getString("clinvarid")),
                getValueOREmpty(row.getString("clndn")),
                getValueOREmpty(row.getString("clnsig")),
                getValueOREmpty(row.getString("mc")),
                getValueOREmpty(row.getString("origin")),
                getValueOREmpty(row.getString("g1000id")),
                getValueOREmpty(row.getString("altCount")),
                getValueOREmpty(row.getString("totalCount")),
                getValueOREmpty(row.getString("altGlobalFreq")),
                getValueOREmpty(row.getString("americanFreq")),
                getValueOREmpty(row.getString("asianFreq")),
                getValueOREmpty(row.getString("afrFreq")),
                getValueOREmpty(row.getString("eurFreq")),
                getValueOREmpty(row.getString("disease")),
                getValueOREmpty(row.getString("drugs")),
                getValueOREmpty(row.getString("clinicalSignificance")),
                getValueOREmpty(row.getString("evidenceStatement")),
                getValueOREmpty(row.getString("variantSummary")),
                getValueOREmpty(row.getString("gene")),
                getValueOREmpty(row.getString("proteinChange")),
                getValueOREmpty(row.getString("oncogenecity")),
                getValueOREmpty(row.getString("mutationEffect"))
				);

		return sample;
	}

	public static ArrayList<Sample> getAllSample() throws Exception {
		String query = "Select * from sample;";
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Sample> samples = new ArrayList<Sample>();

		while(rs.next()){
			Sample s = getSample(rs);
			samples.add(s);
		}
		preparedStatement.close();

		return samples;
	}

	private static String getValueOREmpty(String value){

		if(value.equals("['none']")) {
			return "";

		}else{
            value= value.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("'","");
        }
		return value;
	}

	public static ArrayList<String> getReportOptions() throws Exception{

		ArrayList<String> report = new ArrayList<String>();
		//TODO check the name of departmentID

		String query = "select name from report ;" ;
		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		while(rs.next()){
			report.add(rs.getString(1));
		}

		return report;
	}
}
