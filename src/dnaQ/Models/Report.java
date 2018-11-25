package dnaQ.Models;

public class Report {

    public String report;

    public Report(String userID, String username, String report) {

        this.report = report;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

}
