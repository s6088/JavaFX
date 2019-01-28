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
public class Post {

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


    public Post() {
    }



    public Post (ObservableList<String> data) throws ParseException {
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

    public static ObservableList <String> transform (Post post){
        ObservableList <String> data = FXCollections.observableArrayList();
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-d");
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("H:mm");
        data.add(post.getId());
        data.add(simpleDateFormat1.format(post.getCreatedAt()));
        data.add(simpleDateFormat1.format(post.getDepartTime()));
        data.add(simpleDateFormat2.format(post.getDepartTime()));
        data.add(simpleDateFormat1.format(post.getArriveTime()));
        data.add(simpleDateFormat2.format(post.getArriveTime()));
        data.add(post.getVehicle());
        data.add(post.getDriver());
        data.add(post.getHelper());
        data.add(post.getStartingKm() + "");
        data.add(post.getEndingKm() + "");
        data.add(post.getUser());
        data.add(post.getFrom());
        data.add(post.getTo());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Post> data){
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

    public static Post toClass (ObservableList<String> data) throws ParseException {
        Post post = new Post(data);
        return post;
    }
    public static Post getById (String id) throws SQLException {
        Dao<Post , String> PostDao   = DaoManager.createDao(CONNECTION_SOURCE, Post.class);
        QueryBuilder<Post, String> queryBuilder = PostDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Post> preparedQuery = queryBuilder.prepare();
        List<Post> result = PostDao.query(preparedQuery);
        return result.get(0);
    }


}
