package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.TableView;

public class VehiclesController implements Initializable {


    @FXML
    private TextField identity;

    @FXML
    private JFXComboBox driver;

    @FXML
    private JFXComboBox helper;
    
    @FXML
    private JFXComboBox vehicleType;

    @FXML
    private TextField engineNo;

    @FXML
    private TextField bodyMaker;

    @FXML
    private JFXDatePicker bodymakingDate;

    @FXML
    private JFXDatePicker purchaseDate;

    @FXML
    private TextField price;

    @FXML
    private JFXDatePicker registrationDate;

    @FXML
    private TextField registrationNo;

    @FXML
    private TextField user;

    @FXML
    private TextField kmranSofar;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton clear;

    @FXML
    private TableView tableView;


    private TextField [] textFields;
    private JFXDatePicker [] jfxDatePickers;
    private JFXComboBox [] jfxComboBoxes;
    private JFXButton [] jfxButtons;

    private final String [] columnNames = new String [] {"Id", "VehicleType", "User",  "Driver", "Helper",  "BodyMaker",
            "EngineNo", "BodymakingDate", "PurchaseDate", "price", "RegistrationDate", "RegistrationNo", "KmranSofar"};
    private final String components = "tCCCttDDtDttt";
    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields      = new TextField[]       {identity, bodyMaker, engineNo, price, registrationNo, user, kmranSofar};
        jfxDatePickers  = new JFXDatePicker[]   {bodymakingDate, purchaseDate, registrationDate};
        jfxComboBoxes   = new JFXComboBox[]     {vehicleType, driver, helper};
        jfxButtons      = new JFXButton[]       {add, delete, update, clear};

        try {
            gazi = new Gazi(textFields, jfxDatePickers, null, jfxComboBoxes, null, null,
                            jfxButtons, tableView, columnNames,
                            components, "0011111111110","ssssssDDdDssd",false, null, null, Vehicle.class);
            vehicleType.setItems(GaziDao.getAllByColumnName(VehicleType.class, "VehicleType",  "id"));
            driver.setItems(GaziDao.getAllByColumnName(Driver.class, "driver",  "id"));
            helper.setItems(GaziDao.getAllByColumnName(Helper.class, "helper",  "id"));
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



}
