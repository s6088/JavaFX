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

@DatabaseTable
public class Price {

    //material name
    @DatabaseField(id = true)
    private String id;

    @DatabaseField(canBeNull = false)
    private double unitPrice;

    public Price() {
    }

    public Price (ObservableList<String> data) throws ParseException {
        setId(data.get(0));
        setUnitPrice(Double.parseDouble(data.get(1)));
    }

    public static Price toClass (ObservableList <String> tmp) throws ParseException {
        Price price = new Price(tmp);
        return price;
    }

    public static ObservableList <String> transform (Price price){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(price.getId());
        data.add(price.getUnitPrice() + "");
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Price> data){
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

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public static Price getById (String id) throws SQLException {
        Dao<Price , String> priceDao   = DaoManager.createDao(CONNECTION_SOURCE, Price.class);
        QueryBuilder<Price, String> queryBuilder = priceDao.queryBuilder();
        queryBuilder.where().eq("id", id);
        PreparedQuery<Price> preparedQuery = queryBuilder.prepare();
        List<Price> result = priceDao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }
}
