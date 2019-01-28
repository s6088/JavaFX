package sample;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    public final String DATABASE_URL = "jdbc:sqlite:gazi.db";
    public static ConnectionSource  CONNECTION_SOURCE;
    public final Class [] classes = new Class[] {
            Post.class,     VehicleType.class,
            Vehicle.class,  Driver.class,       Helper.class,   Service.class,
            Expense.class,  Limit.class,
            Price.class,    ServiceName.class,
            Goodwork.class, Allowance.class,    Overtime.class, Meal.class,
            FPost.class,    FExpense.class,
            Holiday.class,  Util.class,
            HourRate.class
    };

    @Override
    public void start(Stage primaryStage) throws Exception{
        CONNECTION_SOURCE = new JdbcConnectionSource(DATABASE_URL);
        for(int i=0; i<classes.length; i++){
            TableUtils.createTableIfNotExists(CONNECTION_SOURCE, classes[i]);
        }
        Parent root = FXMLLoader.load(getClass().getResource("post.fxml"));
        primaryStage.setScene(new Scene(root));
        primaryStage.setMaximized(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
