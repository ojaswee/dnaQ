package dnaQ.Connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import dnaQ.Models.Sample;


public class DatabaseConnections {

    private static Connection databaseConnection = null;
	   
	public static void connect () throws Exception{
		
		   String driver = "com.mysql.cj.jdbc.Driver";
	       String dbuser ="root";
		   String dbpasswd="main";
		   String db="mydna";
		   String url="jdbc:mysql://localhost/"+db + "?useSSL=false";
		   
		   try {
		   //Class.forName(driver);
		   databaseConnection = DriverManager.getConnection(url, dbuser, dbpasswd);
		   }catch (Exception e) {
			   throw new Exception("mysql connection error "+ e.getMessage());
		   }
		   
	}
		 
	public static boolean connectLogin(String userName, String passwd) throws Exception{
		connect();
		boolean success = false;
		try {
			String query = "SELECT * FROM login where username ='"+userName+"'AND pword ='"+passwd +"';";
		    PreparedStatement pstm = databaseConnection.prepareStatement(query);
		    ResultSet rs = pstm.executeQuery();
		    
		    if(rs.next()){
				success=true;
	           }
		    }
		catch (Exception e) {
		   throw new Exception("mysql connection error "+ e.getMessage());
		   }
		return success;
    }
//
//	public static User getUserInfo(String userName) throws Exception{
//
//		User user = null;
//
//		String query = "SELECT username From login  where username='"+userName + "';";
//
//	    PreparedStatement pstm = databaseConnection.prepareStatement(query);
//	    ResultSet rs = pstm.executeQuery();
//
//	    if(rs.next()){
//	    	pstm.close();
//	    }
//	    return user;
//	}

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

	public static ArrayList<Sample> getAllSample() throws Exception{
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
