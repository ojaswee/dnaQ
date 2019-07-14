package dnaQ.Models;

public class TestQueue {

    public String userid;
    public String testid;
    public String testname;
    public String testtype;
    public String run;
    public String status;


    public TestQueue() {
            super();
        }

    public TestQueue (String userid, String testid,String testname, String testtype, String run, String status) {
        super();
        this.userid = userid;
        this.testid = testid;
        this.testname = testname;
        this.testtype= testtype;
        this.run = run;
        this.status = status;

        }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getTesttype() {
        return testtype;
    }

    public void setTesttype(String testtype) {
        this.testtype = testtype;
    }

}
