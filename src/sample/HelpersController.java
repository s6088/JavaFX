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


public class HelpersController implements Initializable {


    @FXML
    private TextField identity;

    @FXML
    private TextField name;

    @FXML
    private JFXDatePicker joiningDate;

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


    private final String [] columnNames = new String [] {"id", "name", "joiningDate", "phone", "vehicleType"};
    private final String components = "ttDtC";
    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields      = new TextField[]       {identity, name, phone};
        jfxDatePickers  = new JFXDatePicker[]   {joiningDate};
        jfxComboBoxes   = new JFXComboBox[]     {vehicleType};
        try {
            gazi = new Gazi(textFields, jfxDatePickers, null, jfxComboBoxes, null, null,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "00111","ssDss",false, null, null, Helper.class);
            vehicleType.setItems(GaziDao.getAllByColumnName(VehicleType.class, "vehicleType", "id"));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }


}

