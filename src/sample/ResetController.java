package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.stmt.UpdateBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.makeAlert;

public class ResetController implements Initializable {



    @FXML
    private PasswordField password;

    @FXML
    private PasswordField cpassword;

    @FXML
    private TextField q1;

    @FXML
    private TextField q2;

    private TextField [] textFields;

    private Dao<Util, String> UtilDao;

    private HashMap<String, String> map;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = new TextField[] { q1, q2, password, cpassword};
        //MyObject.enterKey(textFields, new JFXDatePicker[]{}, new JFXTimePicker[]{}, "tttt");
        try {
            UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);
            map = Util.getById();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void  reset () throws SQLException {
        if(isValid()==false)return;
        UpdateBuilder<Util, String> updateBuilder = UtilDao.updateBuilder();
        updateBuilder.where().eq("id", "password");
        updateBuilder.updateColumnValue("value", password.getText().trim());
        updateBuilder.update();
    }

    public void login () throws IOException {
        AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("login.fxml"));
    }

    public boolean isValid (){
        if(map.get("q1").toLowerCase().compareTo(q1.getText().trim().toLowerCase())!=0){
            makeAlert("wrong answer for question 1");
            return false;
        }
        else if(map.get("q2").toLowerCase().compareTo(q2.getText().trim().toLowerCase())!=0){
            makeAlert("wrong answer for question 2");
            return false;
        }
        else if(password.getText().trim().length() < 8){
            makeAlert("password length should at least 8");
            return false;
        }
        else if(password.getText().trim().compareTo(cpassword.getText().trim())!=0){
            makeAlert("password mismatch");
            return false;
        }
        return true;
    }
}
