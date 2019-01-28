package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.makeAlert;
import static sample.UtilsController.*;

public class ReportsController implements Initializable {


    @FXML
    private JFXDatePicker startingDate;

    @FXML
    private JFXDatePicker endingDate;

    @FXML
    private TextField company_name;

    @FXML
    private TextField company_address;

    @FXML
    private TextField prepared_by;

    @FXML
    private TextField recomended_by;

    @FXML
    private TextField approved_by;

    @FXML
    private TableView tableView;

    @FXML
    private TableView tableView1;

    @FXML
    private JFXComboBox report_on;

    @FXML
    private JFXComboBox employee_type;


    private final String [] employeeList = {"driver", "helper"};
    private final String [] reportList = {"expense", "meal", "good work", "overtime", "km allowance"};

    private Dao<Util, String> UtilDao;
    private HashMap<String, String> map;
    private List<Util> list;

    private TextField [] textFields;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields = new TextField[] {company_name, company_address, prepared_by, recomended_by, approved_by};
        map = new HashMap<>();
        employee_type.setItems(FXCollections.observableArrayList(employeeList));
        report_on.setItems(FXCollections.observableArrayList(reportList));

        try {


            UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);

            // querying all existing utilities form util database
            list = UtilDao.queryForAll();
            if(list!=null){
                for(int i=0; i<list.size(); i++)
                    map.put(list.get(i).getId(), list.get(i).getValue());


                //getting default data if not exist in database
                for(int i=0; i<ids.length; i++){
                    if(!map.containsKey(ids[i]))
                    {
                        try {
                            UtilDao.create(new Util(ids[i], values[i]));
                            map.put(ids[i], values[i]);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }


                // initialize ui combining default data and database data
                for(int i=0; i<textFields.length; i++)textFields[i].setText(map.get(textFields[i].getId()));

        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void preview () throws SQLException {
        if(!validation())return;
        Dao <FPost, String> fPostDao = DaoManager.createDao(CONNECTION_SOURCE, FPost.class);
        List <FPost> list = fPostDao.queryBuilder().where().between("createdAt", Date.valueOf(startingDate.getValue()), Date.valueOf(endingDate.getValue())).query();
        GenerateReport generateReport = new GenerateReport(list, true);
        //generateReport.printReport();
    }







    boolean validation (){
        if(startingDate.getValue()==null || endingDate.getValue()==null || employee_type.getValue()==null || report_on.getValue()==null){
            makeAlert("required field cant be empty!!");
            return false;
        }
        return true;
    }



}
