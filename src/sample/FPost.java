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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;


@DatabaseTable
public class FPost {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Date createdAt;

    @DatabaseField
    private Date departTime;

    @DatabaseField
    private Date arriveTime;

    @DatabaseField
    private String vehicle;

    @DatabaseField
    private String driver;

    @DatabaseField
    private String helper;

    @DatabaseField
    private Double startingKm;

    @DatabaseField
    private Double endingKm;

    @DatabaseField
    private String user;

    @DatabaseField
    private String from;

    @DatabaseField
    private String to;


    public FPost() {
    }


    public FPost(Post post) {
        this.id = post.getId();
        this.createdAt = post.getCreatedAt();
        this.departTime = post.getDepartTime();
        this.arriveTime = post.getArriveTime();
        this.vehicle = post.getVehicle();
        this.driver = post.getDriver();
        this.helper = post.getHelper();
        this.startingKm = post.getStartingKm();
        this.endingKm = post.getEndingKm();
        this.user = post.getUser();
        this.from = post.getFrom();
        this.to = post.getTo();
    }

    public FPost (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d H:mm");
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        setCreatedAt(new java.sql.Date( simpleDateFormat1.parse(data.get(1)).getTime() ));
        setDepartTime(new java.sql.Date( simpleDateFormat.parse(data.get(2) + " " + data.get(3)).getTime() ));
        setArriveTime(new java.sql.Date( simpleDateFormat.parse(data.get(4) + " " + data.get(5)).getTime() ));
        setVehicle(data.get(6));
        setDriver(data.get(7));
        setHelper(data.get(8));
        setStartingKm(Double.parseDouble(data.get(9).trim()));
        setEndingKm(Double.parseDouble(data.get(10).trim()));
        setUser(data.get(11));
        setFrom(data.get(12));
        setTo(data.get(13));
    }

    public static ObservableList <String> transform (FPost fpost){
        ObservableList <String> data = FXCollections.observableArrayList();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-d");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("H:mm");
        data.add(fpost.getId());
        data.add(simpleDateFormat1.format(fpost.getCreatedAt()));
        data.add(simpleDateFormat1.format(fpost.getDepartTime()));
        data.add(simpleDateFormat2.format(fpost.getDepartTime()));
        data.add(simpleDateFormat1.format(fpost.getArriveTime()));
        data.add(simpleDateFormat2.format(fpost.getArriveTime()));
        data.add(fpost.getVehicle());
        data.add(fpost.getDriver());
        data.add(fpost.getHelper());
        data.add(fpost.getStartingKm() + "");
        data.add(fpost.getEndingKm() + "");
        data.add(fpost.getUser());
        data.add(fpost.getFrom());
        data.add(fpost.getTo());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <FPost> data){
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getDepartTime() {
        return departTime;
    }

    public void setDepartTime(Date departTime) {
        this.departTime = departTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getVehicle() {
        return vehicle;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

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

    public Double getStartingKm() {
        return startingKm;
    }

    public void setStartingKm(Double startingKm) {
        this.startingKm = startingKm;
    }

    public Double getEndingKm() {
        return endingKm;
    }

    public void setEndingKm(Double endingKm) {
        this.endingKm = endingKm;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public static FPost toClass (ObservableList<String> data) throws ParseException {
        FPost fpost = new FPost(data);
        return fpost;
    }
    public static FPost getById (String id) throws SQLException {
        Dao<FPost , String> fpostDao   = DaoManager.createDao(CONNECTION_SOURCE, FPost.class);
        QueryBuilder<FPost, String> queryBuilder = fpostDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<FPost> preparedQuery = queryBuilder.prepare();
        List<FPost> result = fpostDao.query(preparedQuery);
        return result.get(0);
    }



}
