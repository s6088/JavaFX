package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable
public class Allowance {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private double driverAllowance;

    @DatabaseField
    private double helperAllowance;

    @DatabaseField
    private double driverNet;

    @DatabaseField
    private double helperNet;

    @DatabaseField
    private String father;

    public Allowance (){
    }

    public Allowance (String father){
        this.father = father;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDriverAllowance() {
        return driverAllowance;
    }

    public void setDriverAllowance(double driverAllowance) {
        this.driverAllowance = driverAllowance;
    }

    public double getHelperAllowance() {
        return helperAllowance;
    }

    public void setHelperAllowance(double helperAllowance) {
        this.helperAllowance = helperAllowance;
    }

    public double getDriverNet() {
        return driverNet;
    }

    public void setDriverNet(double driverNet) {
        this.driverNet = driverNet;
    }

    public double getHelperNet() {
        return helperNet;
    }

    public void setHelperNet(double helperNet) {
        this.helperNet = helperNet;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
