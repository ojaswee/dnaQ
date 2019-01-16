package dnaQ.Models;

public class Test {
    private String testID;
    private String testname;
    private String type;
    private String cost;

    public Test() {
        super();
    }

    public Test (String testID,String testname,String type,String cost) {
        super();
        this.testID = testID;
        this.testname = testname;
        this.type = type;
        this.cost = cost;
    }

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
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

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }
}
