package sample;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class VehicleTypesController implements Initializable {

    @FXML
    private TextField identity;

    @FXML
    private TextField myIdentity;

    @FXML
    private TextField driverSalary;

    @FXML
    private TextField driverLunchRate;

    @FXML
    private TextField driverDinnerRate;

    @FXML
    private TextField driverKmAllowance;

    @FXML
    private TextField helperSalary;

    @FXML
    private TextField helperLunchRate;

    @FXML
    private TextField helperDinnerRate;

    @FXML
    private TextField helperKmAllowance;

    @FXML
    private TextField materialName;

    @FXML
    private TextField maxperKm;

    @FXML
    private JFXCheckBox taka;

    @FXML
    private TableView myTableView;

    @FXML
    private TableView tableView;

    @FXML
    private JFXButton myAdd;

    @FXML
    private JFXButton myDelete;

    @FXML
    private JFXButton myUpdate;

    @FXML
    private JFXButton myClear;

    @FXML
    private JFXButton add;

    @FXML
    private JFXButton delete;

    @FXML
    private JFXButton update;

    @FXML
    private JFXButton clear;


    private TextField [] textFields1;
    private TextField [] textFields2;

    private final String [] columnNames1 = new String []
            {"vehicleType", "driverSalary", "driverLunchCost", "driverDinnerCost", "driverAllowance" ,
            "helperSalary", "helperLunchCost", "helperDinnerCost", "helperAllowance"};
    private final String [] columnNames2 = new String[]
            {"limit Id", "materialName", "maxperKm", "taka"};

    private final String components1 = "ttttttttt";
    private final String components2 = "tttc";
    private Gazi gazi;
    private Gazi gazi2;





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields1 = new TextField[]{identity,
                driverSalary, driverLunchRate, driverDinnerRate, driverKmAllowance,
                helperSalary, helperLunchRate, helperDinnerRate, helperKmAllowance};
        textFields2 = new TextField[]{myIdentity, materialName, maxperKm};

        try {
            gazi2 = new Gazi(textFields2, null, null, null, taka, null,
                    new JFXButton[]{myAdd, myDelete, myUpdate, myClear}, myTableView, columnNames2,
                    components2, "00010", "sssss", true, null, textFields1[0], Limit.class);


            gazi = new Gazi(textFields1, null, null, null, null, null,
                    new JFXButton[]{add, delete, update, clear}, tableView, columnNames1,
                    components1, "000001111", "sdddddddd", false, gazi2, null, VehicleType.class);

        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }




}

