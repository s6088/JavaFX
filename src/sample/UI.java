package sample;

import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.sql.Timestamp;
import java.util.List;

public class UI {

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void tableInit (TableView tableView, ObservableList < ObservableList <String> > data, String [] clmName, String chk) {

        final int columns = clmName.length;


        for (int i = 0; i < columns; i++) {

            final int index = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(clmName[i]);

            //ObservableList<String>, String
            column.setCellFactory(e -> new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    // Always invoke super constructor.
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        if (item.compareTo("sadman")==0) {
                            System.out.println(index + " " + item);
                            this.setStyle("-fx-background-color: #212121;");
                        }
                    }
                }
            });


            column.setCellValueFactory(cd -> Bindings.valueAt(cd.getValue(), index));

            tableView.getColumns().add(column);


        }

        tableView.setItems(data);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //tableView.getSelectionModel().selectedIndexProperty().addListener(new RowSelectChangeListener(i));
    }

    public static void makeAlert (String string){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();

    }

    public static boolean isDouble (String number){
        try
        {
            Double.parseDouble(number);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public static boolean isInteger (String number){
        try
        {
            Integer.parseInt(number);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    public static String getTimestamp (){
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return timestamp.getTime() + "";
    }

    public static String []  aatoa (List<String[]> tmp, int k){
        String [] data = new String[tmp.size()];
        for(int i=0; i<tmp.size(); i++)data[i] = tmp.get(i)[k];
        return data;
    }

}
