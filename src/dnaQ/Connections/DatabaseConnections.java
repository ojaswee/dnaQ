package dnaQ.Connections;

import java.sql.*;
import java.util.ArrayList;

import dnaQ.Models.Sample;
import dnaQ.Models.Test;
import dnaQ.Models.User;

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


	public static Test getUserTest (ResultSet row) throws SQLException{
		Test test = new Test(
				getValueOREmpty(row.getString("testid")),
				getValueOREmpty(row.getString ("name")),
				getValueOREmpty(row.getString ("type")),
				getValueOREmpty(row.getString ("run")),
				getValueOREmpty(row.getString ("usertestid")),
				getValueOREmpty(row.getString ("status"))
		);
		return test;
	}

	public static ArrayList<Test> getAllUserTest (String userid) throws Exception{
		String query = String.format("SELECT t.testid,t.name, t.type, ut.run, ut.usertestid,q.status\n" +
				"FROM usertest ut\n" +
				"INNER JOIN user u ON u.userid = ut.userid \n" +
				"INNER JOIN test t ON t.testid = ut.testid \n" +
				"INNER JOIN queue q ON q.usertestid = ut.usertestid\n" +
				"WHERE ut.userid = '%s';" ,userid);
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Test> userTest = new ArrayList<Test>();

		while(rs.next()){
			Test t = getUserTest(rs);
			userTest.add(t);
		}
		preparedStatement.close();

		return userTest;
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


		//Check reports name
		String query = "select name from report ;" ;
		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		while(rs.next()){
			report.add(rs.getString(1));
		}

		return report;
	}
}
