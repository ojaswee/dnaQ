package dnaQ.Connections;

import java.sql.*;
import java.util.ArrayList;

import dnaQ.Models.Mutation;
import dnaQ.Models.Test;
import dnaQ.Models.TestQueue;
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

	//check credentials
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

	//get name of test for combobox in welcomeFrame
	public static ArrayList<String> getAllAvailableNameofTest()throws SQLException{

		String query = "SELECT DISTINCT (name) FROM test;";

		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		ArrayList<String> name = new ArrayList<String>();
		while(rs.next()) {
			name.add(rs.getString(1));
		}
		return name;
	}

	//get type of test
	public static ArrayList<String> getAllAvailableTypeofTest(String name)throws SQLException{

		String query = String.format("SELECT DISTINCT (type) FROM test where name = '%s';", name);

		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		ArrayList<String> type = new ArrayList<String>();
		while(rs.next()) {
			type.add(rs.getString(1));
		}
		return type;
	}

	//get completed test one by one
	public static Test getCompletedTest (ResultSet row) throws SQLException{
		Test test = new Test(
				getValueOREmpty(row.getString("testid")),
				getValueOREmpty(row.getString ("name")),
				getValueOREmpty(row.getString ("type")),
				getValueOREmpty(row.getString ("run")),
				getValueOREmpty(row.getString ("usertestid"))
		);
		return test;
	}

	//make an arraylist of completed test
	public static ArrayList<Test> getAllCompletedTest (String userid) throws Exception{
		String query = String.format("SELECT t.testid,t.name, t.type, ut.run, ut.usertestid\n" +
				"FROM usertest ut\n" +
				"INNER JOIN user u ON u.userid = ut.userid \n" +
				"INNER JOIN test t ON t.testid = ut.testid \n" +
				"WHERE ut.userid = '%s'",userid);
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Test> tests = new ArrayList<Test>();

		while(rs.next()){
			Test t = getCompletedTest(rs);
			tests.add(t);
		}
		preparedStatement.close();

		return tests;
	}

	//make an arraylist of tests that are processing
	public static ArrayList<TestQueue> getAllProcessingTest(String userid)throws SQLException{
		String query = String.format("SELECT q.testid, t.name, t.type\n" +
				"FROM test t\n" +
				"INNER JOIN queue q ON q.testid= t.testid \n" +
				"WHERE q.userid = '%s' AND q.status = 0",userid);
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();


		ArrayList<TestQueue> testQ = new ArrayList<TestQueue>();

		while(rs.next()){
			TestQueue q = new TestQueue(
					getValueOREmpty(rs.getString("testid")),
					getValueOREmpty(rs.getString ("name")),
					getValueOREmpty(rs.getString ("type"))
			);

			testQ.add(q);
		}
		preparedStatement.close();

		return testQ;
	}

	//get testid for newly uploaded file
	public static String getTestid(String name, String type)throws SQLException{
        String query = String.format("SELECT testid FROM test WHERE name = '%s' AND type = '%s'", name, type);

        PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
        ResultSet rs = preparedStatement.executeQuery();

        String testid= "";
        while(rs.next()) {
            testid = getValueOREmpty(rs.getString("testid"));
        }
	    return testid;
    }

    //get run count for newly uploded file
    public static String getRun (String userid,String testid)throws SQLException{
		String run="";
		String query = String.format("SELECT count(testid)AS run FROM usertest WHERE userid = '%s' AND testid = '%s'", userid, testid);

		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		Integer i = 0;
		while(rs.next()) {
			i = rs.getInt("run") +1 ;
		}
		run = i.toString();
		return run;
	}


	private static Mutation getMutation(ResultSet row) throws SQLException{
		Mutation mutation = new Mutation(
				row.getInt("usertestid"),
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

		return mutation;
	}

	public static ArrayList<Mutation> getAllMutation(String usertestid) throws Exception {
		String query = "Select * from mutation where usertestid = " +usertestid +";";
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();

		ArrayList<Mutation> mutations = new ArrayList<Mutation>();

		while(rs.next()){
			Mutation m = getMutation(rs);
			mutations.add(m);
		}
		preparedStatement.close();

		return mutations;
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
