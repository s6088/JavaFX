package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import static sample.Main.CONNECTION_SOURCE;

@DatabaseTable
public class Util {

    @DatabaseField(id = true)
    String id;

    @DatabaseField
    String value;

    public Util() {
    }

    public Util(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static HashMap getById() throws SQLException {
        HashMap <String, String> map = new HashMap<>();
        Dao<Util, String> UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);
        List<Util> list = UtilDao.queryForAll();
        for(int i=0; i<list.size(); i++)
                map.put(list.get(i).getId(), list.get(i).getValue());
        return map;
    }
}
