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
public class Limit {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String materialName;

    @DatabaseField
    private double maxperKm;

    @DatabaseField
    private boolean taka;

    @DatabaseField
    private String father;


    public Limit() {
    }

    public Limit (ObservableList<String> data) throws ParseException {
        setId(data.get(0));
        setMaterialName(data.get(1));
        if(isDouble(data.get(2)))setMaxperKm(Double.parseDouble(data.get(2)));
        if(data.get(3).compareTo("true")!=0)setTaka(false);
        else setTaka(true);
        setFather(data.get(4));
    }

    public static ObservableList <String> transform (Limit limit){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(limit.getId());
        data.add(limit.getMaterialName());
        data.add(limit.getMaxperKm() + "");
        data.add(limit.getTaka() ? "true" : "false");
        data.add(limit.getFather());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Limit> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public static void tableInitByFather (ObservableList<ObservableList <String>> data, String vehicleType) throws SQLException {
        Dao<Limit, String> LimitDao = DaoManager.createDao(CONNECTION_SOURCE, Limit.class);
        QueryBuilder <Limit, String> queryBuilder = LimitDao.queryBuilder();
        queryBuilder.where().eq("father", vehicleType);
        PreparedQuery <Limit> preparedQuery = queryBuilder.prepare();
        List<Limit> results = LimitDao.query(preparedQuery);
        data.clear();
        for(int i=0; i<results.size(); i++)
            data.add(Limit.transform(results.get(i)));
    }


    public static Limit toClass (ObservableList<String> data) throws ParseException {
        Limit limit = new Limit(data);
        return limit;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public double getMaxperKm() {
        return maxperKm;
    }

    public void setMaxperKm(double maxperKm) {
        this.maxperKm = maxperKm;
    }

    public boolean getTaka() {
        return taka;
    }

    public void setTaka(boolean taka) {
        this.taka = taka;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public static List<Limit> getById (String id) throws SQLException {
        Dao<Limit, String> LimitDao = DaoManager.createDao(CONNECTION_SOURCE, Limit.class);
        QueryBuilder<Limit, String> queryBuilder = LimitDao.queryBuilder();
        queryBuilder.where().eq("father", id);
        PreparedQuery<Limit> preparedQuery = queryBuilder.prepare();
        List<Limit> results = LimitDao.query(preparedQuery);
        return results == null ? null : results;
    }
}
