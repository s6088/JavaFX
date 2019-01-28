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

@DatabaseTable
public class Driver {


    @DatabaseField(id = true)
    private String id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField
    private Date joiningDate;

    @DatabaseField
    private String licenseCategory;

    @DatabaseField
    private Date licenseExpire;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String vehicleType;

    public Driver() {
    }

    public Driver (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        setName(data.get(1));
        if(data.get(2) != null)setJoiningDate(new Date( simpleDateFormat.parse(data.get(2)).getTime() ));
        if(data.get(3) != null)setLicenseExpire(new Date( simpleDateFormat.parse(data.get(3)).getTime() ));
        if(data.get(4) != null)setLicenseCategory(data.get(4));
        if(data.get(5) != null)setPhone(data.get(5));
        if(data.get(6) != null)setVehicleType(data.get(6));
    }

    public static Driver toClass (ObservableList<String> data) throws ParseException {
        Driver driver = new Driver(data);
        return driver;
    }

    public static ObservableList <String> transform (Driver driver){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(driver.getId());
        data.add(driver.getName());
        if(driver.getJoiningDate() != null)data.add(driver.getJoiningDate().toString());
        else data.add(null);
        if(driver.getLicenseExpire() != null)data.add(driver.getLicenseExpire().toString());
        else data.add(null);
        if(driver.getLicenseCategory() != null)data.add(driver.getLicenseCategory());
        else data.add(null);
        if (driver.getPhone() == null) data.add(null);
        else data.add(driver.getPhone());
        if (driver.getVehicleType() == null) data.add(null);
        else data.add(driver.getVehicleType());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Driver> data){
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLicenseCategory() {
        return licenseCategory;
    }

    public void setLicenseCategory(String licenseCategory) {
        this.licenseCategory = licenseCategory;
    }

    public Date getLicenseExpire() {
        return licenseExpire;
    }

    public void setLicenseExpire(Date licenseExpire) {
        this.licenseExpire = licenseExpire;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
    
    public static Driver getById (String id) throws SQLException {
        Dao<Driver , String> driverDao   = DaoManager.createDao(CONNECTION_SOURCE, Driver.class);
        QueryBuilder<Driver, String> queryBuilder = driverDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Driver> preparedQuery = queryBuilder.prepare();
        List<Driver> result = driverDao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }
    

}
