package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import static sample.Main.CONNECTION_SOURCE;
import static sample.UI.makeAlert;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    private Dao<Util, String> UtilDao;

    private HashMap<String, String> map;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);
            map = Util.getById();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void signIn (){
        if(username.getText().trim().compareTo(map.get("username"))==0 &
            password.getText().trim().compareTo(map.get("password"))==0
        ){
            makeAlert("successful login!!");
            return;
        }
        else {
            makeAlert("unsuccessful login!!");
            return;
        }
    }


}
