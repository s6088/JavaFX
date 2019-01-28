package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ServiceNamesController implements Initializable {


    @FXML
    private TextField serviceName;

    @FXML
    private JFXComboBox<String> expiredOn;

    @FXML
    private TextArea description;

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


    private final String [] columnNames = new String [] {"ServiceName", "ExpiredOn", "Description"};
    private final String components = "tCx";
    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        expiredOn.setItems(FXCollections.observableArrayList(new String [] {"both", "date", "km"}));

        try {
            gazi = new Gazi(new TextField[] {serviceName}, null, null, new JFXComboBox[] {expiredOn}, null, description,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "001","sss",false, null, null, ServiceName.class);
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}

