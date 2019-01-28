package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.*;

public class PostsController implements Initializable {

    @FXML
    private TextField identity;

    @FXML
    private TextField myIdentity;

    @FXML
    private JFXDatePicker createdAt;

    @FXML
    private JFXDatePicker departureDate;

    @FXML
    private JFXDatePicker arrivalDate;

    @FXML
    private JFXTimePicker departureTime;

    @FXML
    private JFXTimePicker arrivalTime;

    @FXML
    private TextField vehicle;

    @FXML
    private TextField driver;

    @FXML
    private TextField helper;

    @FXML
    private TextField departureKm;

    @FXML
    private TextField arrivalKm;

    @FXML
    private TextField user;

    @FXML
    private TextField from;

    @FXML
    private TextField to;


    @FXML
    private JFXDatePicker expenseDate;

    @FXML
    private TextField purpose;

    @FXML
    private TextField memo;

    @FXML
    private TextField remark;

    @FXML
    private TextField totalAmmount;

    @FXML
    private TextField expectedAmmount;

    @FXML
    private JFXCheckBox taka;

    @FXML
    private TableView myTableView;

    @FXML
    private TableView panel;

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

    @FXML
    private Label total;

    @FXML
    private MenuItem post;

    @FXML
    private MenuItem service;

    @FXML
    private MenuItem vehicletype;

    @FXML
    private MenuItem report;

    @FXML
    private MenuItem mvehicle;

    @FXML
    private MenuItem mhelper;

    @FXML
    private MenuItem mdriver;

    @FXML
    private MenuItem overtime;

    @FXML
    private MenuItem price;

    @FXML
    private MenuItem servicename;

    @FXML
    private MenuItem util;

    private MenuItem [] menuItems;


    private TextField[] textFields;
    private TextField[] textFields2;
    private JFXDatePicker[] jfxDatePickers;
    private JFXDatePicker[] jfxDatePickers2;
    private JFXTimePicker[] jfxTimePickers;


    private boolean previous;

    private final String[] columnNames = new String[]
            {"Id", "createdAt", "departureDate", "departureTime", "arrivalDate", "arrivalTime", "vehicle", "driver", "helper", "departureKm", "arrivalKm", "user", "from", "to"};
    private final String[] columnNames2 = new String[]
            {"expenses Id", "expenseDate", "purpose", "memo", "remark", "totalAmmount", "taka"};

    private final String components = "tDDTDTtttttttt";
    private final String components2 = "tDttttc";

    private Dao<Vehicle, String> VehicleDao;


    private String[] vehicles;
    private String[] drivers;
    private String[] helpers;
    private Gazi gazi;
    private Gazi gazi2;
    private Dao<Post, String> dao;
    private ObservableList<ObservableList<String>> data;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        menuItems = new MenuItem[] {
                post, service, vehicletype, mvehicle, mdriver, mhelper, overtime, price, servicename, util, report
        };


        for(int i=1; i<menuItems.length; i++){
            final int index = i;
                    menuItems[index].setOnAction(new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            Stage primaryStage = new Stage();
                            Parent root = null;
                            try {
                                String anik = menuItems[index].getId();
                                if(anik.charAt(0)=='m')anik = anik.substring(1);
                                root = FXMLLoader.load(getClass().getResource(  anik+".fxml"));
                                primaryStage.setScene(new Scene(root));
                                primaryStage.setMaximized(true);
                                primaryStage.show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    });
        }

        createdAt.setValue(LocalDate.now());
        identity.setText(getTimestamp());
        previous = false;
        data = FXCollections.observableArrayList();

        try {
            VehicleDao = DaoManager.createDao(CONNECTION_SOURCE, Vehicle.class);
            dao = DaoManager.createDao(CONNECTION_SOURCE, Post.class);
            previous = dao.queryForAll().size() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        textFields = new TextField[]{identity, vehicle, driver, helper, departureKm, arrivalKm, user, from, to};
        textFields2 = new TextField[]{myIdentity, purpose, memo, remark, totalAmmount};

        jfxDatePickers = new JFXDatePicker[]{createdAt, departureDate, arrivalDate};
        jfxDatePickers2 = new JFXDatePicker[]{expenseDate};

        jfxTimePickers = new JFXTimePicker[]{departureTime, arrivalTime};

        GaziUtil.tableInit(panel, data, new String[]{"", ""});

        try {
            gazi2 = new Gazi(textFields2, jfxDatePickers2, null, null, taka, null,
                    new JFXButton[]{myAdd, myDelete, myUpdate, myClear}, myTableView, columnNames2,
                    components2, "01111001", "sDsssdss", true, null, textFields[0], Expense.class);


            gazi = new Gazi(textFields, jfxDatePickers, jfxTimePickers, null, null, null,
                    new JFXButton[]{add, delete, update, clear}, tableView, columnNames,
                    components, "00000000100111", "sDDTDTsssddsss", true, gazi2, null, Post.class);


        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }


        taka.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    total.setText("Total Ammount");
                } else {
                    total.setText("Total Unit");
                }
            }
        });


        vehicle.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (VehicleDao.queryBuilder().where().eq("id", vehicle.getText().trim()).countOf() > 0) {
                    QueryBuilder<Vehicle, String> queryBuilder = VehicleDao.queryBuilder();
                    queryBuilder.where().eq("id", vehicle.getText().trim());
                    PreparedQuery<Vehicle> preparedQuery = queryBuilder.prepare();
                    List<Vehicle> result = VehicleDao.query(preparedQuery);
                    if (result == null) return;
                    driver.setText(result.get(0).getDriver());
                    helper.setText(result.get(0).getHelper());
                    departureKm.setText(result.get(0).getKmranSofar() + "");
                    user.setText(result.get(0).getUser());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }


    public void preview () throws SQLException {

        if (dao.queryBuilder().where().eq("id", identity.getText().trim()).countOf() <= 0) {
            makeAlert("nothing to preview");
            return;
        }
        Calculator calculator = new Calculator(identity.getText().trim(), data);


        DecimalFormat df = new DecimalFormat("#0.00");

        data.clear();

        Meal meal = calculator.getMeal();
        data.add(FXCollections.observableArrayList(new String[]{"Meal", ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Lunch", meal.getNumberOfLunch() + ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Driver Lunch", meal.getDriverLunchAmmount() + ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Helper Lunch", meal.getHelperLunchAmmount() + ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Dinner", meal.getNumberOfDinner() + ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Driver Dinner", meal.getDriverDinnerAmmount() + ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Helper Dinner", meal.getHelperDinnerAmmount() + ""}));


        data.add(FXCollections.observableArrayList(new String[]{"", ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Goodwork", ""}));
        List<Goodwork>goodworks = calculator.getGoodwork();

        data.add(FXCollections.observableArrayList(new String[]{"", ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Expense", ""}));
        Allowance allowance = calculator.getKmAllowance();
        data.add(FXCollections.observableArrayList(new String[]{"Driver gain/loose", df.format(allowance.getDriverAllowance())}));
        data.add(FXCollections.observableArrayList(new String[]{"Helper gain/loose", df.format(allowance.getHelperAllowance())}));


        data.add(FXCollections.observableArrayList(new String[]{"", ""}));
        data.add(FXCollections.observableArrayList(new String[]{"Overtime", ""}));
        Overtime overtimes = calculator.getOvertime();

    }




        public void publish () {

        }




/*

    public void autoCompleteInitilazation () throws SQLException {

        GenericRawResults<String[]> rawResults = VehicleDao.queryRaw("SELECT id FROM vehicle");
        List<String[]> results = rawResults.getResults();
        vehicles = aatoa(results, 0);



        if(vehicles != null){
            Arrays.sort(vehicles);
            TextFields.bindAutoCompletion(vehicle, vehicles);
        }

    }
    */



}
