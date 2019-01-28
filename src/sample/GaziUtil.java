package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class GaziUtil {
    public static void tableInit (TableView tableView, ObservableList<ObservableList<String>> data, String [] columnNames){

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
            tableView.getColumns().add(column);
        }
        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

}
