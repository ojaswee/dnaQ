package dnaQ.Models;

public class Report {

    public Integer reportID;
    public String report;

    public String userID;
    public String username;

    public Report(String userID, String username, String report) {
        // TODO Auto-generated constructor stub
        this.userID = userID;
        this.username = username;
        this.report = report;
    }


    public Integer getReportID() {
        return reportID;
    }

    public void setReportID(Integer reportID) {
        this.reportID = reportID;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

   public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

}
