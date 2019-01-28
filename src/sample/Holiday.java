package sample;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.joda.time.DateTime;

import java.util.Date;

@DatabaseTable
public class Holiday {

    @DatabaseField(id = true)
    String id;

    public Holiday() {
    }

    public Holiday(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
