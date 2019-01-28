package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.ParseException;

@DatabaseTable
public class HourRate {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Double hours;

    @DatabaseField
    private Double driverRate;


    @DatabaseField
    private Double helperRate;

    public HourRate() {
    }


    public HourRate (ObservableList<String> data) throws ParseException {
        id =    data.get(0);
        hours = Double.parseDouble(data.get(1));
        driverRate  = Double.parseDouble(data.get(2));
        helperRate  = Double.parseDouble(data.get(3));
    }

    public static HourRate toClass (ObservableList<String> data) throws ParseException {
        HourRate hourRate =new HourRate(data);
        return hourRate;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getHours() {
        return hours;
    }

    public void setHours(Double hours) {
        this.hours = hours;
    }


    public Double getDriverRate() {
        return driverRate;
    }

    public void setDriverRate(Double driverRate) {
        this.driverRate = driverRate;
    }

    public Double getHelperRate() {
        return helperRate;
    }

    public void setHelperRate(Double helperRate) {
        this.helperRate = helperRate;
    }

    public static ObservableList <String> transform (HourRate hourRate){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(hourRate.getId());
        data.add(hourRate.getHours() + "");
        data.add(hourRate.driverRate + "");
        data.add(hourRate.helperRate + "");
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <HourRate> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }


}

