package dnaQ.Models;

public class Test {
    public String testid;
    public String testname;
    public String type;
    public String testrun;
    public String usertestid;
    public String status;

    public Test() {
        super();
    }

    public Test (String testid,String testname,String type,String testrun,String usertestid,String status) {
        super();
        this.testid = testid;
        this.testname = testname;
        this.type = type;
        this.testrun = testrun;
        this.usertestid = usertestid;
        this.status = status;
    }


    public String getTestid() {
        return testid;
    }

    public void setTestid(String testid) {
        this.testid = testid;
    }

    public String getTestname() {
        return testname;
    }

    public void setTestname(String testname) {
        this.testname = testname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTestrun() {
        return testrun;
    }

    public void setTestrun(String testrun) {
        this.testrun = testrun;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUsertestid() {
        return usertestid;
    }

    public void setUsertestid(String usertestid) {
        this.usertestid = usertestid;
    }


}
