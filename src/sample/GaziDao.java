package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;

public class GaziDao {

    public static ObservableList getAllByColumnName (Class name, String tableName, String columnName) throws SQLException {
        Dao dao = DaoManager.createDao(CONNECTION_SOURCE, name);
        GenericRawResults<String[]> rawResults = dao.queryRaw("SELECT "+ columnName +" FROM "+ tableName);
        List <String[]> results = rawResults.getResults();
        return FXCollections.observableArrayList(aatoa(results, 0));
    }


    public static String []  aatoa(List<String[]> tmp, int k){
        String [] data = new String[tmp.size()];
        for(int i=0; i<tmp.size(); i++)data[i] = tmp.get(i)[k];
        return data;
    }




    public ObservableList<String> getOneColumn (Class c, String string) throws SQLException {
        Dao tmp = DaoManager.createDao(CONNECTION_SOURCE, c);
        if(tmp == null)return null;
        GenericRawResults<String[]> rawResults = tmp.queryRaw(string);
        List<String[]> results = rawResults.getResults();
        ObservableList <String> temp = FXCollections.observableArrayList();
        for(int i=0; i<results.size(); i++)temp.add(results.get(i)[0]);
        return temp;
    };




    public static void addClass (Class class1, Object object1) throws SQLException {
        Dao dao = DaoManager.createDao(CONNECTION_SOURCE, class1);
        dao.create(object1);
    }

    public static Object getById (Class name, String columnName, String value) throws SQLException {
        Dao dao  = DaoManager.createDao(CONNECTION_SOURCE, name);
        QueryBuilder queryBuilder = dao.queryBuilder();
        queryBuilder.where().eq(columnName, value);
        PreparedQuery preparedQuery = queryBuilder.prepare();
        List<Object> result = dao.query(preparedQuery);
        return result==null ? null : result.get(0);
    }


    public static void deleteByColumn (Class name,  String columnName, String columnValue) throws SQLException {
        Dao dao = DaoManager.createDao(CONNECTION_SOURCE, name);
        DeleteBuilder deleteBuilder = dao.deleteBuilder();
        deleteBuilder.where().eq(columnName, columnValue);
        dao.delete(deleteBuilder.prepare());
    }




}
