package sample;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static sample.Main.CONNECTION_SOURCE;

public class RegisterController implements Initializable {

    @FXML
    private TextField name;

    @FXML
    private TextField email;

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

    private final String [] myids = {"username", "email", "password", "cpassword", "q1", "q2"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textFields = new TextField[] {name, email, password, cpassword, q1, q2};
        //MyObject.enterKey(textFields, new JFXDatePicker[]{}, new JFXTimePicker[]{}, "tttttt");
        try {
            UtilDao = DaoManager.createDao(CONNECTION_SOURCE, Util.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*
    public void signUp (){
        if(!isValid())return;
        ObservableList<String> tmp = MyObject.getPanel(textFields, new JFXDatePicker[]{}, new JFXTimePicker[]{}, "tttttt");
        for(int i=0; i<myids.length; i++) {
            try {
                UtilDao.create(new Util(myids[i], tmp.get(i)));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean isValid (){
        if(name.getText().trim().isEmpty()){
            makeAlert("name field cant be empty");
            return false;
        }
        else if(email.getText().trim().isEmpty()){
            makeAlert("name field cant be empty");
            return false;
        }
        else if(MyObject.isValidEmailAddress(email.getText().trim())){
            makeAlert("invalid email");
            return false;
        }
        else if(password.getText().trim().isEmpty()){
            makeAlert("password field cant be empty");
            return false;
        }
        else if(password.getText().trim().length() < 8){
            makeAlert("password length atleast 8");
            return false;
        }
        else if(password.getText().trim().compareTo(cpassword.getText().trim()) != 0){
            System.out.println(password.getText().trim());
            System.out.println(cpassword.getText().trim());
            makeAlert("password mis match");
            return false;
        }
        else if(q1.getText().trim().isEmpty() | q2.getText().trim().isEmpty()){
            makeAlert("question field cant be empty");
            return false;
        }
        return true;
    }
*/
}
