package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.controlsfx.control.CheckComboBox;
import org.json.JSONException;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.isInteger;
import static sample.UI.makeAlert;

public class UtilsController implements Initializable {


    private final String [] monthList = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};


    @FXML
    private Label label;

    @FXML
    private TextField textField;

    @FXML
    private JFXTimePicker jfxTimePicker;

    @FXML
    private TableView tableView;

    @FXML
    private TextField Year;

    @FXML
    private JFXComboBox<String> month;

    @FXML
    private CheckComboBox<String> marker;

    private int myMonth;

    private int myYear;

    private Dao<Holiday, String> HolidayDao;
    private Dao<Util, String> UtilDao;
    private ObservableList <ObservableList <String>> data;
    private HashMap <String, String> map;
    private List <Util> list;
    public static final String [] ids = {
            "lunch_starts", "lunch_duration",
            "dinner_starts", "dinner_duration",
            "company_name", "company_address",
            "prepared_by", "recomended_by", "approved_by",
            "work_starts", "work_duration",
            "min_time_to_count", "month_days"
    };

    public static final String [] values = {
            "9:00", "2", "22:00", "2",
            "Gazi Tyres", "81,82 Water Works Road, Posta, Dhaka, BD",
            "unknown", "unknown", "unknown",
            "9:00", "8",
            "4", "30"
    };

    private final String components = "TtTttttttTttt";

    @Override
    public void initialize(URL location, ResourceBundle resources){

        map = new HashMap<>();
        data = FXCollections.observableArrayList();

        try {
            HolidayDao = DaoManager.createDao(CONNECTION_SOURCE, Holiday.class);
            UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);
            list = UtilDao.queryForAll();
            if(list!=null){
                for(int i=0; i<list.size(); i++)
                    map.put(list.get(i).getId(), list.get(i).getValue());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(int i=0; i<ids.length; i++){
            if(map.containsKey(ids[i])){
                data.add(FXCollections.observableArrayList(new String [] {ids[i], map.get(ids[i])}));
            }
            else{
                try {
                    UtilDao.create(new Util(ids[i], values[i]));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                data.add(FXCollections.observableArrayList(new String [] {ids[i], values[i]}));
            }
        }

       UI.tableInit(tableView, data, new String[] {"", ""}, "");


        myMonth = 0;
        myYear = 0;
        month.setItems(FXCollections.observableArrayList(monthList));
        month.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> arg0, String arg1, String arg2) {
                if (arg2 != null) {
                    myMonth = Integer.parseInt(arg2);
                }
                else myMonth = 0;
            }
        });

        tableView.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener());


    }


    private class RowSelectChangeListener implements ChangeListener<Number> {
        @Override
        public void changed(ObservableValue<? extends Number> ov, Number oldVal, Number newVal) {
            DateTimeFormatter dateTimeFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-d");
            DateTimeFormatter dateTimeFormatter2 = DateTimeFormatter.ofPattern("H:mm");

            int ix = newVal.intValue();
            if ((ix < 0) || (ix >= data.size())) {
                return;
            }
            if(components.charAt(ix)=='t'){
                label.setText(data.get(ix).get(0));
                textField.setVisible(true);
                jfxTimePicker.setVisible(false);
                textField.setText(data.get(ix).get(1));
            }
            else{
                label.setText(data.get(ix).get(0));
                textField.setVisible(false);
                jfxTimePicker.setVisible(true);
                jfxTimePicker.setValue(LocalTime.parse(data.get(ix).get(1), dateTimeFormatter2));
            }
        }
    }


    public void myButton () throws SQLException {
        int ix = tableView.getSelectionModel().getSelectedIndex();
        if(ix<0 || ix>=data.size()){
            makeAlert("problem for saving!!");
            return;
        }
        UpdateBuilder<Util, String> updateBuilder = UtilDao.updateBuilder();
        updateBuilder.where().eq("id", data.get(ix).get(0));
        if(components.charAt(ix)=='t'){
            if(!textField.getText().isEmpty()){
                updateBuilder.updateColumnValue("value", textField.getText().trim());
                data.get(ix).set(1, textField.getText().trim());
            }
            else{
                makeAlert("problem for saving!!");
                return;
            }
        }
        else{
            if(jfxTimePicker.getValue()!=null){
                updateBuilder.updateColumnValue("value", jfxTimePicker.getValue());
                data.get(ix).set(1, jfxTimePicker.getValue().toString());
            }
            else {
                makeAlert("problem for saving!!");
                return;
            }
        }
        updateBuilder.update();
    }

    public void reloadMyButton () throws SQLException, ClassNotFoundException {
        if(!isInteger(Year.getText().trim())){
            makeAlert("invalid year");
            return;
        }
        else if(myMonth==0){
            makeAlert("invalid month");
            return;
        }
        myYear = Integer.parseInt(Year.getText().trim());
        for(LocalDate i = LocalDate.of(myYear, Month.of(myMonth),  1); i.getMonth()==Month.of(myMonth); i=i.plusDays(1))
            marker.getItems().add(i.toString()+ " " + i.getDayOfWeek());
        int index = 0;
        for(LocalDate i=LocalDate.of(myYear, Month.of(myMonth),  1); i.getMonth()==Month.of(myMonth); i=i.plusDays(1))
        {
            if(HolidayDao.queryBuilder().where().eq("id", i.toString()+ " " + i.getDayOfWeek()).countOf() > 0){
                marker.getCheckModel().check(index);
            }
            index++;
        }
    }

    public void saveMyButton () throws SQLException, ClassNotFoundException, JSONException {
        ObservableList<String> temp = marker.getItems();
        for(int i=0; i<temp.size(); i++)
        {
            boolean have = HolidayDao.queryBuilder().where().eq("id", temp.get(i)).countOf() > 0;
            boolean chk = marker.getCheckModel().isChecked(i);
            if(!(have^chk))continue;
            else
            {
                if(chk)
                {
                    GaziDao.addClass(Holiday.class, new Holiday(temp.get(i)));

                }
                else
                {
                    GaziDao.deleteByColumn(Holiday.class, "id", temp.get(i));
                }
            }
        }
    }




}



