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

import static sample.UI.makeAlert;


public class PricesController implements Initializable {


    @FXML
    private TextField materialName;

    @FXML
    private TextField unitPrice;

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
    private final String [] columnNames = new String [] {"materialName", "unitPrice"};
    private final String components = "tt";

    private Gazi gazi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields = new TextField[]{materialName, unitPrice};

        try {
            gazi = new Gazi(textFields, null, null, null, null, null,
                    new JFXButton[] {add, delete, update, clear}, tableView, columnNames,
                    components, "00","sd",false, null, null, Price.class);
            } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }





    public boolean isValid (boolean is) throws SQLException {

        if(materialName.getText().trim().isEmpty()){
            makeAlert("material name cant be empty");
            return false;
        }
        else if(unitPrice.getText().trim().isEmpty()){
            makeAlert("material name cant be empty");
            return false;
        }
        else if(!UI.isDouble(unitPrice.getText().trim())){
            makeAlert("price name should be in ammount");
            return false;
        }
        return true;

    }

    

}

