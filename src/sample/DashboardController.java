package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.UI.makeAlert;

public class DashboardController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    /*
    @FXML
    private AnchorPane root;

    @FXML
    private MenuItem post;

    @FXML
    private MenuItem service;

    @FXML
    private MenuItem vehicletype;

    @FXML
    private MenuItem report;

    @FXML
    private MenuItem vehicle;

    @FXML
    private MenuItem helper;

    @FXML
    private MenuItem driver;

    @FXML
    private MenuItem overtime;

    @FXML
    private MenuItem price;

    @FXML
    private MenuItem servicename;

    @FXML
    private MenuItem util;


    private MenuItem [] menuItems;

    private AnchorPane [] anchorPanes;

    private String [] resource;




    public void setRoot (AnchorPane anchorPane){
        anchorPane.getChildren().setAll(anchorPane);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        menuItems = new MenuItem[] {
                post, service, vehicletype, vehicle, driver, helper, overtime, price, servicename, util, report
        };

        anchorPanes = new AnchorPane[menuItems.length];



        int x = 0;

        root.getChildren().setAll(anchorPanes);
        anchorPanes[0].setVisible(true);

        for(int i=0; i<menuItems.length; i++){
            final int index = i;
            final int y =
            menuItems[index].setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent t) {
                    anchorPanes[index].setVisible(false);
                    anchorPanes[i].setVisible(true);
                    x = i;
                }
            });
        }


        menuItems[0].fire();


    }
    */

}
