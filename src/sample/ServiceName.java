package sample;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

@DatabaseTable
public class ServiceName {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private String expiredOn;

    @DatabaseField
    private String description;


    public ServiceName() {
    }

    public static ServiceName toClass (ObservableList<String> data){
        ServiceName serviceName = new ServiceName();
        serviceName.setId(data.get(0));
        serviceName.setExpiredOn(data.get(1));
        serviceName.setDescription(data.get(2));
        return serviceName;
    }

    public static ObservableList <String> transform (ServiceName serviceName){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(serviceName.getId());
        data.add(serviceName.getExpiredOn());
        data.add(serviceName.getDescription());
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <ServiceName> data){
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

    public String getExpiredOn() {
        return expiredOn;
    }

    public void setExpiredOn(String expiredOn) {
        this.expiredOn = expiredOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
