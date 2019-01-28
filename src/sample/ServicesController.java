package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ServicesController implements Initializable {

    @FXML
    private TextField identity;

    @FXML
    private JFXComboBox<String> serviceName;

    @FXML
    private TextField expiredKm;

    @FXML
    private TextField serviceCharge;

    @FXML
    private TextField vehicleId;

    @FXML
    private TextField workshop;

    @FXML
    private JFXDatePicker expiredDate;

    @FXML
    private JFXDatePicker servicingDate;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton clear;

    @FXML
    public TableView tableView;



    private TextField [] textFields;
    private JFXDatePicker [] jfxDatePickers;

    private final String [] columnNames = new String [] {"Id", "serviceName",  "servicingDate", "vehicleId", "workshop" , "serviceCharge", "expiredKm", "expiredDate"};
    private final String components = "tCDttttD";

    private Gazi gazi;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields = new TextField[]{identity, vehicleId, workshop, serviceCharge, expiredKm};
        jfxDatePickers = new JFXDatePicker[] {servicingDate, expiredDate};

        try {
            gazi = new Gazi(textFields, jfxDatePickers, null, new JFXComboBox[]{serviceName}, null, null,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "00000011","sDsssddD",true, null, null, Service.class);
            serviceName.setItems(GaziDao.getAllByColumnName(ServiceName .class, "serviceName",  "id"));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
         }


/*
        serviceName.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if(ServiceNameDao.queryBuilder().where().eq("id", serviceName.getText().trim()).countOf() > 0) {
                    String by = expireOn();
                        if(by.compareTo("km")==0)expiredDate.setDisable(true);
                        else if(by.compareTo("date")==0)expiredKm.setDisable(true);
                        else {
                        expiredDate.setDisable(false);
                        expiredKm.setDisable(false);
                        }
                }
                else {
                    expiredDate.setDisable(false);
                    expiredKm.setDisable(false);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


        public void autoCompleteInitilazation () throws SQLException {
        GenericRawResults<String[]> rawResults = VehicleDao.queryRaw("SELECT id FROM vehicle");
        List<String[]> results = rawResults.getResults();
        if(results.size() > 0) {
            for(int i=0; i<results.size(); i++)myvehicles.add(results.get(i)[0]);
            java.util.Collections.sort(myvehicles);
            TextFields.bindAutoCompletion(vehicleId, myvehicles);
        }
        rawResults = ServiceNameDao.queryRaw("SELECT id FROM serviceName");
        results = rawResults.getResults();
        if(results.size() > 0) {
            for(int i=0; i<results.size(); i++)myserviceNames.add(results.get(i)[0]);
            java.util.Collections.sort(myserviceNames);
            TextFields.bindAutoCompletion(serviceName, myserviceNames);
        }
    }



    public boolean isValid (boolean is) throws SQLException {

        if(servicingDate.getValue() == null){
            makeAlert("Serviceing Date can't be empty");
            return false;
        }
        else if(serviceName.getText().trim().isEmpty()) {
            makeAlert("Service Name can't be empty");
            return false;
        }
        else if(ServiceNameDao.queryBuilder().where().eq("id", serviceName.getText().trim()).countOf() <= 0) {
            makeAlert("No such service name found in record!");
            return false;
        }
        else if(servicingDate.getValue() == null){
            makeAlert("Servicing date field can't be empty");
            return false;
        }
        else if(vehicleId.getText().trim().isEmpty()) {
            makeAlert("vehicle id can't be empty");
            return false;
        }
        else if(VehicleDao.queryBuilder().where().eq("id", vehicleId.getText().trim()).countOf() <= 0) {
            makeAlert("No such vehicle  found in record!");
            return false;
        }
        else if(workshop.getText().trim().isEmpty()){
            makeAlert("work shop can't be empty");
            return false;
        }
        else if(!isDouble(serviceCharge.getText().trim())){
            makeAlert("problem with out service charge");
            return false;
        }

        String by = expireOn();
        expiredKm.setDisable(false);
        if(by.compareTo("km")==0){
            if(!isDouble(expiredKm.getText().trim())){
                makeAlert("problem with expired km!");
                return false;
            }
        }
        else if(by.compareTo("date")==0){
            if(expiredDate.getValue() == null){
                makeAlert("problem with expired date!");
                return false;
            }
        }
        else {
            if(!isDouble(expiredKm.getText().trim())){
                makeAlert("problem with expired km!");
                return false;
            }
            if(expiredDate.getValue() == null){
                makeAlert("problem with expired date!");
                return false;
            }
        }
        return true;
    }


    private String expireOn () throws SQLException {
        GenericRawResults<String[]> rawResults = ServiceNameDao.queryRaw(
                "SELECT expiredOn FROM servicename WHERE id='"
                        + serviceName.getText().trim() +"'");
        return rawResults.getResults().get(0)[0];
    }




    public boolean addFromservice (){
        try {
            if(!isValid(false))return false;
            Service service = new Service( MyObject.getPanel(textFields, jfxDatePickers, jfxTimePickers, components) );
            ServiceDao.create(service);
            data.add(service.transform(service));
        } catch (SQLException | ParseException e1) {
            makeAlert("an unexpected error occur");
            e1.printStackTrace();
        }
        return true;
    }

*/







}

