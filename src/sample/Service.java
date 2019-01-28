package sample;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static sample.UI.*;

@DatabaseTable
public class Service {

    @DatabaseField(id = true)
    private String id;

    @DatabaseField
    private Date servicingDate;

    @DatabaseField
    private String serviceName;

    @DatabaseField
    private String vehicleId;

    @DatabaseField
    private String workshop;

    @DatabaseField
    private String serviceCharge;

    public String getServiceCharge() {
        return serviceCharge;
    }

    public void setServiceCharge(String serviceCharge) {
        this.serviceCharge = serviceCharge;
    }

    @DatabaseField
    private double expiredKm;


    @DatabaseField
    private Date expiredDate;


    public Service() {
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getId() {
        return id;
    }

    public Service (ObservableList<String> data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-d");
        setId(data.get(0));
        if(data.get(1) != null)setServicingDate(new Date( simpleDateFormat.parse(data.get(1)).getTime() ));
        setServiceName(data.get(2));
        setVehicleId(data.get(3));
        setWorkshop(data.get(4));
        setServiceCharge(data.get(5));
        if(isDouble(data.get(6)))setExpiredKm(Double.parseDouble(data.get(6)));
        if(data.get(7) != null)setExpiredDate(new Date( simpleDateFormat.parse(data.get(7)).getTime() ));
    }

    public static ObservableList <String> transform (Service service){
        ObservableList <String> data = FXCollections.observableArrayList();
        data.add(service.getId());
        data.add(service.getServicingDate().toString());
        data.add(service.getServiceName());
        data.add(service.getVehicleId());
        data.add(service.getWorkshop());
        data.add(service.getServiceCharge());
        data.add(service.getExpiredKm() + "");
        if(service.getExpiredDate() != null)data.add(service.getExpiredDate().toString());
        else data.add(null);
        return data;
    }

    public static ObservableList <ObservableList <String>> transformList (ObservableList <Service> data){
        ObservableList <ObservableList <String>> myData = FXCollections.observableArrayList();
        for(int i=0; i<data.size(); i++)myData.add(transform(data.get(i)));
        return myData;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getServicingDate() {
        return servicingDate;
    }

    public void setServicingDate(Date servicingDate) {
        this.servicingDate = servicingDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public double getExpiredKm() {
        return expiredKm;
    }

    public void setExpiredKm(double expiredKm) {
        this.expiredKm = expiredKm;
    }
}
