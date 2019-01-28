package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.Interval;

import java.sql.Date;


@DatabaseTable
public class Overtime {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Date overtimeStarts;

    @DatabaseField
    private double duration;

    @DatabaseField
    private double driverAmmount;

    @DatabaseField
    private double helperAmmount;

    @DatabaseField
    private String father;

    private Overtime(){}

    public Overtime (String father){
        this.father = father;
    }


}
