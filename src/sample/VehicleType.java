package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.isDouble;


@DatabaseTable
public class VehicleType {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private  double driverSalary;

    @DatabaseField
    private  double helperSalary;

    @DatabaseField
    private  double driverLunchCost;

    @DatabaseField
    private  double helperLunchCost;

    @DatabaseField
    private  double driverDinnerCost;

    @DatabaseField
    private  double helperDinnerCost;

    @DatabaseField
    private  double driverAllowance;

    @DatabaseField
    private  double helperAllowance;

    public VehicleType() {
    }

    public static VehicleType toClass (ObservableList<String> data) throws ParseException {
        VehicleType vehicleType = new VehicleType(data);
        return vehicleType;
    }

    public VehicleType (ObservableList<String> data) throws ParseException {
        setId(data.get(0));
        setDriverSalary(Double.parseDouble(data.get(1)));
        setDriverLunchCost(Double.parseDouble(data.get(2)));
        setDriverDinnerCost(Double.parseDouble(data.get(3)));
        setDriverAllowance(Double.parseDouble(data.get(4)));
        if(data.get(5)!=null && isDouble(data.get(5)))setHelperSalary(Double.parseDouble(data.get(5)));
        else setHelperSalary(0);
        if(data.get(6)!=null && isDouble(data.get(6)))setHelperLunchCost(Double.parseDouble(data.get(6)));
        else setHelperLunchCost(0);
        if(data.get(7)!=null && isDouble(data.get(7)))setHelperDinnerCost(Double.parseDouble(data.get(7)));
        else setHelperDinnerCost(0);
        if(data.get(8)!=null && isDouble(data.get(8)))setHelperAllowance(Double.parseDouble(data.get(8)));
        else setHelperAllowance(0);
    }

    public static ObservableList <String> transform (VehicleType vehicleType){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(vehicleType.getId());
        data.add(vehicleType.getDriverSalary() + "");
        data.add(vehicleType.getDriverLunchCost() + "");
        data.add(vehicleType.getDriverDinnerCost() + "");
        data.add(vehicleType.getDriverAllowance() + "");
        if(isDouble(vehicleType.getHelperSalary() + ""))data.add(vehicleType.getHelperSalary() + "");
        else data.add(null);
        if(isDouble(vehicleType.getHelperLunchCost() + ""))data.add(vehicleType.getHelperLunchCost() + "");
        else data.add(null);
        if(isDouble(vehicleType.getHelperDinnerCost() + ""))data.add(vehicleType.getHelperDinnerCost() + "");
        else data.add(null);
        if(isDouble(vehicleType.getHelperAllowance() + ""))data.add(vehicleType.getHelperAllowance() + "");
        else data.add(null);
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <VehicleType> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getDriverSalary() {
        return driverSalary;
    }

    public void setDriverSalary(double driverSalary) {
        this.driverSalary = driverSalary;
    }

    public double getHelperSalary() {
        return helperSalary;
    }

    public void setHelperSalary(double helperSalary) {
        this.helperSalary = helperSalary;
    }

    public double getDriverLunchCost() {
        return driverLunchCost;
    }

    public void setDriverLunchCost(double driverLunchCost) {
        this.driverLunchCost = driverLunchCost;
    }

    public double getHelperLunchCost() {
        return helperLunchCost;
    }

    public void setHelperLunchCost(double helperLunchCost) {
        this.helperLunchCost = helperLunchCost;
    }

    public double getDriverDinnerCost() {
        return driverDinnerCost;
    }

    public void setDriverDinnerCost(double driverDinnerCost) {
        this.driverDinnerCost = driverDinnerCost;
    }

    public double getHelperDinnerCost() {
        return helperDinnerCost;
    }

    public void setHelperDinnerCost(double helperDinnerCost) {
        this.helperDinnerCost = helperDinnerCost;
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

    public static VehicleType getById (String id) throws SQLException {
        Dao<VehicleType , String> VehicleTypeDao   = DaoManager.createDao(CONNECTION_SOURCE, VehicleType.class);
        QueryBuilder<VehicleType, String> queryBuilder = VehicleTypeDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<VehicleType> preparedQuery = queryBuilder.prepare();
        List<VehicleType> result = VehicleTypeDao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }

}
