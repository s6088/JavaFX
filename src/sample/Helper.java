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
public class Helper {

    @DatabaseField(id = true, canBeNull = false)
    private String id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField
    private Date joiningDate;

    @DatabaseField
    private String phone;

    @DatabaseField
    private String vehicleType;


    public Helper (){

    }

   public Helper (ObservableList <String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        setName(data.get(1));
        if(data.get(2) != null)setJoiningDate(new Date( simpleDateFormat.parse(data.get(2)).getTime() ));
        setPhone(data.get(3));
        setVehicleType(data.get(4));
    }

    public static Helper toClass (ObservableList<String> data) throws ParseException {
        Helper helper = new Helper(data);
        return helper;
    }


    public static ObservableList <String> transform (Helper helper){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(helper.getId());
        data.add(helper.getName());
        if(helper.getJoiningDate() != null)data.add(helper.getJoiningDate().toString());
        else data.add(null);
        data.add(helper.getPhone());
        data.add(helper.getVehicleType());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Helper> data){
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

    public static Helper getById (String id) throws SQLException {
        Dao<Helper , String> HelperDao   = DaoManager.createDao(CONNECTION_SOURCE, Helper.class);
        QueryBuilder<Helper, String> queryBuilder = HelperDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Helper> preparedQuery = queryBuilder.prepare();
        List<Helper> result = HelperDao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }

    @Override
    public String toString() {
        return "Helper{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", joiningDate=" + joiningDate +
                ", phone='" + phone + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                '}';
    }
}

