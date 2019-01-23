package dnaQ.Models;

public class TestQueue {

    public String testid;
    public String testname;
    public String type;

    public TestQueue() {
            super();
        }
    public TestQueue (String testid,String testname,String type) {
        super();
        this.testid = testid;
        this.testname = testname;
        this.type = type;
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
}
