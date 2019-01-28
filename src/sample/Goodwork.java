package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.Interval;

import java.sql.Date;

@DatabaseTable
public class Goodwork {


    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private Date holiday;

    @DatabaseField
    private double driverAmmount;

    @DatabaseField
    private int hrs;

    @DatabaseField
    private double helperAmmount;

    @DatabaseField
    private String father;


    public Goodwork(){

    }

    public int getHrs() {
        return hrs;
    }

    public void setHrs(int hrs) {
        this.hrs = hrs;
    }

    public Goodwork(String father){
        this.father = father;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getHoliday() {
        return holiday;
    }

    public void setHoliday(Date holiday) {
        this.holiday = holiday;
    }

    public double getDriverAmmount() {
        return driverAmmount;
    }

    public void setDriverAmmount(double driverAmmount) {
        this.driverAmmount = driverAmmount;
    }

    public double getHelperAmmount() {
        return helperAmmount;
    }

    public void setHelperAmmount(double helperAmmount) {
        this.helperAmmount = helperAmmount;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
