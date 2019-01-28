package sample;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.UI.isInteger;
import static sample.UI.makeAlert;

public class HourRatesController implements Initializable {

    @FXML
    private TextField identity;

    @FXML
    private TextField hours;

    @FXML
    private TextField driverRate;

    @FXML
    private TextField helperRate;

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

    private final String [] columnNames = new String [] {"id", "hours",  "driver rate", "helper rate"};
    private final String components = "tttt";
    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields = new TextField[] {identity, hours, driverRate, helperRate};
        try {
            gazi = new Gazi(textFields, null, null, null, null, null,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "0001","sddd", true, null, null, HourRate.class);

        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }



    public boolean isValid (boolean is) throws SQLException {

        if(hours.getText().trim().isEmpty()) {
            makeAlert("Identity field can't be empty");
            return false;
        }

        else if(!isInteger(hours.getText().trim())) {
            makeAlert("hours should be a number");
            return false;
        }
        else if(Integer.parseInt(hours.getText().trim())<1 || Integer.parseInt(hours.getText().trim())>12){
            makeAlert("hours should be between 1 - 12");
            return false;
        }

        return true;
    }
}
