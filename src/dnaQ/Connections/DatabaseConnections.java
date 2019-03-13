package dnaQ.Connections;

import java.sql.*;
import java.util.ArrayList;

import dnaQ.Models.Mutation;
import dnaQ.Models.TestQueue;
import dnaQ.Models.User;

import static java.sql.JDBCType.NULL;
import static java.util.Objects.isNull;

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


	//insert new record in queue
    public static void insertInQueue(String userid,String testid ,String run)throws SQLException{
        String query = String.format("INSERT INTO queue (userid,testid,run,status,createdon)values ('%s','%s','%s',0,NOW());",userid,testid,run);

        PreparedStatement pstm = databaseConnection.prepareStatement(query);
        pstm.executeUpdate();
    }

	//get type of test
	public static ArrayList<String> getAllAvailableTypeofTest(String name)throws SQLException{

		String query = String.format("SELECT DISTINCT (type) FROM test where name ='%s';", name);

		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		ArrayList<String> type = new ArrayList<String>();
		while(rs.next()) {
			type.add(rs.getString(1));
		}
		return type;
	}

	//make an arraylist of tests that are in queue
	public static ArrayList<TestQueue> getAllProcessingTest(String userid)throws SQLException{
		String query = String.format("SELECT q.userid, q.testid,t.name, t.type,q.run, q.status\n" +
				"FROM queue q\n" +
				"INNER JOIN test t on q.testid = t.testid\n" +
				"WHERE q.userid = '%s'",userid);
		PreparedStatement preparedStatement = databaseConnection.prepareStatement(query);
		ResultSet rs = preparedStatement.executeQuery();


		ArrayList<TestQueue> testQ = new ArrayList<TestQueue>();

		while(rs.next()){
			TestQueue q = new TestQueue(
					getValueOREmpty(rs.getString("userid")),
					getValueOREmpty(rs.getString ("testid")),
					getValueOREmpty(rs.getString ("name")),
					getValueOREmpty(rs.getString ("type")),
					getValueOREmpty(rs.getString ("run")),
					getValueOREmpty(rs.getString ("status"))
			);

			testQ.add(q);
		}
		preparedStatement.close();

		return testQ;
	}

	public static String getUsertestid (String userid, String testid, String run)throws SQLException{

		String usertestid = "";
		String getusertestid = String.format("SELECT usertestid FROM usertest WHERE userid ='%s' AND testid='%s' AND run = '%s'",userid,testid,run);

		PreparedStatement preparedStatement = databaseConnection.prepareStatement(getusertestid);
		ResultSet rs = preparedStatement.executeQuery();

		while(rs.next()) {
			usertestid = getValueOREmpty(rs.getString("usertestid"));
		}
		return usertestid;
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

    //get run count for newly uploaded file
    public static String getRun (String userid,String testid)throws SQLException{
	    String run = "";
		Integer userrun = 1;
		String queryUsertest = String.format("SELECT count(testid)AS run FROM usertest WHERE userid = '%s' AND testid = '%s'", userid, testid);
		PreparedStatement preparedStatementUT = databaseConnection.prepareStatement(queryUsertest);
		ResultSet rs1 = preparedStatementUT.executeQuery();

		Integer ut = 0;
		while(rs1.next()) {
            ut = rs1.getInt("run") +1;
            System.out.println(ut);
		}

        String queryQueue = String.format("SELECT max(run) AS run FROM queue WHERE userid = '%s' AND testid = '%s'", userid, testid);
        PreparedStatement preparedStatementQ = databaseConnection.prepareStatement(queryQueue);
        ResultSet rs = preparedStatementQ.executeQuery();

        Integer q = 0;
        while(rs.next()) {
            q = rs.getInt("run") +1 ;
            System.out.println(q);
        }

		Integer maxrun = Math.max(userrun,Math.max(ut,q));

        run = maxrun.toString();

		return run;
	}


	private static Mutation getMutation(ResultSet row) throws SQLException{
		Mutation mutation = new Mutation(
				row.getInt("usertestid"),
                getValueOREmpty(row.getString("chr")),
                row.getInt("pos"),
                getValueOREmpty(row.getString("ref")),
                getValueOREmpty(row.getString("alt")),
				getValueOREmpty(row.getString("freqid")),
                getValueOREmpty(row.getString("globalFreq")),
                getValueOREmpty(row.getString("americanFreq")),
                getValueOREmpty(row.getString("asianFreq")),
                getValueOREmpty(row.getString("afrFreq")),
                getValueOREmpty(row.getString("eurFreq")),
                getValueOREmpty(row.getString("cancerid")),
                getValueOREmpty(row.getString("cancerCount")),
                getValueOREmpty(row.getString("clinicalid")),
                getValueOREmpty(row.getString("clinicalDisease")),
                getValueOREmpty(row.getString("signficance")),
                getValueOREmpty(row.getString("gene")),
                getValueOREmpty(row.getString("biologyDisease")),
                getValueOREmpty(row.getString("publicationCount")),
				getValueOREmpty(row.getString("comment"))
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
		}
		else{
            value= value.replaceAll("\\[", "").replaceAll("\\]","").replaceAll("'","");
        }
		return value;
	}

	//length of chr for overviewFrame
	public static Double[] lengthOfChr () throws Exception{
		Double lengthOfChr[] = new Double[24];

		String query = "select length from lengthOfChr ORDER BY chr;";

		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		int chr =0;

		while(rs.next()){
			lengthOfChr[chr]= (rs.getDouble(1)*100/3095677412.0);
			chr = chr+1;
		}

		return lengthOfChr;
	}

	//reports available
	public static ArrayList<String> getReportOptions() throws Exception{

		ArrayList<String> report = new ArrayList<String>();

		//Check reports name
		String query = "SELECT name FROM report ;" ;
		PreparedStatement pstm = databaseConnection.prepareStatement(query);
		ResultSet rs = pstm.executeQuery();

		while(rs.next()){
			report.add(rs.getString(1));
		}

		return report;
	}

	//reportlog insert from python
//	public static void InsertIntoReportLog(){
//
//	}
}
