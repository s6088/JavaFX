package sample;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.util.ResourceBundle;

public class ServiceColorsController implements Initializable {

    @FXML
    private TableView tableView;

    final int days = 10;
    final double kms = 20;




    @Override
    public void initialize(URL location, ResourceBundle resources) {



        ObservableList<ObservableList<String>> tableData = FXCollections.observableArrayList();
        tableData.add(FXCollections.observableArrayList("22/11/2018\nworkshop1", "Row1Col2"));
        tableData.add(FXCollections.observableArrayList("Row2Col1", "Row2Col2"));
        tableData.add(FXCollections.observableArrayList("Row3Col1", "Row3Col2"));

        TableView<ObservableList<String>> table = tableView;

        TableColumn<ObservableList<String>, String> col1 = new TableColumn<ObservableList<String>, String>("wheel");
        col1.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().get(0)));

        // Set the cell factory of the column with a custom TableCell to modify its behavior.
        col1.setCellFactory(e -> new TableCell<ObservableList<String>, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                // Always invoke super constructor.
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // If index is two we set the background color explicitly.
                    if (getIndex() == 2) {
                        this.setStyle("-fx-background-color: green;");
                    }
                }
            }
        });

        TableColumn<ObservableList<String>, String> col2 = new TableColumn<ObservableList<String>, String>("suspense");
        col2.setCellValueFactory(e -> new SimpleStringProperty(e.getValue().get(1)));

        // Set the cell factory of the column with a custom TableCell to modify its behavior.
        col2.setCellFactory(e -> new TableCell<ObservableList<String>, String>() {
            @Override
            public void updateItem(String item, boolean empty) {
                // Always invoke super constructor.
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    setText(item);

                    // If index is zero we set the background color explicitly.
                    if (getIndex() == 0) {
                        this.setStyle("-fx-background-color: blue;");
                    }
                }
            }
        });

        table.getColumns().addAll(col1, col2);
        table.getItems().addAll(tableData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    }


    /*
    public void tableInit (){

        for (int i = 0; i < columnNames.length; i++) {
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellFactory(TextFieldTableCell.forTableColumn());
            final int index = i;
            column.setCellValueFactory(cd -> Bindings.valueAt(cd.getValue(), index));

            column.setOnEditCommit(evt -> {
                ObservableList<String> item = evt.getRowValue();
                String newValue = evt.getNewValue();
                if (item.size() <= index) {
                    for (int j = item.size(); j < index; j++) {
                        item.add(null);
                    }
                    item.add(newValue);
                } else {
                    item.set(index, newValue);
                }
            });
            column.setStyle("-fx-alignment: CENTER;");
            tableView.getColumns().add(column);
        }
        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }
    */


}
