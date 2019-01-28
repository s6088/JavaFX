package sample;


import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class DriversController implements Initializable {


    @FXML
    private TextField identity;

    @FXML
    private TextField name;

    @FXML
    private JFXDatePicker joiningDate;

    @FXML
    private JFXDatePicker licenseExpire;

    @FXML
    private JFXComboBox <String> licenseCategory;

    @FXML
    private TextField phone;

    @FXML
    private JFXComboBox <String> vehicleType;

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
    private JFXComboBox [] jfxComboBoxes;

    private final String [] columnNames = new String [] {"Id", "Name", "JoiningDate", "LicenseExpire", "LicenseCategory", "Phone", "VehicleType"};
    private final String components = "ttDDCtC";
    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields      = new TextField[]       {identity, name, phone};
        jfxDatePickers  = new JFXDatePicker[]   {joiningDate, licenseExpire};
        jfxComboBoxes   = new JFXComboBox[]     {licenseCategory, vehicleType};
        licenseCategory.setItems(FXCollections.observableArrayList(new String [] {"low", "medium", "high"}));

        try {
            gazi = new Gazi(textFields, jfxDatePickers, null, jfxComboBoxes, null, null,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "0011111","ssDDsss",false, null, null, Driver.class);
            vehicleType.setItems(GaziDao.getAllByColumnName(VehicleType.class, "vehicleType", "id"));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}

