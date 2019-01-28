package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.*;


@DatabaseTable
public class Vehicle {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String vehicleType;

    @DatabaseField
    private String driver;

    @DatabaseField
    private String helper;

    @DatabaseField
    private String bodyMaker;

    @DatabaseField
    private String engineNo;

    @DatabaseField
    private Date bodymakingDate;

    @DatabaseField
    private Date purchaseDate;

    @DatabaseField
    private double price;

    @DatabaseField
    private Date registrationDate;

    @DatabaseField
    private String registrationNo;

    @DatabaseField
    private String user;

    @DatabaseField(canBeNull = false)
    private Double kmranSofar;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
    }

    public Vehicle() {
    }

    public Vehicle (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        setVehicleType(data.get(1));
        setDriver(data.get(2));
        setHelper(data.get(3));
        setBodyMaker(data.get(4));
        setEngineNo(data.get(4));
        if(data.get(6) != null)setBodymakingDate(new Date( simpleDateFormat.parse(data.get(6)).getTime() ));
        if(data.get(7) != null)setPurchaseDate(new Date( simpleDateFormat.parse(data.get(7)).getTime() ));
        if(data.get(8) != null)setPrice(Double.valueOf(data.get(8)));
        if(data.get(9) != null)setRegistrationDate(new Date( simpleDateFormat.parse(data.get(9)).getTime() ));
        setRegistrationNo(data.get(10));
        setUser(data.get(11));
        if(data.get(12) != null)setKmranSofar(Double.parseDouble(data.get(12)));
    }

    public static ObservableList <String> transform (Vehicle vehicle){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(vehicle.getId());
        data.add(vehicle.getVehicleType()==null ? null : vehicle.getVehicleType());
        data.add(vehicle.getDriver()==null ? null : vehicle.getDriver());
        data.add(vehicle.getHelper()==null ? null : vehicle.getHelper());
        data.add(vehicle.getBodyMaker()==null ? null : vehicle.getBodyMaker());
        data.add(vehicle.getEngineNo()==null ? null : vehicle.getEngineNo());
        if(vehicle.getBodymakingDate() != null)data.add(vehicle.getBodymakingDate().toString());
        else data.add(null);
        if(vehicle.getPurchaseDate() != null)data.add(vehicle.getPurchaseDate().toString());
        else data.add(null);
        if(isDouble(vehicle.getPrice()+"")) data.add(vehicle.getPrice() + "");
        else data.add(null);
        if(vehicle.getRegistrationDate() != null)data.add(vehicle.getRegistrationDate().toString());
        else data.add(null);
        data.add(vehicle.getRegistrationNo()==null ? null : vehicle.getRegistrationNo());
        data.add(vehicle.getUser()==null ? null : vehicle.getUser());
        if(isDouble(vehicle.getKmranSofar()+"")) data.add(vehicle.getKmranSofar() + "");
        else data.add(null);
        return data;
    }

    public static Vehicle toClass (ObservableList<String> data) throws ParseException {
        Vehicle vehicle = new Vehicle(data);
        return vehicle;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Vehicle> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public Date getBodymakingDate() {
        return bodymakingDate;
    }

    public void setBodymakingDate(Date bodymakingDate) {
        this.bodymakingDate = bodymakingDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBodyMaker() {
        return bodyMaker;
    }

    public void setBodyMaker(String bodyMaker) {
        this.bodyMaker = bodyMaker;
    }

    public String getEngineNo() {
        return engineNo;
    }

    public void setEngineNo(String engineNo) {
        this.engineNo = engineNo;
    }


    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Double getKmranSofar() {
        return kmranSofar;
    }

    public void setKmranSofar(Double kmranSofar) {
        this.kmranSofar = kmranSofar;
    }

    public static Vehicle getById (String id) throws SQLException {
        Dao<Vehicle , String> VehicleDao   = DaoManager.createDao(CONNECTION_SOURCE, Vehicle.class);
        QueryBuilder<Vehicle, String> queryBuilder = VehicleDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Vehicle> preparedQuery = queryBuilder.prepare();
        List<Vehicle> result = VehicleDao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }
}
